package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.project.comlab.comlabapp.Adapters.RecyclerCommentsAdapter;
import com.project.comlab.comlabapp.POJO.CommentsModel;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_owner;
    JustifiedTextView tv_description;
    ImageView iv_image;
    RecyclerView rv_comments;
    RecyclerCommentsAdapter adapter;
    TextInputEditText et_comment;
    Button btn_comment;
    List<CommentsModel> commentsList;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    String key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("news");
        mAuth = FirebaseAuth.getInstance();

        tv_title = (TextView) findViewById(R.id.new_title_detail);
        tv_owner = (TextView) findViewById(R.id.new_owner_detail);
        tv_description = (JustifiedTextView) findViewById(R.id.new_description_detail);
        iv_image = (ImageView) findViewById(R.id.new_image_detail);
        rv_comments = (RecyclerView) findViewById(R.id.new_rv_comments);
        et_comment = (TextInputEditText) findViewById(R.id.new_et_comment_detail);
        btn_comment = (Button) findViewById(R.id.new_btn_comment_detail);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        rv_comments.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        commentsList = new ArrayList<>();

        adapter = new RecyclerCommentsAdapter(commentsList);

        rv_comments.setAdapter(adapter);

        if(extras != null){
            key = extras.getString("key");
            String title = extras.getString("title");
            String owner = extras.getString("owner");
            String description = extras.getString("description");
            String image = extras.getString("image");

            tv_title.setText(title);
            tv_owner.setText(owner);
            tv_description.setText(description);
            Picasso.with(getApplicationContext()).load(image).into(iv_image);

        }

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment = et_comment.getText().toString();

                if(comment.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo de comentario vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = mAuth.getCurrentUser();
                String emailOwner = user.getEmail();
                CommentsModel commentModel = new CommentsModel(comment, emailOwner);
                reference.child(key).child("comments").push().setValue(commentModel);
            }
        });




         reference.child(key).child("comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CommentsModel commentsModel = dataSnapshot.getValue(CommentsModel.class);
                commentsModel.setKey(dataSnapshot.getKey());
                commentsList.add(commentsModel);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                CommentsModel commentsModel = dataSnapshot.getValue(CommentsModel.class);
                for (CommentsModel cm: commentsList) {
                    if(cm.getKey().equals(key)){
                        cm.setValues(commentsModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (CommentsModel cm: commentsList) {
                    if(cm.getKey().equals(key)){
                        commentsList.remove(cm);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
