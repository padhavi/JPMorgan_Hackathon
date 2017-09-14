package hk.ust.cse.comp107x.ngoconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup2 extends AppCompatActivity
{
    private FirebaseAuth auth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener authListener;
    // [END declare_auth_listener]
    private EditText emailField;
    private EditText passwordField;
    private EditText repPasswordField;
    private EditText userNameField;
    private Button signup;

    private String userName,email,password;
    private static final String LOG_TAG="SignUp EmailPassword";

    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        signup=(Button)findViewById(R.id.signup);
        emailField=(EditText)findViewById(R.id.email1);
        passwordField=(EditText)findViewById(R.id.password1);
        repPasswordField =(EditText)findViewById(R.id.re_password1);
        userNameField =(EditText)findViewById(R.id.username);
        Firebase.setAndroidContext(this);


        // [START initialize_auth]
        auth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(LOG_TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //  updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //add AuthStateListener whenever user is authenticated
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //remove AuthStateListener whenerver user is unauthenticated
        auth.removeAuthStateListener(authListener);
    }


    private void createAccount() {
        userName=userNameField.getText().toString();
        email=emailField.getText().toString();
        password=passwordField.getText().toString();

        Log.d(LOG_TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOG_TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if(task.isSuccessful())
                        {
                            // Use your User POJO to create the user data if they don't already exist.
                            // You can call the createUserInFirebaseHelper helper method and put
                            // the code for creating the user there.
                            String uid =  auth.getCurrentUser().getUid();
                            Log.d(LOG_TAG,"uid  "+ uid);
                            createUserInFirebaseHelper(uid);
                            Intent i1 = new Intent(signup2.this,LoginScreen.class);
                            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i1);
                        }
                        else {
                            Log.d(LOG_TAG, "Sign up fail "+ (task.getException().getMessage()));
                            Toast.makeText(signup2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void createUserInFirebaseHelper(String uid) {

        mDatabase= FirebaseDatabase.getInstance().getReference();

        Log.d(LOG_TAG,"user");
        Users newUser = new Users(userName, email);
        mDatabase.child("USERS").child(uid).setValue(newUser);



    }




    //to check whether text field is filled or not
    private boolean validateForm() {
        boolean valid = true;

        String user = userNameField.getText().toString();
        if (TextUtils.isEmpty(user)) {
            userNameField.setError("Required.");
            valid = false;
        } else {
            userNameField.setError(null);
        }
        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }
        String repeatPassword = repPasswordField.getText().toString();
        if (TextUtils.isEmpty(repeatPassword)) {
            repPasswordField.setError("Required.");
            valid = false;
        } else {
            repPasswordField.setError(null);
        }
        if(password.equals(repeatPassword)){
            repPasswordField.setError(null);

        }else{
            repPasswordField.setError("Passwords don't match");
            valid=false;
        }

        return valid;
    }
}
