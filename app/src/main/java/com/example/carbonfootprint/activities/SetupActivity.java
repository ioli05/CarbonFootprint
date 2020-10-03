package com.example.carbonfootprint.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.helpers.Auth;

public class SetupActivity extends AppCompatActivity {

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        findActivityViews();

        Intent intent = new Intent(this, ProfileActivity.class);
        nextBtn.setOnClickListener(view -> startActivity(intent));
    }

    private void findActivityViews() {
        nextBtn = findViewById(R.id.next_btn);
    }

}