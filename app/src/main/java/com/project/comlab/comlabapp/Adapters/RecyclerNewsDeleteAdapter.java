package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comlab.comlabapp.Activities.NewDetailActivity;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aldodev20 on 20/05/17.
 */

public class RecyclerNewsDeleteAdapter extends RecyclerView.Adapter<RecyclerNewsDeleteAdapter.ViewHolder> {


    List<NewsModel> newsList;
    Activity activity;
    Context context;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    int[] colors = {R.color.black, R.color.purple, R.color.indigo, R.color.blue, R.color.cyan,
            R.color.green, R.color.yellow, R.color.orange, R.color.brown};

    // Numero random del 0 a 9
    int randomNum = (int)(Math.floor(Math.random() * 10 ));

    public RecyclerNewsDeleteAdapter(Activity activity, Context context, List<NewsModel> newsList){
        this.activity = activity;
        this.context = context;
        this.newsList = newsList;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("news");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(newsList.get(position).getTitle());
        holder.description.setText(newsList.get(position).getDescription());
        //Picasso.with(context).load(newsList.get(position).getImage()).into(holder.image);
        holder.image.setBackgroundColor(context.getResources().getColor(colors[(position + randomNum) % 9]));
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NewDetailActivity.class);
                intent.putExtra("title", newsList.get(position).getTitle());
                intent.putExtra("description", newsList.get(position).getDescription());
                intent.putExtra("owner", newsList.get(position).getOwner());
                intent.putExtra("image", newsList.get(position).getImage());
                intent.putExtra("comments", (Serializable) newsList.get(position).getCommentsList());
                activity.startActivity(intent);
            }
        });

        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(newsList.get(position));
                return true;
            }
        });
    }

    public void remove(NewsModel news){
        reference.child(news.getKey()).removeValue();
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

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_news_title);
            description = (TextView) itemView.findViewById(R.id.item_news_description);
            cardview = (CardView) itemView.findViewById(R.id.item_news_card);
            image = (ImageView) itemView.findViewById(R.id.item_news_image);
        }
    }
}
