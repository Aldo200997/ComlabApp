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
        itemList.add(new ItemsProfileModel("Editar perfil", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fprofileuno.png?alt=media&token=d780a409-fe60-4cb2-a0e6-a79c308e3b02"));
        itemList.add(new ItemsProfileModel("Mis aportes", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fprofiledos.png?alt=media&token=be359f9e-b3bc-4ec2-9c97-cdd9136222f5"));
        itemList.add(new ItemsProfileModel("Mis eventos", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fprofilecinco.png?alt=media&token=e977a8ac-41f8-4cb1-a9ba-829d3ffd6e6b"));
        itemList.add(new ItemsProfileModel("Mis proyectos", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fprofilecuatro.png?alt=media&token=7b717295-3597-43fa-9ae2-e5b7999d04e4"));
        itemList.add(new ItemsProfileModel("Mis likes", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fprofilelikes.png?alt=media&token=b4e94d20-cdc1-4147-8313-67050d179da0"));
        itemList.add(new ItemsProfileModel("Mis amigos", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fprofilefriend.png?alt=media&token=b50cc509-bbf0-4ce3-8285-843bfb1f27a4"));
    }



}
