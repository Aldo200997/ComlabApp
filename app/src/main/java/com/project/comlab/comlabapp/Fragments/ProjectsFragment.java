package com.project.comlab.comlabapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.comlab.comlabapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends Fragment {


    public ProjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        return view;
    }

}
