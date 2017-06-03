package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.comlab.comlabapp.POJO.CommentsModel;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;

import java.util.List;

/**
 * Created by aldodev20 on 14/05/17.
 */

public class RecyclerCommentsAdapter extends RecyclerView.Adapter<RecyclerCommentsAdapter.ViewHolder> {

    List<CommentsModel> commentsList;
    Context context;
    Activity activity;

    public RecyclerCommentsAdapter(Activity activity, Context context, List<CommentsModel> commentsList){
        this.activity = activity;
        this.context = context;
        this.commentsList = commentsList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comments, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.emailOwner.setText(commentsList.get(position).getEmailOwner());
        holder.text.setText(commentsList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView emailOwner, text;

        public ViewHolder(View itemView) {
            super(itemView);

            emailOwner = (TextView) itemView.findViewById(R.id.item_comments_user);
            text = (TextView) itemView.findViewById(R.id.item_comments_text);
        }
    }

    
}
