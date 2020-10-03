package com.example.carbonfootprint.fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardFragment extends Fragment {

    private ArrayList<LeaderboardModel> leaderboardModelArrayListTravel;

    LeaderboardAdapter adapter;
    private static final String TAG = "LeaderboardFragment";

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
        adapter = new LeaderboardAdapter(this.getActivity(), leaderboardModelArrayListTravel);

        getDataFromDatabase();


        //Collections.sort(leaderboardModelArrayListTravel, (o1, o2) -> o1.getDistance() > o2.getDistance() ? -1 : 1);


        // Attach the adapter to a ListView
        ListView listView = this.getView().findViewById(R.id.listLeaderboard);
        listView.setAdapter(adapter);
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

                        adapter.notifyDataSetChanged();
                        ListView listView = getView().findViewById(R.id.listLeaderboard);
                        listView.setAdapter(adapter);
                    }
                });

    }
}
