package com.project.comlab.comlabapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Random;

public class AddNewActivity extends AppCompatActivity {

    TextInputEditText et_title, et_description, et_tag;
    Button btn_add;
    Button btn_photo;
    Button btn_tag;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    String pathAbsolute;

    String title;
    String description;
    String tag;
    String owner;
    String emailOwner;

    Intent gallery;

    // alertDialog
    String value;
    String [] preferences = {"Realidad Aumentada", "Realidad Virtual", "Videojuegos",
            "Machine Learning", "Big Data", "Internet of Things", "Movilidad", "Web",
            "Ecommerce", "Emprendimiento", "Seguridad informática", "Otros"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("news");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("newsImages");

        showToolbar("Agregar Aporte", true);

        et_title = (TextInputEditText) findViewById(R.id.add_new_title);
        et_description = (TextInputEditText) findViewById(R.id.add_new_description);
        et_tag = (TextInputEditText) findViewById(R.id.add_new_tag);
        btn_tag = (Button) findViewById(R.id.add_new_button_tag);


        btn_photo = (Button) findViewById(R.id.add_new_button_photo);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        btn_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTag();
            }
        });


        btn_add = (Button) findViewById(R.id.add_new_button);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = et_title.getText().toString();
                description = et_description.getText().toString();
                tag = et_tag.getText().toString();
                owner = "";

                if(title.equals("") || description.equals("") || tag.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo vacio", Toast.LENGTH_SHORT).show();
                    return;
                }


                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    owner = user.getDisplayName();
                    emailOwner = user.getEmail();
                }

               uploadFile();

            }
        });


    }

    private void showToolbar(String title, boolean backButton){
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(backButton);
    }

    private void takePicture(){
        gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        if(gallery.resolveActivity(getPackageManager()) != null){
            startActivityForResult(gallery, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){

            Uri imageUri = data.getData();
            File file = new File(getRealPathFromURI(imageUri));
            pathAbsolute = file.getAbsolutePath();
            Toast.makeText(getApplicationContext(), "Foto seleccionada", Toast.LENGTH_SHORT).show();

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

    private void uploadFile(){

        if(pathAbsolute != null){
            File imageFile = new File(pathAbsolute);

            long fileInBytes = imageFile.length();
            long fileInKb = fileInBytes / 1024;


            if(fileInKb > 2000){
                Toast.makeText(getApplicationContext(), "Tu imagen es muy pesada", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri uri = Uri.fromFile(imageFile);
            StorageReference imageReference = storageReference.child("image_" + uri.getLastPathSegment());
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
                    String imageURL = taskSnapshot.getDownloadUrl().toString();
                    NewsModel noticia = new NewsModel(title, description, imageURL, owner, emailOwner, tag, 0);
                    reference.push().setValue(noticia);
                    Intent intent = new Intent(AddNewActivity.this, ContainerActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            NewsModel noticia = new NewsModel(title, description, owner, emailOwner, tag, 0);
            reference.push().setValue(noticia);
            Intent intent = new Intent(AddNewActivity.this, ContainerActivity.class);
            startActivity(intent);
        }
    }

    private void getTag(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AddNewActivity.this);
        alert.setTitle("Elegir tag");
        View view = AddNewActivity.this.getLayoutInflater().inflate(R.layout.item_dialog_tag, null);

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
