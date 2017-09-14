package hk.ust.cse.comp107x.ngoconnect;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Padhavi on 08-07-2017.
 */



public class loginpage extends Fragment {

    private int id;
    private DatabaseReference mDatabase;
    Button searchbtn;

    private String uid, currentUserName,currentUserEmail,currentphno,currentEventsname;
    private TextView womanname1,details;
    private FirebaseAuth auth;
    private View myView;
    Button mapbutton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("REGISTRATION FORM");
        myView = inflater.inflate(R.layout.loginpage, container, false);
        //reference to the database
        mDatabase= FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        uid=auth.getCurrentUser().getUid();
        final DatabaseReference[] ref2 = new DatabaseReference[1];
    mapbutton=(Button)myView.findViewById(R.id.mapbtn);

        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),check.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                v.getContext().startActivity(i);

            }
        });







        return myView;

    }




}
