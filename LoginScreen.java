package hk.ust.cse.comp107x.ngoconnect;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


//public class MainActivity extends ActionBarActivity {


public class LoginScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int id;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private static final String LOG_TAG="Login";
    private String uid, currentUserName,currentUserEmail;
    private TextView userNameTextView,emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //textView's
        userNameTextView=(TextView)navigationView.getHeaderView(0).findViewById(R.id.userNameText);
        emailTextView=(TextView)navigationView.getHeaderView(0).findViewById(R.id.emailText);

        //reference to the database
        mDatabase= FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        uid=auth.getCurrentUser().getUid();
        Log.d(LOG_TAG+" auth",uid);

        mDatabase.child("USERS").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUserName =dataSnapshot.child("name").getValue(String.class);
                currentUserEmail=dataSnapshot.child("email").getValue(String.class);
                Log.d(LOG_TAG+" name",currentUserName);
                userNameTextView.setText(currentUserName);
                emailTextView.setText(currentUserEmail);

              /*  User user =dataSnapshot.getValue(User.class);
                if(user!=null)
                    Log.d(LOG_TAG +" user", user.getEmail());*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(auth.getCurrentUser()!=null)
                    Log.d(LOG_TAG, "getUser:onCancelled", databaseError.toException());
            }
        });


        FragmentManager fragmentManager = getFragmentManager();
        id=R.id.button;
        if (id == R.id.button) {
            fragmentManager.beginTransaction()
                    .replace((R.id.content_frame)
                            , new loginpage()).commit();

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.search){
            return true;
                *//*Intent i=new Intent(LoginScreen.this,SelectBook.class);
                startActivity(i);*//*
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_register) {
            fragmentManager.beginTransaction()
                    .replace((R.id.content_frame)
                            , new register()).commit();

        }
        else if (id == R.id.nav_update) {
            fragmentManager.beginTransaction()
                    .replace((R.id.content_frame)
                            , new update()).commit();

        } else if (id == R.id.nav_logout) {
            fragmentManager.beginTransaction()
                    .replace((R.id.content_frame)
                            , new logout()).commit();

        }else if (id == R.id.nav_home) {
            fragmentManager.beginTransaction()
                    .replace((R.id.content_frame)
                            , new loginpage()).commit();

        }else if (id == R.id.nav_faq) {
            fragmentManager.beginTransaction()
                    .replace((R.id.content_frame)
                            , new FAQ()).commit();
        }

        // Handle navigation view item clicks here.

        if (id == R.id.nav_home) {
             Toast.makeText(this,"home",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_register) {
            Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_update) {
            Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_faq) {
            Toast.makeText(this, "FAQ", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

