package com.example.carbonfootprint.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.model.UserModel;
import com.example.carbonfootprint.services.DatabaseService;
import com.example.carbonfootprint.tabset.MainActivity;

public class SetupActivity extends AppCompatActivity {

    private RadioGroup fuelTypeRg;
    private RadioGroup carSizeRg;
    private EditText electricityEt;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        findActivityViews();

        nextBtn.setOnClickListener(view -> {
            String fuelType = getCheckedOptionFuelType(fuelTypeRg.getCheckedRadioButtonId());
            String carSize = getCheckedOptionCarSize(carSizeRg.getCheckedRadioButtonId());
            int electricity = Integer.parseInt(electricityEt.getText().toString());

            DatabaseService.getInstance().writeNewUserData(new UserModel(carSize, fuelType, electricity));

            Toast.makeText(this, "Awesome! This was the first step in making your footprint greener", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void findActivityViews() {
        nextBtn = findViewById(R.id.next_btn);
        fuelTypeRg = findViewById(R.id.fuel_type_rg);
        carSizeRg = findViewById(R.id.car_size_rg);
        electricityEt = findViewById(R.id.electricity_et);
    }

    private String getCheckedOptionFuelType(int checkedId) {
        switch (checkedId) {
            case R.id.fuel_type_rg_1:
                return "Petrol";
            case R.id.fuel_type_rg_2:
                return "Diesel";
            case R.id.fuel_type_rg_3:
                return "Hybrid";
            case R.id.fuel_type_rg_4:
                return "Electric";
            default:
                return "";
        }
    }

    private String getCheckedOptionCarSize(int checkedId) {
        switch (checkedId) {
            case R.id.car_size__rg_1:
                return "Compact";
            case R.id.car_size__rg_2:
                return "Medium";
            case R.id.car_size__rg_3:
                return "Minivan";
            case R.id.car_size__rg_4:
                return "SUV";
            default:
                return "";
        }
    }

}