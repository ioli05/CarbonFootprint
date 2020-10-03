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
import com.example.carbonfootprint.adapter.NewsfeedAdapter;
import com.example.carbonfootprint.helpers.NewRouteDialog;
import com.example.carbonfootprint.listeners.AddRouteListener;
import com.example.carbonfootprint.model.NewsfeedModel;
import com.example.carbonfootprint.services.DatabaseService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsFeedFragment extends Fragment {

    private static final String TAG = "NewsFeedFragment";
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
        ArrayList<NewsfeedModel> arrayOfRoutes = new ArrayList<>();
        NewsfeedAdapter adapter = new NewsfeedAdapter(getActivity(), arrayOfRoutes);

        DatabaseService databaseService = DatabaseService.getInstance();
        databaseService.setAddRouteListener(newsfeedModel -> {
            arrayOfRoutes.add(newsfeedModel);
            adapter.notifyDataSetChanged();
        });

        FirebaseFirestore.getInstance()
                .collection("Routes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for(DocumentSnapshot document : myListOfDocuments){
                                NewsfeedModel newsfeedModel = document.toObject(NewsfeedModel.class);

                                arrayOfRoutes.add(newsfeedModel);
                                Log.d(TAG, "onComplete: " + document);
                            }

                            adapter.notifyDataSetChanged();
                            ListView listView = getView().findViewById(R.id.list);
                            listView.setAdapter(adapter);
                        }
                    }
                });
    }
}
