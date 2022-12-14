package com.taquangkhoi.keisic.ui.data;

import android.content.Context;
import android.util.Log;

import com.taquangkhoi.keisic.ui.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    /*
    * volatile: The volatile keyword ensures that multiple threads handle the unique instance
    * variable correctly when it is being initialized to the Singleton instance.
    * volatile đánh dấu biến đã được lưu trong bộ nhớ chính
    */
    private static volatile LoginRepository instance;

    private LoginSignupDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginSignupDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginSignupDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password, Context context) {
        Log.i("LoginRepository", "login: " + username + " " + password);

        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password, context);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public Result<LoggedInUser> signup(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.signup(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}