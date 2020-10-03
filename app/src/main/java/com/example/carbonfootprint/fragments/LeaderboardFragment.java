package com.example.carbonfootprint.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.adapter.LeaderboardAdapter;
import com.example.carbonfootprint.adapter.NewsfeedAdapter;
import com.example.carbonfootprint.model.LeaderboardModel;
import com.example.carbonfootprint.model.NewsfeedModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderboardFragment extends Fragment {

    private ArrayList<LeaderboardModel> leaderboardModelArrayListTravel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFields();
    }

    private void initFields() {
        leaderboardModelArrayListTravel = new ArrayList<>();

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        leaderboardModelArrayListTravel.add(new LeaderboardModel("" ,sdf.format(timestamp), "Tamara", 10, "Car", 10.7f));
        leaderboardModelArrayListTravel.add(new LeaderboardModel("", sdf.format(timestamp), "Tudor", 20, "Car", 6.7f));
        leaderboardModelArrayListTravel.add(new LeaderboardModel("", sdf.format(timestamp), "Vlad", 16.5f, "Bus", 9.7f));
        leaderboardModelArrayListTravel.add(new LeaderboardModel("", sdf.format(timestamp),"Diana", 8.7f, "Car", 15.7f));
        leaderboardModelArrayListTravel.add(new LeaderboardModel("", sdf.format(timestamp),"Ionela", 4.5f, "Train", 20.7f));
        leaderboardModelArrayListTravel.add(new LeaderboardModel("", sdf.format(timestamp),"Razvan", 6f, "Car", 5.7f));


        Collections.sort(leaderboardModelArrayListTravel, (o1, o2) -> o1.getDistance() > o2.getDistance() ? -1 : 1);

        LeaderboardAdapter adapter = new LeaderboardAdapter(this.getActivity(), leaderboardModelArrayListTravel);

        // Attach the adapter to a ListView
        ListView listView = this.getView().findViewById(R.id.listLeaderboard);
        listView.setAdapter(adapter);
    }
}
