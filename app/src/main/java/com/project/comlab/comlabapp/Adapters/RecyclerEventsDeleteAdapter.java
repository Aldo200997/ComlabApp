package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comlab.comlabapp.Activities.EventDetailActivity;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by aldodev20 on 20/05/17.
 */

public class RecyclerEventsDeleteAdapter extends RecyclerView.Adapter<RecyclerEventsDeleteAdapter.ViewHolder>{

    List<EventsModel> eventsList;
    Activity activity;
    Context context;
    TextInputEditText et_date;
    TextInputEditText et_time;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private DatabaseReference comments;
    private int dia, mes, anio, hora, minutos;


    int[] colors = {R.color.black, R.color.purple, R.color.indigo, R.color.blue, R.color.cyan,
            R.color.green, R.color.yellow, R.color.orange, R.color.brown};

    // Numero random del 0 a 9
    int randomNum = (int)(Math.floor(Math.random() * 10 ));

    public RecyclerEventsDeleteAdapter(Activity activity, Context context, List<EventsModel> eventsList){
        this.eventsList = eventsList;
        this.activity = activity;
        this.context = context;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("events");
        comments = database.getReference("comments").child("events");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delete_events, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(eventsList.get(position).getTitle());
        holder.place.setText(eventsList.get(position).getAdress());
        holder.description.setText(eventsList.get(position).getDescription());
        holder.date.setText(eventsList.get(position).getDate());
        holder.time.setText(eventsList.get(position).getTime());
        holder.image.setBackgroundColor(context.getResources().getColor(colors[(position + randomNum) % 9]));
        holder.rating.setNumStars(5);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EventDetailActivity.class);
                intent.putExtra("key", eventsList.get(position).getKey());
                intent.putExtra("title", eventsList.get(position).getTitle());
                intent.putExtra("place", eventsList.get(position).getAdress());
                intent.putExtra("description", eventsList.get(position).getDescription());
                intent.putExtra("image", eventsList.get(position).getImage());
                intent.putExtra("date", eventsList.get(position).getDate());
                intent.putExtra("time", eventsList.get(position).getTime());
                activity.startActivity(intent);
            }
        });

        holder.delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(eventsList.get(position));
                return true;
            }
        });

        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update(eventsList.get(position));
                return true;
            }
        });
    }

    private void remove(EventsModel event){
        reference.child(event.getKey()).removeValue();
        comments.child(event.getKey()).removeValue();

    }

    private void update(final EventsModel event){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Editar publicacion");
        View view = activity.getLayoutInflater().inflate(R.layout.item_dialog_events, null);



        final Button btn_date = (Button) view.findViewById(R.id.item_dialog_events_date_btn);
        final Button btn_time = (Button) view.findViewById(R.id.item_dialog_events_time_btn);
        final TextInputEditText et_title = (TextInputEditText) view.findViewById(R.id.item_dialog_events_title);
        final TextInputEditText et_description = (TextInputEditText) view.findViewById(R.id.item_dialog_events_description);
        final TextInputEditText et_adress = (TextInputEditText) view.findViewById(R.id.item_dialog_events_adress);
        et_date = (TextInputEditText) view.findViewById(R.id.item_dialog_events_date);
        et_time = (TextInputEditText) view.findViewById(R.id.item_dialog_events_time);


        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePicker = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }
                        ,dia, mes, anio);
                datePicker.show();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                hora = calendar.get(Calendar.HOUR_OF_DAY);
                minutos = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        et_time.setText(hourOfDay + ":" + minute);
                    }
                },hora, minutos, false);
                timePicker.show();
            }
        });

        builder.setCancelable(true);
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                event.setTitle(et_title.getText().toString());
                event.setDescription(et_description.getText().toString());
                event.setAdress(et_adress.getText().toString());
                event.setDate(et_date.getText().toString());
                event.setTime(et_time.getText().toString());
                reference.child(event.getKey()).setValue(event);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setView(view);

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView place;
        TextView description;
        TextView date;
        TextView time;
        ImageView image, delete;
        RatingBar rating;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_delete_events_title);
            place = (TextView) itemView.findViewById(R.id.item_delete_events_place);
            description = (TextView) itemView.findViewById(R.id.item_delete_events_description);
            date = (TextView) itemView.findViewById(R.id.item_delete_events_date);
            time = (TextView) itemView.findViewById(R.id.item_delete_events_time);
            image = (ImageView) itemView.findViewById(R.id.item_delete_events_image);
            delete = (ImageView) itemView.findViewById(R.id.item_delete_events_delete);
            rating = (RatingBar) itemView.findViewById(R.id.item_delete_events_rb);
            cardView = (CardView) itemView.findViewById(R.id.item_delete_events_card);
        }
    }
}
