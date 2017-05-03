package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.project.comlab.comlabapp.R;

public class EventDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_place, tv_date, tv_time;
    JustifiedTextView tv_description;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        tv_title = (TextView) findViewById(R.id.event_title_detail);
        tv_place = (TextView) findViewById(R.id.event_place_detail);
        tv_date = (TextView) findViewById(R.id.event_date_detail);
        tv_time = (TextView) findViewById(R.id.event_time_detail);
        tv_description = (JustifiedTextView) findViewById(R.id.event_description_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            String title = extras.getString("title");
            String place = extras.getString("place");
            String date = extras.getString("date");
            String time = extras.getString("time");
            String description = extras.getString("description");

            tv_title.setText(title);
            tv_place.setText(place);
            tv_description.setText(description);
            tv_date.setText(date);
            tv_time.setText(time);
        }
    }
}
