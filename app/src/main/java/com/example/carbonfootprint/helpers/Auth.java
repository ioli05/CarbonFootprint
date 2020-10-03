package com.example.carbonfootprint.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.carbonfootprint.activities.LoginActivity;
import com.example.carbonfootprint.R;
import com.example.carbonfootprint.activities.ProfileActivity;
import com.example.carbonfootprint.activities.SetupActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Auth {
    public static final int G_SIGN_IN = 100;

    private static final String TAG = "Auth";

    private static final String METHOD_SIGN_IN_GOOGLE = "handleSignInWithGoogle";

    private static FirebaseAuth mAuth;
    private static GoogleSignInClient gsClient;

    public static void setup(Context context) {
        Context mContext = context.getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gsOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.web_client_id))
                .requestEmail()
                .build();
        gsClient = GoogleSignIn.getClient(mContext, gsOptions);
    }

    public static boolean isUserSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public static FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public static void signInWithGoogle(Activity activity) {
        Intent signInIntent = gsClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, G_SIGN_IN);
    }

    public static void handleSignInWithGoogle(LoginActivity activity, Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            // The Task returned from this call is always completed, no need to attach a listener.
            GoogleSignInAccount account = task.getResult(ApiException.class);

            AuthCredential credential = GoogleAuthProvider
                    .getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential).addOnCompleteListener(authTask -> {
                activity.dismissProgressDialog();
                if (authTask.isSuccessful()) {
                    Log.i(TAG, METHOD_SIGN_IN_GOOGLE + ":SUCCESS");
                    activity.updateUI();
                } else {
                    Log.w(TAG, METHOD_SIGN_IN_GOOGLE + ":FAILED", task.getException());
                }
            });
        } catch (ApiException e) {
            Log.e(TAG, METHOD_SIGN_IN_GOOGLE + ":FAILED", e);
        }
    }

    public static void signOut(ProfileActivity activity) {
        mAuth.signOut();
        gsClient.signOut().addOnCompleteListener(activity, task -> activity.updateUI());
    }
}