package com.project.comlab.comlabapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.comlab.comlabapp.Activities.ContainerActivity;
import com.project.comlab.comlabapp.Activities.CreateAccountActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_register;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_register = (Button) findViewById(R.id.login_button_register);
        btn_login = (Button) findViewById(R.id.login_button);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContainerActivity.class);
                startActivity(intent);
            }
        });
    }
}
