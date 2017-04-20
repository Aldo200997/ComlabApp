package com.project.comlab.comlabapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.project.comlab.comlabapp.Adapters.RecyclerInterestedAdapter;
import com.project.comlab.comlabapp.POJO.InterestedModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

public class InterestedActivity extends AppCompatActivity {

    RecyclerView rv_interested;
    List<InterestedModel> interestList;
    RecyclerInterestedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested);

        rv_interested = (RecyclerView) findViewById(R.id.rv_interested);
        rv_interested.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        initializeData();
        adapter = new RecyclerInterestedAdapter(interestList);
        rv_interested.setAdapter(adapter);



    }

    private void initializeData(){
        interestList = new ArrayList<>();
        interestList.add(new InterestedModel("Machine Learning", R.drawable.ml));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.bd));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.ml));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.bd));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.ml));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.bd));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.ml));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.bd));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.ml));
        interestList.add(new InterestedModel("Machine Learning", R.drawable.bd));
    }
}
