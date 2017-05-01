package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;

public class AddNewActivity extends AppCompatActivity {

    TextInputEditText et_title, et_description, et_tag;
    Button btn_add;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("news");

        et_title = (TextInputEditText) findViewById(R.id.add_new_title);
        et_description = (TextInputEditText) findViewById(R.id.add_new_description);
        et_tag = (TextInputEditText) findViewById(R.id.add_new_tag);

        btn_add = (Button) findViewById(R.id.add_new_button);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = et_title.getText().toString();
                String description = et_description.getText().toString();
                String tag = et_tag.getText().toString();
                String owner = "";

                if(title.equals("") || description.equals("") || tag.equals("")){
                    Snackbar.make(v,"Campo vacío", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    owner = user.getDisplayName();
                }

                NewsModel noticia = new NewsModel(title, description, owner, tag);

                reference.push().setValue(noticia);
                Intent intent = new Intent(AddNewActivity.this, ContainerActivity.class);
                startActivity(intent);
            }
        });


    }
}
