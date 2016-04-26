package com.example.gwygw_000.project;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class LoginActivity extends FirebaseLoginBaseActivity
        implements CreateUserDialog.OnFragmentInteractionListener, FragmentCreateUserInfo.OnFragmentInteractionListener{

    Firebase firebaseRef;
    String mName;

    /* String Constants */
    private static final String FIREBASEREF = "https://luminous-heat-2520.firebaseio.com/";
    private static final String FIREBASE_ERROR = "Firebase Error";
    private static final String USER_ERROR = "User Error";
    private static final String LOGIN_SUCCESS = "Login Success";
    private static final String LOGOUT_SUCCESS = "Logout Success";
    private static final String USER_CREATION_SUCCESS =  "Successfully created user";
    private static final String USER_CREATION_ERROR =  "User creation error";
    private static final String EMAIL_INVALID =  "email is invalid :";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASEREF);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.login);
        if (login != null) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.this.showFirebaseLoginPrompt();
                }
            });
        }

        Button createButton = (Button) findViewById(R.id.createuser);
        if (createButton != null) {
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCreateUserDialog();
                }
            });
        }
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        Toast toast = Toast.
                makeText(this ,FIREBASE_ERROR + firebaseLoginError.message, Toast.LENGTH_SHORT);
        Log.d("Debug", firebaseLoginError.message);
        toast.show();
        resetFirebaseLoginPrompt();
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        Toast toast = Toast
                .makeText(this , USER_ERROR + firebaseLoginError.message, Toast.LENGTH_SHORT);
        toast.show();
        resetFirebaseLoginPrompt();
    }

    @Override
    public Firebase getFirebaseRef() {
        return firebaseRef;
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        switch (authData.getProvider()) {
            case "password":
                mName = (String) authData.getProviderData().get("email");
                break;
            default:
                mName = (String) authData.getProviderData().get("displayName");
                break;
        }
        Toast.makeText(getApplicationContext(), LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }

    @Override
    public void onFirebaseLoggedOut() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        // All providers are optional! Remove any you don't want.
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
        //setEnabledAuthProvider(AuthProviderType.GOOGLE);
        //setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        //setEnabledAuthProvider(AuthProviderType.TWITTER);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseRef.unauth();
    }

    // Validate email address for new accounts.
    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            Toast toast = Toast
                    .makeText(this , EMAIL_INVALID + email, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

    // create a new user in Firebase
    public void showCreateUserDialog() {
        CreateUserDialog createUserDialog = new CreateUserDialog();
        createUserDialog.show(getFragmentManager(), "CreateUser");
    }

    public void createUser(final String username, String password) {
        if(username == null ||  !isEmailValid(username)) {
            return;
        }
        firebaseRef.createUser(username, password,
                new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Toast toast = Toast
                                .makeText(getBaseContext(), USER_CREATION_SUCCESS, Toast.LENGTH_SHORT);
                        toast.show();
                        // TODO : invoke a fragment of user information
                        FragmentCreateUserInfo createUserDialog = FragmentCreateUserInfo.newInstance(username);
                        createUserDialog.show(getFragmentManager(), "CreateUserInfo");
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast toast = Toast
                                .makeText(getBaseContext(), USER_CREATION_ERROR, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    @Override
    public void onFragmentInteraction(String username, String password) {
        createUser(username, password);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
