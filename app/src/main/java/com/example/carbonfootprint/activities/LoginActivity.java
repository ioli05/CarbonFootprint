package com.example.carbonfootprint.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.carbonfootprint.R;
import com.example.carbonfootprint.helpers.Auth;
import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity {

    private SignInButton googleSignInBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Auth.setup(this);
        updateUI();

        findActivityViews();

        googleSignInBtn.setOnClickListener(view -> {
            Auth.signInWithGoogle(this);
            showProgressDialog("Signing in with Google. Please wait...");
        });

        getSupportActionBar().hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Auth.G_SIGN_IN) {
            Auth.handleSignInWithGoogle(this, data);
        }
    }

    public void updateUI() {
        if (Auth.isUserSignedIn()) {
            Intent intent = new Intent(this, SetupActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    private void findActivityViews() {
        googleSignInBtn = findViewById(R.id.login_google_sign_in_btn);
        progressDialog = new ProgressDialog(this);
    }

}