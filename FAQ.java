package hk.ust.cse.comp107x.ngoconnect;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class FAQ extends Fragment {

    private int id;
    private DatabaseReference mDatabase;
    Button registerbtn;


    private FirebaseAuth auth;
    private View myView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("REGISTRATION FORM");
        myView = inflater.inflate(R.layout.faq, container, false);





        return myView;

    }




}
