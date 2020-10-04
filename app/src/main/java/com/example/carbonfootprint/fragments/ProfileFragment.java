package com.example.carbonfootprint.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.activities.LoginActivity;
import com.example.carbonfootprint.adapter.NewsfeedAdapter;
import com.example.carbonfootprint.helpers.Auth;
import com.example.carbonfootprint.helpers.NewsfeedModelComparator;
import com.example.carbonfootprint.model.NewsfeedModel;
import com.example.carbonfootprint.services.DatabaseService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";


    public static class ProfileTravelFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d(TAG, "Travel");
            View view = inflater.inflate(R.layout.fragment_profile_travel, container, false);

            ArrayList<NewsfeedModel> arrayOfRoutes = new ArrayList<>();
            NewsfeedAdapter adapter = new NewsfeedAdapter(getActivity(), arrayOfRoutes);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(view.getContext());

            DatabaseService databaseService = DatabaseService.getInstance();
            databaseService.setAddRouteListener(newsfeedModel -> {
                arrayOfRoutes.add(newsfeedModel);
                adapter.notifyDataSetChanged();
            });

            String username = acct.getDisplayName();
            FirebaseFirestore.getInstance()
                    .collection("Routes")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for(DocumentSnapshot document : myListOfDocuments){
                                NewsfeedModel newsfeedModel = document.toObject(NewsfeedModel.class);

                                if(newsfeedModel.getName().equals(username)){
                                    arrayOfRoutes.add(newsfeedModel);
                                }
                            }

                            Collections.sort(arrayOfRoutes, new NewsfeedModelComparator());
                            adapter.notifyDataSetChanged();
                            ListView listView = getActivity().findViewById(R.id.list_profile);
                            listView.setAdapter(adapter);
                        }
                    });

            return view;
        }
    }

    public static class ProfileConsumptionFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d(TAG, "Consumption");
            View view = inflater.inflate(R.layout.fragment_profile_consumption, container, false);

            return view;
        }
    }

    public static class ProfileBadgesFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d(TAG, "Badges");
            View view = inflater.inflate(R.layout.fragment_profile_badges, container, false);

            return view;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        CircleImageView profilePicture = view.findViewById(R.id.profile_image);
        TextView fullname = view.findViewById(R.id.full_name);

        Button signOutBtn = view.findViewById(R.id.sign_out_profile);
        signOutBtn.setOnClickListener(btn -> Auth.signOut(this));

        openFragment(new ProfileTravelFragment());

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            Uri personPhoto = acct.getPhotoUrl();
            Picasso.get().setLoggingEnabled(true);
            Picasso.get().load(personPhoto).into(profilePicture);
            Log.d(TAG, "onCreateView: " + personPhoto + " " + personName);

            fullname.setText(personName);

            BottomNavigationView navView = view.findViewById(R.id.nav_profile_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            navView.setOnNavigationItemSelectedListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_profile_travel:
                        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        openFragment(new ProfileTravelFragment());
                        return true;
                    case R.id.navigation_profile_consumption:
                        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        openFragment(new ProfileConsumptionFragment());
                        return true;
                    case R.id.navigation_profile_badges:
                        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        openFragment(new ProfileBadgesFragment());
                        return true;
                }
                return false;
            });
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_cnt, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateUI() {
        if (!Auth.isUserSignedIn()) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();
        }
    }

}
