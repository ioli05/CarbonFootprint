package com.example.carbonfootprint.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.helpers.Auth;

public class ProfileActivity extends AppCompatActivity {

    private Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findActivityViews();

        signOutBtn.setOnClickListener(view -> Auth.signOut(this));
    }

    private void findActivityViews() {
        signOutBtn = findViewById(R.id.sign_out_btn);
    }

    public void updateUI() {
        if (!Auth.isUserSignedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}