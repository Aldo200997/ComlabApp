package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comlab.comlabapp.MainActivity;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btn;
    TextInputEditText et_email, et_email_repeat, et_username, et_password, et_password_repeat;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        et_email = (TextInputEditText) findViewById(R.id.create_account_email);
        et_email_repeat = (TextInputEditText) findViewById(R.id.create_account_email_repeat);
        et_username = (TextInputEditText) findViewById(R.id.create_account_username);
        et_password = (TextInputEditText) findViewById(R.id.create_account_password);
        et_password_repeat = (TextInputEditText) findViewById(R.id.create_account_password_repeat);

        String titleToolbar = getResources().getString(R.string.create_account_toolbar_str);
        showToolbar(titleToolbar, true);

        btn = (Button) findViewById(R.id.create_account_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Intent intent = new Intent(CreateAccountActivity.this, InterestedActivity.class);
                startActivity(intent); */

                String email = et_email.getText().toString().trim();
                String email_repeat = et_email_repeat.getText().toString().trim();
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String password_repeat = et_password_repeat.getText().toString();

                if(email.equals("") || email_repeat.equals("") || username.equals("") || password.equals("") || password_repeat.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!email.equals(email_repeat)){
                    Toast.makeText(getApplicationContext(), "Los correos no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(password_repeat)){
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    updateProfile();
                                }else{
                                    Toast.makeText(getApplicationContext(), "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }


    private void updateProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
            if(user != null){
                reference.child(user.getUid()).child("preferencias").setValue("IoT");
                UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setDisplayName(et_username.getText().toString()).build();
                user.updateProfile(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
    }



    private void showToolbar(String title, boolean backButton){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(backButton);
    }
}
