package com.project.comlab.comlabapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.comlab.comlabapp.Activities.ContainerActivity;
import com.project.comlab.comlabapp.Activities.CreateAccountActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_register;
    Button btn_login;
    Button btn_signOut;
    Button btn_go;
    TextView tv_state;
    private FirebaseAuth mAuth;
    TextInputEditText et_email, et_password;
    TextInputLayout la_email, la_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        tv_state = (TextView) findViewById(R.id.login_state);

        btn_register = (Button) findViewById(R.id.login_button_register);
        btn_login = (Button) findViewById(R.id.login_button);
        btn_signOut = (Button) findViewById(R.id.signOut_button);
        btn_go = (Button) findViewById(R.id.login_go);

        la_email = (TextInputLayout) findViewById(R.id.layout_email);
        la_password = (TextInputLayout) findViewById(R.id.layout_password);
        et_email = (TextInputEditText) findViewById(R.id.login_email);
        et_password = (TextInputEditText) findViewById(R.id.login_password);

        updateState();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                /* Intent intent = new Intent(MainActivity.this, ContainerActivity.class);
                startActivity(intent); */

                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if(email.equals("")){
                    Snackbar.make(v,"email vacío", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(password.equals("")){
                    Snackbar.make(v,"contraseña vacía", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity.this, ContainerActivity.class);
                                    startActivity(intent);
                                }else{
                                    Snackbar.make(v,"Proceso incorrecto", Snackbar.LENGTH_SHORT).show();
                                }
                                updateState();
                            }
                        });
            }
        });

        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void updateState(){
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            tv_state.setText("Sesión iniciada " + user.getDisplayName());
            btn_login.setVisibility(View.GONE);
            btn_signOut.setVisibility(View.VISIBLE);
            la_email.setVisibility(View.GONE);
            la_password.setVisibility(View.GONE);
            btn_register.setVisibility(View.GONE);
            btn_go.setVisibility(View.VISIBLE);
            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ContainerActivity.class);
                    startActivity(intent);
                }
            });


        }else{
            tv_state.setText("Sesión NO iniciada");
            btn_signOut.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
            la_email.setVisibility(View.VISIBLE);
            la_password.setVisibility(View.VISIBLE);
            btn_register.setVisibility(View.VISIBLE);
            btn_go.setVisibility(View.GONE);


        }
    }

    private void signOut(){
        mAuth.signOut();
        updateState();
    }

    @Override
    public void onBackPressed() {

    }
}
