package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesgood.views.JustifiedTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comlab.comlabapp.Adapters.RecyclerCommentsAdapter;
import com.project.comlab.comlabapp.POJO.CommentsModel;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_place, tv_place_two, tv_place_three, tv_date, tv_date_two, tv_date_three, tv_time, tv_time_two, tv_time_three;
    JustifiedTextView tv_description;
    ImageView iv_image;
    Button btn_comment;
    TextInputEditText et_comment;

    String key = "";
    RecyclerView rv_comments;
    RecyclerCommentsAdapter adapter;
    List<CommentsModel> commentsList;

    CardView one, two, three;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("comments").child("events");
        mAuth = FirebaseAuth.getInstance();

        tv_title = (TextView) findViewById(R.id.event_title_detail);
        tv_place = (TextView) findViewById(R.id.event_place_detail);
        tv_place_two = (TextView) findViewById(R.id.event_place_two_detail);
        tv_place_three = (TextView) findViewById(R.id.event_place_three_detail);
        tv_date = (TextView) findViewById(R.id.event_date_detail);
        tv_date_two = (TextView) findViewById(R.id.event_date_two_detail);
        tv_date_three = (TextView) findViewById(R.id.event_date_three_detail);
        tv_time = (TextView) findViewById(R.id.event_time_detail);
        tv_time_two = (TextView) findViewById(R.id.event_time_two_detail);
        tv_time_three = (TextView) findViewById(R.id.event_time_three_detail);
        tv_description = (JustifiedTextView) findViewById(R.id.event_description_detail);
        iv_image = (ImageView) findViewById(R.id.event_image_detail);
        rv_comments = (RecyclerView) findViewById(R.id.event_rv_comments_detail);
        et_comment = (TextInputEditText) findViewById(R.id.event_et_comment_detail);
        btn_comment = (Button) findViewById(R.id.event_btn_comment_detail);

        one = (CardView) findViewById(R.id.card_detail_one);
        two = (CardView) findViewById(R.id.card_detail_two);
        three = (CardView) findViewById(R.id.card_detail_three);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        rv_comments.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        commentsList = new ArrayList<>();
        adapter = new RecyclerCommentsAdapter(EventDetailActivity.this, getApplicationContext(),commentsList);
        rv_comments.setAdapter(adapter);


        if(extras != null){
            key = extras.getString("key");
            String title = extras.getString("title");
            String place = extras.getString("place");
            String place_two = extras.getString("place_two");
            String place_three = extras.getString("place_three");
            String date = extras.getString("date");
            String date_two = extras.getString("date_two");
            String date_three = extras.getString("date_three");
            String time = extras.getString("time");
            String time_two = extras.getString("time_two");
            String time_three = extras.getString("time_three");
            String description = extras.getString("description");
            String image = extras.getString("image");

            if(place != null  && place_two == null && place_three == null){
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
            }

            else if(place != null && place_two != null && place_three == null){
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.GONE);
            }

            else if(place != null && place_two != null && place_three != null){
                one.setVisibility(View.VISIBLE);
                two.setVisibility(View.VISIBLE);
                three.setVisibility(View.VISIBLE);
            }

            else{
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);
                three.setVisibility(View.GONE);
            }





            tv_title.setText(title);
            tv_place.setText(place);
            tv_place_two.setText(place_two);
            tv_place_three.setText(place_three);
            tv_description.setText(description);
            tv_date.setText(date);
            tv_date_two.setText(date_two);
            tv_date_three.setText(date_three);
            tv_time.setText(time);
            tv_time_two.setText(time_two);
            tv_time_three.setText(time_three);
            Picasso.with(getApplicationContext()).load(image).into(iv_image);
        }

         btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = et_comment.getText().toString();

                if(text.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo de comentario vacío", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = mAuth.getCurrentUser();
                String eOwner = user.getEmail();
                CommentsModel comment = new CommentsModel(text, eOwner);
                reference.child(key).push().setValue(comment);

            }
        });


         reference.child(key).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 commentsList.removeAll(commentsList);
                 for (DataSnapshot snap: dataSnapshot.getChildren()) {
                     CommentsModel comment = snap.getValue(CommentsModel.class);
                     commentsList.add(comment);
                 }

                 adapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });




    }
}
