package com.taquangkhoi.keisic.ui.data;

import com.google.firebase.auth.FirebaseAuth;
import com.taquangkhoi.keisic.ui.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginSignupDataSource {
    private FirebaseAuth mAuth;
    private static final String TAG = "LoginDataSource";

    public Result<LoggedInUser> login(String username, String password) {

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