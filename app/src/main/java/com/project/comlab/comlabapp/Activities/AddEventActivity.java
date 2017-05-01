package com.project.comlab.comlabapp.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.R;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    TextInputEditText et_title, et_description, et_adress, et_date, et_time, et_tag;
    Button btn_date, btn_time;
    Button btn_event;
    private int dia, mes, anio, hora, minutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("events");

        btn_date = (Button) findViewById(R.id.add_event_date_btn);
        btn_time = (Button) findViewById(R.id.add_event_time_btn);
        btn_date.setOnClickListener(this);
        btn_time.setOnClickListener(this);

        et_title = (TextInputEditText) findViewById(R.id.add_event_title);
        et_description = (TextInputEditText) findViewById(R.id.add_event_description);
        et_adress = (TextInputEditText) findViewById(R.id.add_event_adress);
        et_date = (TextInputEditText) findViewById(R.id.add_event_date);
        et_time = (TextInputEditText) findViewById(R.id.add_event_time);
        et_tag = (TextInputEditText) findViewById(R.id.add_event_tag);

        btn_event = (Button) findViewById(R.id.add_event_btn);
        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = et_title.getText().toString();
                String description = et_description.getText().toString();
                String adress = et_adress.getText().toString();
                String date = et_date.getText().toString();
                String time = et_time.getText().toString();
                String tag = et_tag.getText().toString();
                String owner = "";

                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    owner = user.getDisplayName();
                }

                if(title.equals("") || description.equals("") || adress.equals("") || date.equals("") || time.equals("") || tag.equals("")){
                    Snackbar.make(v,"Campo vacío", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                EventsModel event = new EventsModel(title, description, adress, owner, date, time, tag);
                reference.push().setValue(event);
                Intent intent = new Intent(AddEventActivity.this, ContainerActivity.class);
                startActivity(intent);
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
}
