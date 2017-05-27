package com.project.comlab.comlabapp.ProfileConfig;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comlab.comlabapp.Adapters.RecyclerProjectsDeleteAdapter;
import com.project.comlab.comlabapp.CreateToolbar;
import com.project.comlab.comlabapp.POJO.ProjectsModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyProjectsActivity extends AppCompatActivity implements CreateToolbar {


    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    RecyclerView rv;
    RecyclerProjectsDeleteAdapter adapter;
    List<ProjectsModel> myProjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);

        showToolbar("Mis proyectos", false);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("projects");
        mAuth = FirebaseAuth.getInstance();

        rv = (RecyclerView) findViewById(R.id.rv_myProjects);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        myProjectList = new ArrayList<>();

        adapter = new RecyclerProjectsDeleteAdapter(this, getApplicationContext(), myProjectList);
        rv.setAdapter(adapter);

        FirebaseUser user = mAuth.getCurrentUser();

        reference.orderByChild("emailOwner").equalTo(user.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ProjectsModel project = dataSnapshot.getValue(ProjectsModel.class);
                project.setKey(dataSnapshot.getKey());
                myProjectList.add(project);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                ProjectsModel project = dataSnapshot.getValue(ProjectsModel.class);
                for (ProjectsModel pm: myProjectList) {
                    if(pm.getKey().equals(key)){
                        pm.setValues(project);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (ProjectsModel pm: myProjectList) {
                    if(pm.getKey().equals(key)){
                        myProjectList.remove(pm);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void showToolbar(String title, boolean backButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(backButton);
    }

    @Override
    public void showToolbarF(View v, String title, boolean backButton) {

    }
}
