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
import com.example.carbonfootprint.adapter.NewsfeedAdapter;
import com.example.carbonfootprint.helpers.NewRouteDialog;
import com.example.carbonfootprint.model.NewsfeedModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NewsFeedFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newsfeed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFields();
        getActivity().findViewById(R.id.add_route_btn).setOnClickListener(v -> new NewRouteDialog(getActivity()).show());
    }

    private void initFields() {

        ArrayList<NewsfeedModel> arrayOfUsers = new ArrayList<>();

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        arrayOfUsers.add(new NewsfeedModel(sdf.format(timestamp), "Tamara", 10, "Car", 10.7f));
        arrayOfUsers.add(new NewsfeedModel(sdf.format(timestamp), "Tudor", 20, "Car", 6.7f));
        arrayOfUsers.add(new NewsfeedModel(sdf.format(timestamp), "Vlad", 16.5f, "Bus", 9.7f));
        arrayOfUsers.add(new NewsfeedModel(sdf.format(timestamp), "Diana", 8.7f, "Car", 15.7f));
        arrayOfUsers.add(new NewsfeedModel(sdf.format(timestamp), "Ionela", 4.5f, "Train", 20.7f));
        arrayOfUsers.add(new NewsfeedModel(sdf.format(timestamp), "Razvan", 6f, "Car", 5.7f));
        NewsfeedAdapter adapter = new NewsfeedAdapter(this.getActivity(), arrayOfUsers);

        // Attach the adapter to a ListView
        ListView listView = this.getView().findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
