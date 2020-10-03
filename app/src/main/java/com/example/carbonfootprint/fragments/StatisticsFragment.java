package com.example.carbonfootprint.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carbonfootprint.R;

public class StatisticsFragment extends Fragment {

    private View romania;
    private View europe;
    private View world;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();

        romania.setOnClickListener(v -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Romania Carbon Emissions compared to world averages")
                            .setView(LayoutInflater.from(getActivity()).inflate(R.layout.romania_data, null))
                            .setCancelable(true)
                            .create();
                    alertDialog.show();
                    alertDialog.getWindow().setLayout(1080, 990);
                }
        );

        europe.setOnClickListener(v -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("European Countries with Greatest Carbon Emissions")
                            .setView(LayoutInflater.from(getActivity()).inflate(R.layout.europe_data, null))
                            .setCancelable(true)
                            .create();
                    alertDialog.show();
                    alertDialog.getWindow().setLayout(1080, 990);
                }
        );

        world.setOnClickListener(v -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Countries with Greatest Carbon Emissions in the World")
                            .setView(LayoutInflater.from(getActivity()).inflate(R.layout.world_data, null))
                            .setCancelable(true)
                            .create();
                    alertDialog.show();
                    alertDialog.getWindow().setLayout(1080, 920);
                }
        );
    }

    private void findViews() {
        romania = getActivity().findViewById(R.id.romania_stats);
        europe = getActivity().findViewById(R.id.europe_stats);
        world = getActivity().findViewById(R.id.world_stats);
    }
}
