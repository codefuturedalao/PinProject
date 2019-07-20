package com.example.android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import top.codefuturesql.loginandregi.HttpUtil;
import top.codefuturesql.loginandregi.Login;

public class LoginActivity extends AppCompatActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    final String url = HttpUtil.ServeUrl;

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUserNameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_by_name);
        //initialize
        mUserNameView = (EditText) findViewById(R.id.userNameInLogin);
        mPasswordView = (EditText) findViewById(R.id.passwordInLogin);

        //注册事件
        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(url);
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invaliduserName, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(String url) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a validuserName address.
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //    showProgress(true);
            mAuthTask = new UserLoginTask(userName, password);

            if (mAuthTask.login(url)) {
                Toast.makeText(LoginActivity.this, "Success to log in!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(LoginActivity.this, "Fail to log in!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isUserNameValid(String userName) {
        //TODO: Replace this with your own logic
        return userName.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUserName;
        private final String mPassword;

        UserLoginTask(String userName, String password) {
            mUserName = userName;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mUserName)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //  showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        public boolean login(String url) {
            Map<String, String> map = new HashMap<>();
            map.put("name", mUserName);
            map.put("password", mPassword);
////            FuncUtil.sendMessage("now i an in losAngle!");
////            FuncUtil.sendAlarm("and there is a hole in losAngle",10,-118.4079f, 33.9434f);
////
//            Alarm [] ala =  FuncUtil.getAlarm();
//            System.out.println(ala.length);
//            for(int i = 0;i<ala.length;i++){
//                System.out.println("" + ala[i].message);
//                System.out.println("" + ala[i].sendtime);
//                System.out.println("" + ala[i].longitude);
//                System.out.println("" + ala[i].latitude);
//            }
            return (Login.login(url, map));
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //    showProgress(false);
        }
    }


}
