package com.project.comlab.comlabapp.ProfileConfig;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.project.comlab.comlabapp.R;

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText et_username;
    Button btn_update;
    private FirebaseAuth mAuth;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();

        et_username = (TextInputEditText) findViewById(R.id.et_myUsername);
        btn_update = (Button) findViewById(R.id.btn_updateProfile);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_username.getText().toString();

                if(username == null){
                    Toast.makeText(getApplicationContext(), "El campo está vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateUsername();


            }
        });
    }

    private void updateUsername(){
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            UserProfileChangeRequest change = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
            user.updateProfile(change).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Se cambiaron correctamente los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
