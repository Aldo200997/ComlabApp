package com.project.comlab.comlabapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.comlab.comlabapp.CreateToolbar;
import com.project.comlab.comlabapp.POJO.ProjectsModel;
import com.project.comlab.comlabapp.R;

import java.io.File;

public class AddProjectActivity extends AppCompatActivity implements CreateToolbar {

    TextInputEditText et_title, et_description, et_tag;
    Button btn_photo, btn_add;

    Intent gallery;

    String path;

    private FirebaseDatabase database;
    private DatabaseReference dReference;
    private FirebaseStorage storage;
    private StorageReference sReference;
    private FirebaseAuth mAuth;

    String title, description, tag, owner, emailOwner;

    // alertDialog
    Button btn_tag;
    String value;
    String [] preferences = {"Realidad Aumentada", "Realidad Virtual", "Videojuegos",
            "Machine Learning", "Big Data", "Internet of Things", "Movilidad", "Web",
            "Ecommerce", "Emprendimiento", "Seguridad informática", "Otros"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        showToolbar("Agregar proyecto", true);

        database = FirebaseDatabase.getInstance();
        dReference = database.getReference("projects");
        storage = FirebaseStorage.getInstance();
        sReference = storage.getReference("projectsImages");
        mAuth = FirebaseAuth.getInstance();

        et_title = (TextInputEditText) findViewById(R.id.add_project_title);
        et_description = (TextInputEditText) findViewById(R.id.add_project_description);
        et_tag = (TextInputEditText) findViewById(R.id.add_project_tag);

        btn_photo = (Button) findViewById(R.id.add_project_button_photo);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPicture();
            }
        });

        btn_tag = (Button) findViewById(R.id.add_project_button_tag);
        btn_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTag();
            }
        });

        btn_add = (Button) findViewById(R.id.add_project_button);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = et_title.getText().toString();
                description = et_description.getText().toString();
                tag = et_tag.getText().toString();

                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    owner = user.getDisplayName();
                    emailOwner = user.getEmail();
                }

                if(title.equals("") || description.equals("") || tag.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                uploadPicture();
            }
        });
    }

    private void setPicture(){
        gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if(gallery.resolveActivity(getPackageManager()) != null){
            startActivityForResult(gallery, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            File imageFile = new File(getRealPathFromURI(imageUri));
            path = imageFile.getAbsolutePath();
            Toast.makeText(getApplicationContext(), "Imágen seleccionada", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void uploadPicture(){
        if(path != null){
            File file = new File(path);

            long fileInBytes = file.length();
            long fileInKb = fileInBytes / 1024;


            if(fileInKb > 2000){
                Toast.makeText(getApplicationContext(), "Tu imagen es muy pesada", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri uri = Uri.fromFile(file);
            StorageReference imageReference = sReference.child("imageProject_" + uri.getLastPathSegment());
            UploadTask task = imageReference.putFile(uri);

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "No se pudo subir la imagen", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Subida completa", Toast.LENGTH_SHORT).show();
                    String image = taskSnapshot.getDownloadUrl().toString();
                    ProjectsModel proyecto = new ProjectsModel(title, description, image, owner, emailOwner, tag);
                    dReference.push().setValue(proyecto);
                    Intent intent = new Intent(AddProjectActivity.this, ContainerActivity.class);
                    startActivity(intent);
                }
            });

        }else{
            ProjectsModel proyecto = new ProjectsModel(title, description, owner, emailOwner, tag);
            dReference.push().setValue(proyecto);
            Intent intent = new Intent(AddProjectActivity.this, ContainerActivity.class);
            startActivity(intent);
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

    private void getTag(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AddProjectActivity.this);
        alert.setTitle("Elegir tag");
        View view = AddProjectActivity.this.getLayoutInflater().inflate(R.layout.item_dialog_tag, null);

        final ListView lv = (ListView) view.findViewById(R.id.item_dialog_tag_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, preferences);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                value = preferences[position];
            }
        });


        alert.setCancelable(true);
        alert.setPositiveButton("Seleccionar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                et_tag.setText(value);
            }
        });

        AlertDialog dialog = alert.create();
        dialog.setView(view);

        dialog.show();
    }
}
