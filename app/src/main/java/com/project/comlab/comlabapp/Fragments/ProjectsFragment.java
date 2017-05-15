package com.project.comlab.comlabapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comlab.comlabapp.Activities.AddProjectActivity;
import com.project.comlab.comlabapp.Adapters.RecyclerProjectsAdapter;
import com.project.comlab.comlabapp.POJO.ProjectsModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends Fragment {

    RecyclerView rv_projects;
    RecyclerProjectsAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    List<ProjectsModel> projectList;
    FloatingActionButton fab;


    public ProjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("projects");

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                startActivity(intent);
            }
        });

        rv_projects = (RecyclerView) view.findViewById(R.id.rv_projects);
        rv_projects.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        projectList = new ArrayList<>();
        adapter = new RecyclerProjectsAdapter(getActivity(), getContext(), projectList);

        rv_projects.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                projectList.removeAll(projectList);
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    ProjectsModel project = snap.getValue(ProjectsModel.class);
                    projectList.add(project);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

}
