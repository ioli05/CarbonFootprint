package com.example.carbonfootprint.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.activities.SetupActivity;
import com.example.carbonfootprint.listeners.AddRouteListener;
import com.example.carbonfootprint.model.NewsfeedModel;
import com.example.carbonfootprint.services.DatabaseService;
import com.example.carbonfootprint.tabset.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.Map;

public class NewRouteDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    private RadioGroup transportTypeRg;
    private EditText distanceEt;
    public Button yes, no;
    public AddRouteListener addRouteListener;

    private String fuelType;
    private String carSize;

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

                if (transportType.equals("Car")) {
                    FirebaseFirestore.getInstance()
                            .collection("Users")
                            .document(Auth.getCurrentUser().getUid())
                            .get()
                            .addOnCompleteListener(task -> {
                                Map<String, Object> data = task.getResult().getData();
                                fuelType = (String) data.get("fuelType");
                                carSize = (String) data.get("carSize");
                                doStuff(personName, personPhoto, userId, transportType, distance);
                            });
                } else {
                    doStuff(personName, personPhoto, userId, transportType, distance);
                }
                break;
            case R.id.add_route_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void doStuff(String personName, String personPhoto, String uid, String transportType, double distance) {
        double carbonScore = calculateCarbonScore(transportType, distance);
        Date timestamp = new Date(System.currentTimeMillis());

        NewsfeedModel routeDetails = new NewsfeedModel(timestamp, personName, distance, transportType, carbonScore, personPhoto, uid);

        DatabaseService.getInstance().writeNewRoute(routeDetails);
    }

    public void setAddRouteListener(AddRouteListener addRouteListener) {
        this.addRouteListener = addRouteListener;
    }

    private double calculateCarbonScore(String transportType, double distance) {
        return getCarbonFactor(transportType) * distance;
    }

    private double getCarbonFactor(String transportType) {
        switch (transportType) {
            case "Airplane":
                return 0.1753;
            case "Train":
                return 0.065;
            case "Bus":
                return 0.0891;
            case "Car":
                return getCarCarbonFactor(fuelType, carSize);
            default:
                return 0;
        }
    }

    private double getCarCarbonFactor(String fuelType, String carSize) {
        switch (fuelType) {
            case "Petrol":
                switch (carSize) {
                    case "Compact":
                        return 0.2;
                    case "Medium":
                        return 0.31;
                    case "Minivan":
                    case "SUV":
                        return 0.47;
                }
                break;
            case "Diesel":
                switch (carSize) {
                    case "Compact":
                        return 0.24;
                    case "Medium":
                        return 0.38;
                    case "Minivan":
                        return 0.57;
                    case "SUV":
                        return 0.67;
                }
                break;
            case "Hybrid":
                switch (carSize) {
                    case "Compact":
                        return 0.19;
                    case "Medium":
                        return 0.25;
                    case "Minivan":
                    case "SUV":
                        return 0.28;
                }
                break;
            case "Electric":
                switch (carSize) {
                    case "Compact":
                        return 0.2;
                    case "Medium":
                        return 0.28;
                    case "Minivan":
                        return 0.35;
                    case "SUV":
                        return 0.47;
                }
                break;
        }
        return 0;
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
