package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.project.comlab.comlabapp.R;

public class ProjectDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_owner;
    JustifiedTextView tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        tv_title = (TextView) findViewById(R.id.project_title_detail);
        tv_owner = (TextView) findViewById(R.id.project_owner_detail);
        tv_description = (JustifiedTextView) findViewById(R.id.project_description_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            String title = extras.getString("title");
            String description = extras.getString("description");
            String owner = extras.getString("owner");

            tv_title.setText(title);
            tv_description.setText(description);
            tv_owner.setText(owner);
        }
    }
}