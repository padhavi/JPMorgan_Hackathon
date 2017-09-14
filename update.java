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


public class update extends Fragment {

    private int id;
    private DatabaseReference mDatabase;
    Button updatebutton,getbtn;

    private String uid, currentUserName,currentUserEmail,currentphno,currentEventsname;
    private TextView reportdate,location,remarks,updateno,wid,wname;
    private FirebaseAuth auth;
    private View myView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Update");
        myView = inflater.inflate(R.layout.update, container, false);
        //reference to the database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        final DatabaseReference[] ref2 = new DatabaseReference[1];
        updatebutton = (Button) myView.findViewById(R.id.updatebutton);
        //getbtn = (Button) myView.findViewById(R.id.getbtn);
        reportdate = (TextView) myView.findViewById(R.id.reportdate);
        location = (TextView) myView.findViewById(R.id.location);
        remarks = (TextView) myView.findViewById(R.id.remarks);
        updateno = (TextView) myView.findViewById(R.id.updateno);
        wid = (TextView) myView.findViewById(R.id.wid);










            updatebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creating firebase object
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    //Getting values to store
                    String report = reportdate.getText().toString().trim();
                    String loc = location.getText().toString().trim();
                    String remark = remarks.getText().toString().trim();
                    String update = updateno.getText().toString().trim();
                    String womanid = wid.getText().toString().trim();



                        ref2[0] = mDatabase.child("updates").child(womanid).child(update);
                        ref2[0].child("loc").setValue(reportdate.getText().toString());
                        ref2[0].child("remark").setValue(location.getText().toString());
                        ref2[0].child("update").setValue(remarks.getText().toString());


                        Intent j = new Intent(getActivity(), LoginScreen.class);
                        j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(j);
                    }

            });


            return myView;

        }





}
