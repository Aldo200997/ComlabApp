package com.project.comlab.comlabapp.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.comlab.comlabapp.Maps.MapsActivity;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;

import java.io.File;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    TextInputEditText et_title, et_description, et_adress, et_date, et_time, et_tag, et_activities;
    String title;
    String description;
    String adress;
    String date;
    String time;
    String tag;
    String owner;
    String emailOwner;

    Button btn_date, btn_time;
    Button btn_add;
    Button photo;
    Button btn_tag;
    Button btn_map;

    private int dia, mes, anio, hora, minutos;

    String path;
    Intent gallery = null;

    // alertDialog
    String value;
    String [] preferences = {"Realidad Aumentada", "Realidad Virtual", "Videojuegos",
            "Machine Learning", "Big Data", "Internet of Things", "Movilidad", "Web",
            "Ecommerce", "Emprendimiento", "Seguridad informática", "Otros"};


    // Switches and elements
    SwitchCompat one, two, three;
    TextInputEditText et_adress_two, et_adress_three;
    TextInputEditText et_activities_two, et_activities_three;
    TextView n_one, n_two, n_three;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        reference = database.getReference("events");
        storageReference = storage.getReference("eventsImages");

        showToolbar("Agregar Evento", true);

        btn_date = (Button) findViewById(R.id.add_event_date_btn);
        btn_time = (Button) findViewById(R.id.add_event_time_btn);
        btn_date.setOnClickListener(this);
        btn_time.setOnClickListener(this);

        et_title = (TextInputEditText) findViewById(R.id.add_event_title);
        et_description = (TextInputEditText) findViewById(R.id.add_event_description);
        et_adress = (TextInputEditText) findViewById(R.id.add_event_adress);
        et_adress_two = (TextInputEditText) findViewById(R.id.add_event_adress_two);
        et_adress_three = (TextInputEditText) findViewById(R.id.add_event_adress_three);
        et_date = (TextInputEditText) findViewById(R.id.add_event_date);
        et_time = (TextInputEditText) findViewById(R.id.add_event_time);
        et_tag = (TextInputEditText) findViewById(R.id.add_event_tag);
        et_activities = (TextInputEditText) findViewById(R.id.add_event_activities);
        et_activities_two = (TextInputEditText) findViewById(R.id.add_event_activities_two);
        et_activities_three = (TextInputEditText) findViewById(R.id.add_event_activities_three);

        n_one = (TextView) findViewById(R.id.number_one);
        n_two = (TextView) findViewById(R.id.number_two);
        n_three = (TextView) findViewById(R.id.number_three);

        one = (SwitchCompat) findViewById(R.id.switch_one);
        two = (SwitchCompat) findViewById(R.id.switch_two);
        three = (SwitchCompat) findViewById(R.id.switch_three);
        one.setChecked(false);
        two.setChecked(false);
        three.setChecked(false);

        if(!one.isChecked() && !two.isChecked() && !three.isChecked()){
            et_adress.setVisibility(View.GONE);
            et_adress_two.setVisibility(View.GONE);
            et_adress_three.setVisibility(View.GONE);
            et_activities.setVisibility(View.GONE);
            et_activities_two.setVisibility(View.GONE);
            et_activities_three.setVisibility(View.GONE);
        }


        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et_adress.setVisibility(View.VISIBLE);
                    et_adress_two.setVisibility(View.GONE);
                    et_adress_three.setVisibility(View.GONE);
                    et_activities.setVisibility(View.VISIBLE);
                    et_activities_two.setVisibility(View.GONE);
                    et_activities_three.setVisibility(View.GONE);
                    two.setVisibility(View.GONE);
                    three.setVisibility(View.GONE);
                    n_one.setVisibility(View.VISIBLE);
                    n_two.setVisibility(View.GONE);
                    n_three.setVisibility(View.GONE);
                }else{
                    et_adress.setVisibility(View.GONE);
                    et_adress_two.setVisibility(View.GONE);
                    et_adress_three.setVisibility(View.GONE);
                    et_activities.setVisibility(View.GONE);
                    et_activities_two.setVisibility(View.GONE);
                    et_activities_three.setVisibility(View.GONE);
                    one.setVisibility(View.VISIBLE);
                    two.setVisibility(View.VISIBLE);
                    three.setVisibility(View.VISIBLE);
                    n_one.setVisibility(View.VISIBLE);
                    n_two.setVisibility(View.VISIBLE);
                    n_three.setVisibility(View.VISIBLE);
                }
            }
        });

        two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et_adress.setVisibility(View.VISIBLE);
                    et_adress_two.setVisibility(View.VISIBLE);
                    et_adress_three.setVisibility(View.GONE);
                    et_activities.setVisibility(View.VISIBLE);
                    et_activities_two.setVisibility(View.VISIBLE);
                    et_activities_three.setVisibility(View.GONE);
                    one.setVisibility(View.GONE);
                    three.setVisibility(View.GONE);
                    n_one.setVisibility(View.GONE);
                    n_two.setVisibility(View.VISIBLE);
                    n_three.setVisibility(View.GONE);
                }else{
                    et_adress.setVisibility(View.GONE);
                    et_adress_two.setVisibility(View.GONE);
                    et_adress_three.setVisibility(View.GONE);
                    et_activities.setVisibility(View.GONE);
                    et_activities_two.setVisibility(View.GONE);
                    et_activities_three.setVisibility(View.GONE);
                    one.setVisibility(View.VISIBLE);
                    two.setVisibility(View.VISIBLE);
                    three.setVisibility(View.VISIBLE);
                    n_one.setVisibility(View.VISIBLE);
                    n_two.setVisibility(View.VISIBLE);
                    n_three.setVisibility(View.VISIBLE);
                }
            }
        });

        three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et_adress.setVisibility(View.VISIBLE);
                    et_adress_two.setVisibility(View.VISIBLE);
                    et_adress_three.setVisibility(View.VISIBLE);
                    et_activities.setVisibility(View.VISIBLE);
                    et_activities_two.setVisibility(View.VISIBLE);
                    et_activities_three.setVisibility(View.VISIBLE);
                    one.setVisibility(View.GONE);
                    two.setVisibility(View.GONE);
                    n_one.setVisibility(View.GONE);
                    n_two.setVisibility(View.GONE);
                    n_three.setVisibility(View.VISIBLE);
                }else{
                    et_adress.setVisibility(View.GONE);
                    et_adress_two.setVisibility(View.GONE);
                    et_adress_three.setVisibility(View.GONE);
                    et_activities.setVisibility(View.GONE);
                    et_activities_two.setVisibility(View.GONE);
                    et_activities_three.setVisibility(View.GONE);
                    one.setVisibility(View.VISIBLE);
                    two.setVisibility(View.VISIBLE);
                    three.setVisibility(View.VISIBLE);
                    n_one.setVisibility(View.VISIBLE);
                    n_two.setVisibility(View.VISIBLE);
                    n_three.setVisibility(View.VISIBLE);
                }

            }
        });


        photo = (Button) findViewById(R.id.add_event_button_photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPicture();
            }
        });

        btn_tag = (Button) findViewById(R.id.add_event_button_tag);
        btn_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTag();
            }
        });

        btn_map = (Button) findViewById(R.id.add_event_button_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEventActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        btn_add = (Button) findViewById(R.id.add_event_btn);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = et_title.getText().toString();
                description = et_description.getText().toString();
                adress = et_adress.getText().toString();
                date = et_date.getText().toString();
                time = et_time.getText().toString();
                tag = et_tag.getText().toString();
                owner = "";

                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    owner = user.getDisplayName();
                    emailOwner = user.getEmail();
                }

                if(!one.isChecked() && !two.isChecked() && !three.isChecked()){
                    Snackbar.make(v,"Selecciona cuantas sedes hay", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(title.equals("") || description.equals("") || adress.equals("") || date.equals("") || time.equals("") || tag.equals("")){
                    Snackbar.make(v,"Campo vacío", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                uploadPicture();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == btn_date){
            final Calendar calendar = Calendar.getInstance();
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    et_date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                }
            }
            ,dia, mes, anio);
            datePicker.show();
        }

        if(v == btn_time){
            final Calendar calendar = Calendar.getInstance();
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minutos = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    et_time.setText(hourOfDay + ":" + minute);
                }
            },hora, minutos, false);
            timePicker.show();

        }
    }

    private void showToolbar(String title, boolean backButton){
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(backButton);
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
                    EventsModel evento = new EventsModel(title, description, imageURL, owner, emailOwner, adress, date, time, tag);
                    reference.push().setValue(evento);
                    Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
                    startActivity(intent);
                }
            });

        }else{
            EventsModel evento = new EventsModel(title, description, adress, owner, emailOwner, date, time, tag);
            reference.push().setValue(evento);
            Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
            startActivity(intent);
        }
    }

    private void getTag(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AddEventActivity.this);
        alert.setTitle("Elegir tag");
        View view = AddEventActivity.this.getLayoutInflater().inflate(R.layout.item_dialog_tag, null);

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
