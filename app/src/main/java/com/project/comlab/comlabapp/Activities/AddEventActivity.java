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
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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
    String adress_two;
    String adress_three;
    String activities;
    String activities_two;
    String activities_three;
    String date;
    String date_two;
    String date_three;
    String time;
    String time_two;
    String time_three;
    String tag;
    String owner;
    String emailOwner;

    Button btn_date, btn_date_two, btn_date_three, btn_time, btn_time_two, btn_time_three;
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
    TextInputEditText et_date_two, et_date_three;
    TextInputEditText et_time_two, et_time_three;
    TextView n_one, n_two, n_three;


    // CardViews
    CardView c_one, c_two, c_three;




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
        btn_date_two = (Button) findViewById(R.id.add_event_date_two_btn);
        btn_date_three = (Button) findViewById(R.id.add_event_date_three_btn);
        btn_time = (Button) findViewById(R.id.add_event_time_btn);
        btn_time_two = (Button) findViewById(R.id.add_event_time_two_btn);
        btn_time_three = (Button) findViewById(R.id.add_event_time_three_btn);
        btn_date.setOnClickListener(this);
        btn_date_two.setOnClickListener(this);
        btn_date_three.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        btn_time_two.setOnClickListener(this);
        btn_time_three.setOnClickListener(this);

        et_title = (TextInputEditText) findViewById(R.id.add_event_title);
        et_description = (TextInputEditText) findViewById(R.id.add_event_description);
        et_adress = (TextInputEditText) findViewById(R.id.add_event_adress);
        et_adress_two = (TextInputEditText) findViewById(R.id.add_event_adress_two);
        et_adress_three = (TextInputEditText) findViewById(R.id.add_event_adress_three);
        et_date = (TextInputEditText) findViewById(R.id.add_event_date);
        et_date_two = (TextInputEditText) findViewById(R.id.add_event_date_two);
        et_date_three = (TextInputEditText) findViewById(R.id.add_event_date_three);
        et_time = (TextInputEditText) findViewById(R.id.add_event_time);
        et_time_two = (TextInputEditText) findViewById(R.id.add_event_time_two);
        et_time_three = (TextInputEditText) findViewById(R.id.add_event_time_three);
        et_tag = (TextInputEditText) findViewById(R.id.add_event_tag);
        et_activities = (TextInputEditText) findViewById(R.id.add_event_activities);
        et_activities_two = (TextInputEditText) findViewById(R.id.add_event_activities_two);
        et_activities_three = (TextInputEditText) findViewById(R.id.add_event_activities_three);

        c_one = (CardView) findViewById(R.id.card_one);
        c_two = (CardView) findViewById(R.id.card_two);
        c_three = (CardView) findViewById(R.id.card_three);

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
            c_one.setVisibility(View.GONE);
            c_two.setVisibility(View.GONE);
            c_three.setVisibility(View.GONE);
        }


        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    c_one.setVisibility(View.VISIBLE);
                    c_two.setVisibility(View.GONE);
                    c_three.setVisibility(View.GONE);

                    two.setVisibility(View.GONE);
                    three.setVisibility(View.GONE);
                    n_one.setVisibility(View.VISIBLE);
                    n_two.setVisibility(View.GONE);
                    n_three.setVisibility(View.GONE);
                }else{


                    c_one.setVisibility(View.GONE);
                    c_two.setVisibility(View.GONE);
                    c_three.setVisibility(View.GONE);

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

                    c_one.setVisibility(View.VISIBLE);
                    c_two.setVisibility(View.VISIBLE);
                    c_three.setVisibility(View.GONE);

                    one.setVisibility(View.GONE);
                    three.setVisibility(View.GONE);
                    n_one.setVisibility(View.GONE);
                    n_two.setVisibility(View.VISIBLE);
                    n_three.setVisibility(View.GONE);
                }else{

                    c_one.setVisibility(View.GONE);
                    c_two.setVisibility(View.GONE);
                    c_three.setVisibility(View.GONE);

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

                    c_one.setVisibility(View.VISIBLE);
                    c_two.setVisibility(View.VISIBLE);
                    c_three.setVisibility(View.VISIBLE);

                    one.setVisibility(View.GONE);
                    two.setVisibility(View.GONE);
                    n_one.setVisibility(View.GONE);
                    n_two.setVisibility(View.GONE);
                    n_three.setVisibility(View.VISIBLE);
                }else{

                    c_one.setVisibility(View.GONE);
                    c_two.setVisibility(View.GONE);
                    c_three.setVisibility(View.GONE);

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
                adress_two = et_adress_two.getText().toString();
                adress_three = et_adress_three.getText().toString();
                date = et_date.getText().toString();
                date_two = et_date_two.getText().toString();
                date_three = et_date_three.getText().toString();
                time = et_time.getText().toString();
                time_two = et_time_two.getText().toString();
                time_three = et_time_three.getText().toString();
                tag = et_tag.getText().toString();
                owner = "";
                activities = et_activities.getText().toString();
                activities_two = et_activities_two.getText().toString();
                activities_three = et_activities_three.getText().toString();


                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    owner = user.getDisplayName();
                    emailOwner = user.getEmail();
                }

                if(!one.isChecked() && !two.isChecked() && !three.isChecked()){
                    Snackbar.make(v,"Selecciona cuantas sedes hay", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(one.isChecked()){
                    if(title.equals("") || description.equals("") || adress.equals("") || activities.equals("") || date.equals("") || time.equals("") || tag.equals("")){
                        Snackbar.make(v,"Campos vacíos", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(two.isChecked()){
                    if(title.equals("") || description.equals("") || adress.equals("") || adress_two.equals("") || activities.equals("") || activities_two.equals("") || date.equals("") || date_two.equals("") || time.equals("") || time_two.equals("") || tag.equals("")){
                        Snackbar.make(v,"Campos vacíos", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }


                if(three.isChecked()){
                    if(title.equals("") || description.equals("") || adress.equals("") || adress_two.equals("") || adress_three.equals("") || activities.equals("") || activities_two.equals("") || activities_three.equals("") || date.equals("") || date_two.equals("") || date_three.equals("") || time.equals("") || time_two.equals("") || time_three.equals("") || tag.equals("")){
                        Snackbar.make(v,"Campos vacíos", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                }

                uploadPicture();
            }
        });
    }

    @Override
    public void onClick(View v) {
        final Calendar calendar = Calendar.getInstance();
        if(v == btn_date){
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

        if(v == btn_date_two){
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    et_date_two.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                }
            }
                    ,dia, mes, anio);
            datePicker.show();
        }

        if(v == btn_date_three){
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    et_date_three.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                }
            }
                    ,dia, mes, anio);
            datePicker.show();
        }

        if(v == btn_time){
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

        if(v == btn_time_two){
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minutos = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    et_time_two.setText(hourOfDay + ":" + minute);
                }
            },hora, minutos, false);
            timePicker.show();

        }


        if(v == btn_time_three){
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            minutos = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    et_time_three.setText(hourOfDay + ":" + minute);
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
                    if(one.isChecked()){
                        EventsModel event = new EventsModel(title, description, imageURL, owner, emailOwner, adress, activities, date, time, tag);
                        reference.push().setValue(event);
                        Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
                        startActivity(intent);
                    }

                    if(two.isChecked()){
                        EventsModel event = new EventsModel(title, description, imageURL, owner, emailOwner, adress, activities, date, time, tag);
                        event.setAdrees_two(adress_two);
                        event.setActivities_two(activities_two);
                        event.setDate_two(date_two);
                        event.setTime_two(time_two);
                        reference.push().setValue(event);
                        Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
                        startActivity(intent);
                    }

                    if(three.isChecked()){
                        EventsModel event = new EventsModel(title, description, imageURL, owner, emailOwner, adress, activities, date, time, tag);
                        event.setAdrees_two(adress_two);
                        event.setAdress_three(adress_three);
                        event.setActivities_two(activities_two);
                        event.setActivities_three(activities_three);
                        event.setDate_two(date_two);
                        event.setDate_three(date_three);
                        event.setTime_two(time_two);
                        event.setTime_three(time_three);
                        reference.push().setValue(event);
                        Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
                        startActivity(intent);
                    }

                }
            });

        }else{
            if(one.isChecked()){
                EventsModel event = new EventsModel(title, description, adress, activities, owner, emailOwner, date, time, tag);
                reference.push().setValue(event);
                Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
                startActivity(intent);
            }

            if(two.isChecked()){
                EventsModel event = new EventsModel(title, description, adress, activities, owner, emailOwner, date, time, tag);
                event.setAdrees_two(adress_two);
                event.setActivities_two(activities_two);
                event.setDate_two(date_two);
                event.setTime_two(time_two);
                reference.push().setValue(event);
                Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
                startActivity(intent);
            }

            if(three.isChecked()){
                EventsModel event = new EventsModel(title, description, adress, activities, owner, emailOwner, date, time, tag);
                event.setAdrees_two(adress_two);
                event.setAdress_three(adress_three);
                event.setActivities_two(activities_two);
                event.setActivities_three(activities_three);
                event.setDate_two(date_two);
                event.setDate_three(date_three);
                event.setTime_two(time_two);
                event.setTime_three(time_three);
                reference.push().setValue(event);
                Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
                startActivity(intent);
            }

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
