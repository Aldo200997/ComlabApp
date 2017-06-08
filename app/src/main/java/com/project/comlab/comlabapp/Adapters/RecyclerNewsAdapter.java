package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.codesgood.views.JustifiedTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comlab.comlabapp.Activities.NewDetailActivity;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;
import java.util.Random;
import java.util.Timer;



/**
 * Created by aldodev20 on 24/04/17.
 */

public class RecyclerNewsAdapter extends RecyclerView.Adapter<RecyclerNewsAdapter.ViewHolder> {

    List<NewsModel> newsList;
    Activity activity;
    Context context;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    boolean checked = false;


    int[] colors = {R.color.black, R.color.purple, R.color.indigo, R.color.blue, R.color.cyan,
            R.color.green, R.color.yellow, R.color.orange, R.color.brown};

    // Numero random del 0 a 9
    int randomNum = (int)(Math.floor(Math.random() * 10 ));




    public RecyclerNewsAdapter(Activity activity, Context context, List<NewsModel> newsList){
        this.newsList = newsList;
        this.activity = activity;
        this.context = context;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("news");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(newsList.get(position).getTitle());
        holder.description.setText(newsList.get(position).getDescription());
        //Picasso.with(context).load(newsList.get(position).getImage()).into(holder.image);
        holder.image.setBackgroundColor(context.getResources().getColor(colors[(position + randomNum) % 9]));
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NewDetailActivity.class);
                intent.putExtra("key", newsList.get(position).getKey());
                intent.putExtra("title", newsList.get(position).getTitle());
                intent.putExtra("description", newsList.get(position).getDescription());
                intent.putExtra("owner", newsList.get(position).getOwner());
                intent.putExtra("image", newsList.get(position).getImage());
                //intent.putExtra("comments", (Serializable) newsList.get(position).getCommentsList());
                activity.startActivity(intent);
            }
        });



        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(holder.like.isChecked()){
                    int numLikes = newsList.get(position).getLikes() + 1;
                    reference.child(newsList.get(position).getKey()).child("likes").setValue(numLikes);
                    newsList.get(position).setLikes(numLikes);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        ImageView image;
        CardView cardview;
        CheckBox like;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_news_title);
            description = (TextView) itemView.findViewById(R.id.item_news_description);
            cardview = (CardView) itemView.findViewById(R.id.item_news_card);
            image = (ImageView) itemView.findViewById(R.id.item_news_image);
            like = (CheckBox) itemView.findViewById(R.id.item_news_checkbox_like);
        }
    }
}
