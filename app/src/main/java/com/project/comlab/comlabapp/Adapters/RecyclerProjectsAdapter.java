package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.project.comlab.comlabapp.Activities.ProjectDetailActivity;
import com.project.comlab.comlabapp.POJO.ProjectsModel;
import com.project.comlab.comlabapp.R;
import com.project.comlab.comlabapp.SearchV.CustomFilterProjects;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by aldodev20 on 13/05/17.
 */

public class RecyclerProjectsAdapter extends RecyclerView.Adapter<RecyclerProjectsAdapter.ViewHolder> implements Filterable{

    public List<ProjectsModel> projectList, filterProjectsList;
    Context context;
    Activity activity;
    CustomFilterProjects filterProjects;

    int[] colors = {R.color.black, R.color.purple, R.color.indigo, R.color.blue, R.color.cyan,
            R.color.green, R.color.yellow, R.color.orange, R.color.brown};

    // Numero random del 0 a 9
    int randomNum = (int)(Math.floor(Math.random() * 10 ));

    public RecyclerProjectsAdapter(Activity activity, Context context, List<ProjectsModel> projectList){
        this.context = context;
        this.projectList = projectList;
        this.filterProjectsList = projectList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_projects, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(projectList.get(position).getTitle());
        holder.description.setText(projectList.get(position).getDescription());
        holder.owner.setText(projectList.get(position).getOwner());
        holder.tag.setText(projectList.get(position).getTag());
        holder.image.setBackgroundColor(context.getResources().getColor(colors[(position + randomNum) % 9]));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProjectDetailActivity.class);
                intent.putExtra("key", projectList.get(position).getKey());
                intent.putExtra("title", projectList.get(position).getTitle());
                intent.putExtra("description", projectList.get(position).getDescription());
                intent.putExtra("owner", projectList.get(position).getOwner());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    @Override
    public Filter getFilter() {
        if(filterProjects == null){
            filterProjects = new CustomFilterProjects(filterProjectsList, this);
        }

        return filterProjects;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView user_image;
        TextView owner;
        ImageView image;
        TextView title;
        TextView description;
        CardView cardView;
        TextView tag;

        public ViewHolder(View itemView) {
            super(itemView);

            user_image = (CircleImageView) itemView.findViewById(R.id.item_project_user_photo);
            owner = (TextView) itemView.findViewById(R.id.item_project_owner);
            image = (ImageView) itemView.findViewById(R.id.item_project_image);
            title = (TextView) itemView.findViewById(R.id.item_project_title);
            description = (TextView) itemView.findViewById(R.id.item_project_description);
            cardView = (CardView) itemView.findViewById(R.id.item_project_card);
            tag = (TextView) itemView.findViewById(R.id.item_projects_tag);
        }
    }
}
