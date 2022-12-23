package com.taquangkhoi.keisic.ui.login;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.taquangkhoi.keisic.MainActivity;
import com.taquangkhoi.keisic.R;
import com.taquangkhoi.keisic.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;

    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private TextView registerTextView, tvwTitle;
    private Button loginButton;
    private ProgressBar loadingProgressBar;

    private static final int LOGIN_MODE = 1;
    private static final int REGISTER_MODE = 2;

    private int currentMode = LOGIN_MODE;

    private static final String TAG = "LoginActivity";

    FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        addViews();
        addEvents();

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        if (user != null) {
            signInDirectly();
            Log.i("LoginActivity", "User is signed in");
            //reload();
        }
    }

    private void addViews() {
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        usernameEditText = binding.edtUsername;
        passwordEditText = binding.edtPassword;
        confirmPasswordEditText = binding.edtConfirmPassword;
        loginButton = binding.btnLogin;
        loadingProgressBar = binding.loading;

        registerTextView = binding.tvwRegister;
        tvwTitle = binding.tvwTitle;
    }

    private void addEvents() {
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), LoginActivity.this);
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //updateUI(null);
                        }
                    });
//            switch (currentMode) {
//                case LOGIN_MODE:
//                    loadingProgressBar.setVisibility(View.VISIBLE);
//                    loginViewModel.login(usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString(), LoginActivity.this
//                    );
//                    break;
//                case REGISTER_MODE:
//                    loadingProgressBar.setVisibility(View.VISIBLE);
//                    loginViewModel.signup(usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString()
//                    );
//                    break;
//            }
        });

        registerTextView.setOnClickListener(v -> {
            if (confirmPasswordEditText.getVisibility() == View.GONE) {
                confirmPasswordEditText.setVisibility(View.VISIBLE);
                tvwTitle.setText(R.string.action_sign_up_short);
                registerTextView.setText(R.string.already_have_an_account_log_in);
                loginButton.setText(R.string.action_sign_up_short);
                currentMode = REGISTER_MODE;
            } else {
                confirmPasswordEditText.setVisibility(View.GONE);
                tvwTitle.setText(R.string.action_sign_in_short);
                registerTextView.setText(R.string.dont_have_an_account_sign_up);
                loginButton.setText(R.string.action_sign_in_short);
                currentMode = LOGIN_MODE;
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    /**
     * Create an Intent with data form FirebaseUser and go to MainActivity
     */
    private void signInDirectly() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        // add extra data
        intent.putExtra("username", user.getDisplayName());
        intent.putExtra("UID", user.getUid());
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}