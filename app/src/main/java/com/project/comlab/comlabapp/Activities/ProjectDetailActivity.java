package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codesgood.views.JustifiedTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comlab.comlabapp.Adapters.RecyclerCommentsAdapter;
import com.project.comlab.comlabapp.POJO.CommentsModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_owner;
    JustifiedTextView tv_description;
    List<CommentsModel> commentsList;
    TextInputEditText et_comment;
    Button btn_comment;
    RecyclerView rv;
    RecyclerCommentsAdapter adapter;
    String key = "";

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("comments").child("projects");
        mAuth = FirebaseAuth.getInstance();

        tv_title = (TextView) findViewById(R.id.project_title_detail);
        tv_owner = (TextView) findViewById(R.id.project_owner_detail);
        tv_description = (JustifiedTextView) findViewById(R.id.project_description_detail);
        rv = (RecyclerView) findViewById(R.id.project_rv_comments);
        et_comment = (TextInputEditText) findViewById(R.id.project_et_comment_detail);
        btn_comment = (Button) findViewById(R.id.project_btn_comment_detail);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        commentsList = new ArrayList<>();
        adapter = new RecyclerCommentsAdapter(ProjectDetailActivity.this, getApplicationContext(), commentsList);
        rv.setAdapter(adapter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null){
            key = extras.getString("key");
            String title = extras.getString("title");
            String description = extras.getString("description");
            String owner = extras.getString("owner");

            tv_title.setText(title);
            tv_description.setText(description);
            tv_owner.setText(owner);
        }

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et_comment.getText().toString();
                if(text.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo de comentario vacio", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = mAuth.getCurrentUser();
                String emailOwner = user.getEmail();
                CommentsModel comment = new CommentsModel(text, emailOwner);
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
