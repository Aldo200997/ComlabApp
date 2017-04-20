package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.project.comlab.comlabapp.R;

public class CreateAccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        String titleToolbar = getResources().getString(R.string.create_account_toolbar_str);
        showToolbar(titleToolbar, true);

        btn = (Button) findViewById(R.id.create_account_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, InterestedActivity.class);
                startActivity(intent);
            }
        });
    }



    private void showToolbar(String title, boolean backButton){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(backButton);
    }
}
