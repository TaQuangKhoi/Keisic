package com.taquangkhoi.keisic.ui.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.taquangkhoi.keisic.MainActivity;
import com.taquangkhoi.keisic.ui.data.model.LoggedInUser;
import com.taquangkhoi.keisic.ui.login.LoginActivity;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginSignupDataSource {
    Context context;
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginDataSource";
    FirebaseUser user;

    public LoginSignupDataSource() {

    }

    public Result<LoggedInUser> login(String username, String password, Context context) {
        Log.i(TAG, "login: " + username + " " + password);

        try {
            // TODO: handle loggedInUser authentication
            // Initialize Firebase Auth


            LoggedInUser fakeUser =
                    new LoggedInUser(
                            user.getIdToken(true).toString(),
                            user.getDisplayName());

            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            Log.i(TAG, "login error: " + e.getMessage());
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> signup(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");

            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}