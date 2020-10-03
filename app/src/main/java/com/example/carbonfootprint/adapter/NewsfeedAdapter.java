package com.example.carbonfootprint.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.model.NewsfeedModel;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsfeedAdapter extends ArrayAdapter<NewsfeedModel> {

    ArrayList<NewsfeedModel> newsfeedModelArrayList;

    public NewsfeedAdapter(Activity context, ArrayList<NewsfeedModel> newsfeedModelArrayList) {
        super(context, R.layout.newsfeed_list, newsfeedModelArrayList);

        this.newsfeedModelArrayList = newsfeedModelArrayList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        NewsfeedModel model = newsfeedModelArrayList.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.newsfeed_list, parent, false);
        }
        // Lookup view for data population
        CircleImageView profilePicture = convertView.findViewById(R.id.image);
        TextView name = convertView.findViewById(R.id.nameNewsFeed);
        TextView date = convertView.findViewById(R.id.date);
        TextView distance = convertView.findViewById(R.id.distance);
        TextView type = convertView.findViewById(R.id.type);
        TextView carbonScore = convertView.findViewById(R.id.carbon_score);

        // Populate the data into the template view using the data object
        String personPhoto = model.getPhotoURI();
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(personPhoto).into(profilePicture);
        name.setText(model.getName());
        date.setText(model.getTimestamp().toString());
        distance.setText(String.valueOf(model.getDistance()) + " km");
        type.setText(model.getType());
        carbonScore.setText(String.valueOf(model.getCarbonScore()));
        // Return the completed view to render on screen
        return convertView;
    }
}
