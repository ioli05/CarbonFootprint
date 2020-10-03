package com.example.carbonfootprint.services;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.example.carbonfootprint.helpers.Auth;
import com.example.carbonfootprint.listeners.AddRouteListener;
import com.example.carbonfootprint.model.NewsfeedModel;
import com.example.carbonfootprint.model.UserModel;
import com.google.firebase.firestore.FirebaseFirestore;

import static java.util.Objects.isNull;

public class DatabaseService {

    private static final String TAG = "DatabaseService";
    private FirebaseFirestore mDb;
    private AddRouteListener addRouteListener;
    private static DatabaseService databaseService = null;

    public void setAddRouteListener(AddRouteListener addRouteListener) {
        this.addRouteListener = addRouteListener;
    }

    public static DatabaseService getInstance() {
        if (isNull(databaseService)) {
            databaseService = new DatabaseService();
        }

        return databaseService;
    }

    public void writeNewRoute(NewsfeedModel routeDetails) {
        mDb = FirebaseFirestore.getInstance();

        mDb.collection("Routes")
                .document()
                .set(routeDetails)
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "writeNewRoute: success"))
                .addOnFailureListener(aVoid ->
                        Log.d(TAG, "writeNewRoute: failure"));

        if (!isNull(addRouteListener)) {
            addRouteListener.addRouteListener(routeDetails);
        }
    }

    public void writeNewUserData(UserModel userModel) {
        mDb = FirebaseFirestore.getInstance();

        mDb.collection("Users")
                .document(Auth.getCurrentUser().getUid())
                .set(userModel)
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "writeNewUserData: success"))
                .addOnFailureListener(aVoid ->
                        Log.d(TAG, "writeNewUserData: failure"));
    }
}
