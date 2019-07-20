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

import top.codefuturesql.loginandregi.FuncUtil;
import top.codefuturesql.loginandregi.HttpUtil;
import top.codefuturesql.loginandregi.Login;

public class LoginActivity extends AppCompatActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    final String url = HttpUtil.ServeUrl;

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_by_email);
        //initialize
        mEmailView = (EditText) findViewById(R.id.emailInLogin);
        mPasswordView = (EditText) findViewById(R.id.passwordInLogin);

        //注册事件
        Button loginButton = (Button)findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(url);
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(String url) {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
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
            mAuthTask = new UserLoginTask(email, password);

            if (mAuthTask.login(url)){
                Toast.makeText(LoginActivity.this,"Success to log in!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MapActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(LoginActivity.this,"Fail to log in!",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
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

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
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
                if (pieces[0].equals(mEmail)) {
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

        public boolean login(String url){
            Map<String,String> map = new HashMap<>();
            map.put("name",mEmail);
            map.put("password",mPassword);
//            FuncUtil.sendMessage("now i an in losAngle!");
//            FuncUtil.sendAlarm("and there is a hole in losAngle",10,-118.4079f, 33.9434f);
//
            String[] alarm = new String[0];
            String[] sendtime = new String[0];
            double[] longitude = new double[0];
            double[] latitude = new double[0];
            FuncUtil.getAlarm(alarm,sendtime,longitude,latitude);
            System.out.println(alarm.length);
            for(int i = 0;i<2;i++){
                System.out.println("there is "+alarm.length+" alarm");
                System.out.println("" + alarm[i]);
                System.out.println("" + sendtime[i]);
                System.out.println("" + longitude[i]);
                System.out.println("" + latitude[i]);
            }
            return (Login.login(url,map));
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //    showProgress(false);
        }
    }


}
