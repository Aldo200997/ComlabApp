package com.project.comlab.comlabapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.comlab.comlabapp.POJO.CommentsModel;
import com.project.comlab.comlabapp.R;

import java.util.List;

/**
 * Created by aldodev20 on 14/05/17.
 */

public class RecyclerCommentsAdapter extends RecyclerView.Adapter<RecyclerCommentsAdapter.ViewHolder> {

    List<CommentsModel> commentsList;

    public RecyclerCommentsAdapter(List<CommentsModel> commentsList){
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
        holder.text.setText(commentsList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView username, text;

        public ViewHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.item_comments_user);
            text = (TextView) itemView.findViewById(R.id.item_comments_text);
        }
    }

    
}
