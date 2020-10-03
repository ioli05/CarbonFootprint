package com.example.carbonfootprint.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.listeners.AddRouteListener;
import com.example.carbonfootprint.model.NewsfeedModel;
import com.example.carbonfootprint.services.DatabaseService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Date;

public class NewRouteDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    private RadioGroup transportTypeRg;
    private EditText distanceEt;
    public Button yes, no;
    public AddRouteListener addRouteListener;

    public NewRouteDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_new_route);
        findViews();

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    private void findViews() {
        transportTypeRg = findViewById(R.id.transportation_type_rg);
        distanceEt = findViewById(R.id.distance_et);
        yes = findViewById(R.id.add_route_yes);
        no = findViewById(R.id.add_route_no);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_route_yes:
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(v.getContext());
                DatabaseService databaseService = DatabaseService.getInstance();

                String personName = acct.getDisplayName();
                String personPhoto = acct.getPhotoUrl().toString();
                String userId = acct.getId();
                String transportType = getCheckedOptionTransportType(transportTypeRg.getCheckedRadioButtonId());
                double distance = Double.parseDouble(distanceEt.getText().toString());

                double carbonScore = calculateCarbonScore(transportType, distance);
                Date timestamp = new Date(System.currentTimeMillis());

                NewsfeedModel routeDetails = new NewsfeedModel(timestamp, personName, distance, transportType, carbonScore, personPhoto, userId);

                databaseService.writeNewRoute(routeDetails);
                Toast.makeText(c, transportType + " , " + distance, Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_route_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void setAddRouteListener(AddRouteListener addRouteListener) {
        this.addRouteListener = addRouteListener;
    }

    private double calculateCarbonScore(String transportType, double distance) {

        return 30.56;
    }

    private String getCheckedOptionTransportType(int checkedId) {
        switch (checkedId) {
            case R.id.transportation_type_rg_1:
                return "Airplane";
            case R.id.transportation_type_rg_2:
                return "Train";
            case R.id.transportation_type_rg_3:
                return "Bus";
            case R.id.transportation_type_rg_4:
                return "Car";
            case R.id.transportation_type_rg_5:
                return "Walk or Bike";
            default:
                return "";
        }
    }
}
