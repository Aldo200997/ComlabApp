package com.project.comlab.comlabapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.comlab.comlabapp.ProfileConfig.EditProfileActivity;
import com.project.comlab.comlabapp.ProfileConfig.MyNewsActivity;
import com.project.comlab.comlabapp.ProfileConfig.MyPostsActivity;
import com.project.comlab.comlabapp.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView tv_ep, tv_mpub, tv_ma, tv_me, tv_mpro, tv_ml, tv_username_b, tv_username_s;
    Intent intent;
    private FirebaseAuth mAuth;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();

        tv_username_b = (TextView) view.findViewById(R.id.username_profile_b);
        tv_username_s = (TextView) view.findViewById(R.id.username_profile_s);
        tv_ep = (TextView) view.findViewById(R.id.ep_profile);
        tv_mpub = (TextView) view.findViewById(R.id.mpub_profile);
        tv_ma = (TextView) view.findViewById(R.id.ma_profile);
        tv_me = (TextView) view.findViewById(R.id.me_profile);
        tv_mpro = (TextView) view.findViewById(R.id.mpro_profile);
        tv_ml = (TextView) view.findViewById(R.id.ml_profile);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            tv_username_b.setText(user.getDisplayName());
            tv_username_s.setText(user.getDisplayName());
        }


        tv_ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });


        tv_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), MyNewsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
