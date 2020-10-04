package com.example.carbonfootprint.fragments;

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
import androidx.fragment.app.FragmentTransaction;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.adapter.LeaderboardAdapter;
import com.example.carbonfootprint.helpers.Auth;
import com.example.carbonfootprint.model.LeaderboardModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardFragment extends Fragment {


    private static final String TAG = "LeaderboardFragment";

    public static class LeaderboardTravelFragment extends Fragment {
        private ArrayList<LeaderboardModel> leaderboardModelArrayListTravel;

        LeaderboardAdapter adapter;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            Log.d(TAG, "Travel");
            View view = inflater.inflate(R.layout.fragment_leaderboard_travel, container, false);

            initFields();
            return view;
        }

        private void initFields() {
            leaderboardModelArrayListTravel = new ArrayList<>();
            adapter = new LeaderboardAdapter(this.getActivity(), leaderboardModelArrayListTravel);

            getDataFromDatabase();

        }

        private void getDataFromDatabase() {

            Map<String, LeaderboardModel> map = new HashMap<>();

            FirebaseFirestore.getInstance()
                    .collection("Routes")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for(DocumentSnapshot document : myListOfDocuments){
                                LeaderboardModel newsfeedModel = document.toObject(LeaderboardModel.class);

                                if (!map.containsKey(newsfeedModel.getName())) {
                                    map.put(newsfeedModel.getName(), newsfeedModel);
                                }
                                else {
                                    LeaderboardModel model = map.get(newsfeedModel.getName());
                                    model.setDistance(model.getDistance() + newsfeedModel.getDistance());
                                    model.setCarbonScore(model.getCarbonScore() + newsfeedModel.getCarbonScore());
                                    map.put(newsfeedModel.getName(), model);
                                }

                                Log.d(TAG, "onComplete: " + document);
                            }
                            for (String name : map.keySet()) {
                                leaderboardModelArrayListTravel.add(map.get(name));
                            }

                            Collections.sort(leaderboardModelArrayListTravel, (o1, o2) -> o1.getDistance() > o2.getDistance() ? -1 : 1);
                            adapter.notifyDataSetChanged();
                            ListView listView = getView().findViewById(R.id.listLeaderboard);
                            listView.setAdapter(adapter);
                        }
                    });

        }
    }

    public static class LeaderboardConsumptionFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            Log.d(TAG, "Consumption");
            View view = inflater.inflate(R.layout.fragment_leaderboard_consumption, container, false);

            return view;
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        BottomNavigationView navView = view.findViewById(R.id.nav_leaderboard_view);

        openFragment(new LeaderboardTravelFragment());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_leaderboard_travel:
                    Log.d(TAG, "Travel ");
                    openFragment(new LeaderboardTravelFragment());
                    return true;
                case R.id.navigation_leaderboard_consumption:
                    Log.d(TAG, "consumption ");

                    openFragment(new LeaderboardConsumptionFragment());
                    return true;
            }
            return false;
        });

        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
