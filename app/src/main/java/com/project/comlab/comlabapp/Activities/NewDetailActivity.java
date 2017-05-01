package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.project.comlab.comlabapp.R;

public class NewDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_owner;
    JustifiedTextView tv_description;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

        tv_title = (TextView) findViewById(R.id.new_title_detail);
        tv_owner = (TextView) findViewById(R.id.new_owner_deatil);
        tv_description = (JustifiedTextView) findViewById(R.id.new_description_detail);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            String title = extras.getString("title");
            String owner = extras.getString("owner");
            String description = extras.getString("description");

            tv_title.setText(title);
            tv_owner.setText(owner);
            tv_description.setText(description);

        }


    }

    private void showToolbar(String title, boolean backButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(backButton);
    }
}
