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


public class register extends Fragment {

    private int id;
    private DatabaseReference mDatabase;
    Button registerbtn;

    private String uid, currentUserName,currentUserEmail,currentphno,currentEventsname;
    private TextView idtext,nametext,phnotext,doctext,dobtext;
    private FirebaseAuth auth;
    private View myView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("REGISTRATION FORM");
        myView = inflater.inflate(R.layout.register, container, false);
        //reference to the database
        mDatabase= FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        uid=auth.getCurrentUser().getUid();
        final DatabaseReference[] ref2 = new DatabaseReference[1];
        registerbtn=(Button)myView.findViewById(R.id.registerbutton);
        idtext=(TextView)myView.findViewById(R.id.id);
       nametext=(TextView)myView.findViewById(R.id.name);
        phnotext=(TextView)myView.findViewById(R.id.phnno);
        dobtext=(TextView)myView.findViewById(R.id.dob);
      doctext=(TextView)myView.findViewById(R.id.doc);




        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object

                mDatabase= FirebaseDatabase.getInstance().getReference();


                //Getting values to store
                String id = idtext.getText().toString().trim();
                String name = nametext.getText().toString().trim();
                String phno =phnotext.getText().toString().trim();
                String doc = doctext.getText().toString().trim();
                String dob = dobtext.getText().toString().trim();


                ref2[0] = mDatabase.child("applicant").child(name);
                ref2[0].child("id").setValue(idtext.getText().toString());
                ref2[0].child("doc").setValue(doctext.getText().toString());
                ref2[0].child("phone").setValue(phnotext.getText().toString());
                ref2[0].child("dob").setValue(dobtext.getText().toString());


                Intent j = new Intent(getActivity(), LoginScreen.class);
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(j);

            }
        });


        return myView;

    }




}
