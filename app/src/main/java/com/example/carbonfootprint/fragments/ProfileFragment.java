package com.example.carbonfootprint.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.carbonfootprint.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment  extends Fragment {
    private static final String TAG = "ProfileFragment";

    public static class ProfileTravelFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d(TAG, "Travel");
            View view = inflater.inflate(R.layout.fragment_profile_travel, container, false);

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

        openFragment(new ProfileTravelFragment());

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            Uri personPhoto = acct.getPhotoUrl();
            Picasso.with(getActivity()).setLoggingEnabled(true);
            Picasso.with(getActivity()).load(personPhoto).into(profilePicture);
            Log.d(TAG, "onCreateView: " + personPhoto + " " + personName);

            fullname.setText(personName);

            BottomNavigationView navView = view.findViewById(R.id.nav_profile_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.

            navView.setOnNavigationItemSelectedListener(menuItem -> {
                switch(menuItem.getItemId()) {
                    case R.id.navigation_profile_travel:
                        openFragment(new ProfileTravelFragment());
                        return true;
                    case R.id.navigation_profile_consumption:
                        openFragment(new ProfileConsumptionFragment());
                        return true;
                    case R.id.navigation_profile_badges:
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
}
