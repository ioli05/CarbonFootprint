package com.example.carbonfootprint.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.model.LeaderboardModel;
import com.example.carbonfootprint.model.NewsfeedModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardModel> {
    ArrayList<LeaderboardModel> leaderboardModelArrayList;

    public LeaderboardAdapter(Activity context, ArrayList<LeaderboardModel> leaderboardModelArrayList) {
        super(context, R.layout.leaderboard_list, leaderboardModelArrayList);

        this.leaderboardModelArrayList = leaderboardModelArrayList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        NewsfeedModel model = leaderboardModelArrayList.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_list, parent, false);
        }
        // Lookup view for data population
        CircleImageView image = convertView.findViewById(R.id.image);
        TextView name = convertView.findViewById(R.id.nameNewsFeed);
        TextView distance = convertView.findViewById(R.id.distance);
        TextView carbonScore = convertView.findViewById(R.id.carbon_score);
        ImageView badge = convertView.findViewById(R.id.image_badge);

        // Populate the data into the template view using the data object
        String personPhoto = model.getPhotoURI();
        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(personPhoto).into(image);
        name.setText(model.getName());
        distance.setText(String.valueOf(model.getDistance()) + " km");

        if (position == 0) {
           badge.setImageResource(R.drawable.gold_medal);
        }
        if (position == 1) {
           badge.setImageResource(R.drawable.silver_medal);
        }
        if (position == 2) {
           badge.setImageResource(R.drawable.bronze_medal);
        }

        carbonScore.setText(String.valueOf(model.getCarbonScore()));
        // Return the completed view to render on screen
        return convertView;
    }
}
