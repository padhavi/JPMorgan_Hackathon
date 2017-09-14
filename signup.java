package hk.ust.cse.comp107x.ngoconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class signup extends AppCompatActivity implements View.OnClickListener {
    private Button signIn,signUp;
    private static final String TAG = "EmailPassword";

    private ProgressDialog progress;

    private EditText emailField;
    private EditText passwordField;
    // [START declare_auth]
    private FirebaseAuth auth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener authListener;
    // [END declare_auth_listener]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        Firebase.setAndroidContext(this);


        signIn=(Button)findViewById(R.id.button);
        signUp=(Button)findViewById(R.id.button2);

        /*signIn.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {

                signIn(emailField.getText().toString(), passwordField.getText().toString());
            }
        });*/

        /*signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SignUp.class);
                startActivity(i);

            }
        });
*/


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
                    Intent intent=new Intent(signup.this,LoginScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //  updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
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

    //handler for signUp button
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    //handler for signIn button
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(signup.this, LoginScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Log.w(TAG, "signInWithEmail"+ task.getException().getMessage());
                            Toast.makeText(signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]

    }

    //to check whether text field is filled or not
    private boolean validateForm() {
        boolean valid = true;

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

        return valid;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button:

                progress=new ProgressDialog(this);
                progress.setMessage("signing in");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();

                final int totalProgressTime =10;
                final Thread t = new Thread() {
                    @Override
                    public void run() {
                        int jumpTime = 0;

                        while(jumpTime < totalProgressTime) {
                            try {
                                sleep(20);
                                jumpTime += 4;
                                progress.setProgress(jumpTime);
                            }
                            catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                };
                t.start();
                signIn(emailField.getText().toString(), passwordField.getText().toString());

                break;
            case R.id.button2:
                Intent i = new Intent(signup.this,signup2.class);
                Log.d(TAG,"sign up clicked");
                startActivity(i);
                break;
        }
    }

}




