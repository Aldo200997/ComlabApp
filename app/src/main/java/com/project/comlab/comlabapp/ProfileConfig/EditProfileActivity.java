package com.project.comlab.comlabapp.ProfileConfig;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.project.comlab.comlabapp.Activities.ContainerActivity;
import com.project.comlab.comlabapp.CreateToolbar;
import com.project.comlab.comlabapp.R;

public class EditProfileActivity extends AppCompatActivity implements CreateToolbar {

    TextInputEditText et_username;
    Button btn_update, btn_photo;
    private FirebaseAuth mAuth;
    String username;
    Uri image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        showToolbar("Editar perfil", false);

        mAuth = FirebaseAuth.getInstance();

        et_username = (TextInputEditText) findViewById(R.id.et_myUsername);
        btn_update = (Button) findViewById(R.id.btn_updateProfile);
        btn_photo = (Button) findViewById(R.id.btn_myImage);

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfilePhoto();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_username.getText().toString();

                if(username.equals("") && image == null){
                    Toast.makeText(getApplicationContext(), "Campo vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(username.equals("") && image != null){
                    updateImage();
                }

                if(!username.equals("") && image == null){
                    updateUsername();
                }

                if(!username.equals("") && image != null){
                    updateUsername();
                    updateImage();
                }

                startActivity(new Intent(EditProfileActivity.this, ContainerActivity.class));


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

    private void updateImage(){
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            UserProfileChangeRequest change = new UserProfileChangeRequest.Builder().setPhotoUri(image).build();
            user.updateProfile(change).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Se actualizaron los datos correctamente", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void updateProfilePhoto(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if(gallery.resolveActivity(getPackageManager()) != null){
            startActivityForResult(gallery, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            image = data.getData();
            Toast.makeText(getApplicationContext(), "Se seleccionó la nueva imágen", Toast.LENGTH_SHORT).show();
        }
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
