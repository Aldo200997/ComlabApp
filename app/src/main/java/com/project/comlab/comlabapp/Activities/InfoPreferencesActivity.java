package com.project.comlab.comlabapp.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.comlab.comlabapp.Adapters.RecyclerPreferencesAdapter;
import com.project.comlab.comlabapp.MainActivity;
import com.project.comlab.comlabapp.POJO.PreferencesModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

public class InfoPreferencesActivity extends AppCompatActivity {


    TextView first;
    TextView second;
    TextView third;
    Button btn;
    RecyclerView rv;
    RecyclerPreferencesAdapter adapter;
    List<PreferencesModel> preferencesList;
    static int lastPosition = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_preferences);

        first = (TextView) findViewById(R.id.first_message);
        second = (TextView) findViewById(R.id.second_message);
        third = (TextView) findViewById(R.id.third_message);
        btn = (Button) findViewById(R.id.info_btn);

        LinearLayout ll = (LinearLayout) findViewById(R.id.info_linear);

        setAnimation(ll);

        Typeface face = Typeface.createFromAsset(getAssets(),"font/letrados.ttf");
        first.setTypeface(face);
        second.setTypeface(face);
        third.setTypeface(face);


        rv = (RecyclerView) findViewById(R.id.rv_preferences);
        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        initializeData();
        adapter = new RecyclerPreferencesAdapter(getApplicationContext(), preferencesList);
        rv.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoPreferencesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeData(){
        preferencesList = new ArrayList<>();
        preferencesList.add(new PreferencesModel("Realidad Aumentada", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Far.png?alt=media&token=2cd2ced6-ffc8-4eec-96bb-15c400c8de65"));
        preferencesList.add(new PreferencesModel("Realidad Virtual", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fvr.png?alt=media&token=edc12547-2edf-43e2-84cf-b5e498c5318b"));
        preferencesList.add(new PreferencesModel("Videojuegos", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fvideogames.png?alt=media&token=5d096f99-d827-4e9d-bb33-1c9072b7c35a"));
        preferencesList.add(new PreferencesModel("Machine Learning", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fmachinelearning.png?alt=media&token=bd9e1583-a591-4597-bd3a-fdc311c81aa8"));
        preferencesList.add(new PreferencesModel("Big data", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fbigdata.png?alt=media&token=b0b87469-d455-4dc6-8280-8c818141bb61"));
        preferencesList.add(new PreferencesModel("Internet of Things", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fiot.png?alt=media&token=b8431287-8058-4a71-967e-49b9ca9f1124"));
        preferencesList.add(new PreferencesModel("Movilidad", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fmobile.png?alt=media&token=866d64c1-cf4b-4ea0-a719-3d1e7e2111d4"));
        preferencesList.add(new PreferencesModel("Web", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fweb.png?alt=media&token=3f7eba7c-a80f-43b1-b967-6c75afa7affe"));
        preferencesList.add(new PreferencesModel("Ecommerce", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fecommerce.png?alt=media&token=affdde1e-bd18-4706-9e2f-1b60a9917d76"));
        preferencesList.add(new PreferencesModel("Emprendimiento", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fbusiness.png?alt=media&token=a016fde3-3466-403d-9f51-b9edd62e657f"));
        preferencesList.add(new PreferencesModel("Seguridad informática", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fsecurity.png?alt=media&token=5b6932c6-b1aa-4d11-b1fe-18d3f2ca9923"));
        preferencesList.add(new PreferencesModel("Muchos más", "https://firebasestorage.googleapis.com/v0/b/comlabapplication.appspot.com/o/profileImages%2Fothers.png?alt=media&token=33c54a6c-4dd3-4515-9f6a-47ddd2a6a17a"));
    }

    private void setAnimation(View viewToAnimate){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animator);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {

    }
}
