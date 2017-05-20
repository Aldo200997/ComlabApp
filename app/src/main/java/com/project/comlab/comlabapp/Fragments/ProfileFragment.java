package com.project.comlab.comlabapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.comlab.comlabapp.Adapters.RecyclerProfileAdapter;
import com.project.comlab.comlabapp.POJO.ItemsProfileModel;
import com.project.comlab.comlabapp.ProfileConfig.EditProfileActivity;
import com.project.comlab.comlabapp.ProfileConfig.MyEventsActivity;
import com.project.comlab.comlabapp.ProfileConfig.MyNewsActivity;
import com.project.comlab.comlabapp.ProfileConfig.MyPostsActivity;
import com.project.comlab.comlabapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    TextView tv_username_b, tv_username_s;
    Intent intent;
    RecyclerView rv;
    RecyclerProfileAdapter adapter;
    List<ItemsProfileModel> itemList;
    CircleImageView image;
    private FirebaseAuth mAuth;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();

        rv = (RecyclerView) view.findViewById(R.id.rv_profile);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        initializeData();
        adapter = new RecyclerProfileAdapter(getActivity(), getContext(), itemList);
        rv.setAdapter(adapter);

        tv_username_b = (TextView) view.findViewById(R.id.username_profile_b);
        tv_username_s = (TextView) view.findViewById(R.id.username_profile_s);
        image = (CircleImageView) view.findViewById(R.id.image_profile);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            tv_username_b.setText(user.getDisplayName());
            tv_username_s.setText(user.getDisplayName());
            image.setImageURI(user.getPhotoUrl());
        }


        return view;
    }

    private void initializeData(){
        itemList = new ArrayList<>();
        itemList.add(new ItemsProfileModel("Editar perfil", R.drawable.profileuno));
        itemList.add(new ItemsProfileModel("Mis aportes", R.drawable.profiledos));
        itemList.add(new ItemsProfileModel("Mis eventos", R.drawable.profilecinco));
        itemList.add(new ItemsProfileModel("Mis proyectos", R.drawable.profilecuatro));
        itemList.add(new ItemsProfileModel("Mis likes", R.drawable.profileuno));
        itemList.add(new ItemsProfileModel("Mis amigos", R.drawable.profileuno));
    }



}
