package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

public class NewDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_owner;
    JustifiedTextView tv_description;
    ImageView iv_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

        tv_title = (TextView) findViewById(R.id.new_title_detail);
        tv_owner = (TextView) findViewById(R.id.new_owner_detail);
        tv_description = (JustifiedTextView) findViewById(R.id.new_description_detail);
        iv_image = (ImageView) findViewById(R.id.new_image_detail);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            String title = extras.getString("title");
            String owner = extras.getString("owner");
            String description = extras.getString("description");
            String image = extras.getString("image");

            tv_title.setText(title);
            tv_owner.setText(owner);
            tv_description.setText(description);
            Picasso.with(getApplicationContext()).load(image).into(iv_image);

        }
    }
}
