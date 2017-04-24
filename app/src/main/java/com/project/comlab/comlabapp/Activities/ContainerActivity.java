package com.project.comlab.comlabapp.Activities;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.comlab.comlabapp.Fragments.EventsFragment;
import com.project.comlab.comlabapp.Fragments.NewsFragment;
import com.project.comlab.comlabapp.Fragments.ProfileFragment;
import com.project.comlab.comlabapp.Fragments.ProjectsFragment;
import com.project.comlab.comlabapp.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ContainerActivity extends AppCompatActivity {

    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setDefaultTab(R.id.contributes);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId){

                    case R.id.contributes:
                        NewsFragment newsFragment = new NewsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, newsFragment).commit();
                        break;

                    case R.id.events:
                        EventsFragment eventsFragment = new EventsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, eventsFragment).commit();
                        break;

                    case R.id.projects:
                        ProjectsFragment projectsFragment = new ProjectsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, projectsFragment).commit();
                        break;

                    case R.id.profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileFragment).commit();
                        break;



                }
            }
        });
    }
}
