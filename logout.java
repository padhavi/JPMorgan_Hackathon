package hk.ust.cse.comp107x.ngoconnect;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Padhavi on 08-07-2017.
 */

public class logout extends Fragment {

    private FirebaseAuth auth;
    private static final String LOG_TAG ="Logout";
    private View myView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth= FirebaseAuth.getInstance();
        auth.signOut();
        if(auth.getCurrentUser()==null)
            Log.d(LOG_TAG,"Log out successful");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Intent j = new Intent(getActivity(), MainActivity.class);
        j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(j);
        myView = inflater.inflate(R.layout.logout, container, false);
        return myView;

    }
}
