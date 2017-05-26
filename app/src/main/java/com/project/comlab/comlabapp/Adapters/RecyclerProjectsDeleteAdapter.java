package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.comlab.comlabapp.Activities.ProjectDetailActivity;
import com.project.comlab.comlabapp.POJO.ProjectsModel;
import com.project.comlab.comlabapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by aldodev20 on 25/05/17.
 */

public class RecyclerProjectsDeleteAdapter extends RecyclerView.Adapter<RecyclerProjectsDeleteAdapter.ViewHolder> {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    Activity activity;
    Context context;
    List<ProjectsModel> projectList;

    int[] colors = {R.color.black, R.color.purple, R.color.indigo, R.color.blue, R.color.cyan,
            R.color.green, R.color.yellow, R.color.orange, R.color.brown};

    // Numero random del 0 a 9
    int randomNum = (int)(Math.floor(Math.random() * 10 ));

    public RecyclerProjectsDeleteAdapter(Activity activity, Context context, List<ProjectsModel> projectList){
        this.activity = activity;
        this.context = context;
        this.projectList = projectList;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("projects");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_delete_projects, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(projectList.get(position).getTitle());
        holder.description.setText(projectList.get(position).getDescription());
        holder.owner.setText(projectList.get(position).getOwner());
        holder.image.setBackgroundColor(context.getResources().getColor(colors[(position + randomNum) % 9]));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProjectDetailActivity.class);
                intent.putExtra("title", projectList.get(position).getTitle());
                intent.putExtra("description", projectList.get(position).getDescription());
                intent.putExtra("owner", projectList.get(position).getOwner());
                activity.startActivity(intent);
            }
        });

        holder.delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(projectList.get(position));
                return true;
            }
        });

        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                update(projectList.get(position));
                return true;
            }
        });




    }

    private void remove(ProjectsModel projects){
        reference.child(projects.getKey()).removeValue();
    }

    private void update(final ProjectsModel projects){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Editar publicacion");
        View view = activity.getLayoutInflater().inflate(R.layout.item_dialog_projects, null);

        final TextInputEditText et_title = (TextInputEditText) view.findViewById(R.id.item_dialog_projects_title);
        final TextInputEditText et_description = (TextInputEditText) view.findViewById(R.id.item_dialog_projects_description);
        final TextInputEditText et_documentation = (TextInputEditText) view.findViewById(R.id.item_dialog_projects_documentation);


        builder.setCancelable(true);
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                projects.setTitle(et_title.getText().toString());
                projects.setDescription(et_description.getText().toString());
                projects.setDocumentation(et_documentation.getText().toString());
                reference.child(projects.getKey()).setValue(projects);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView user_image;
        TextView owner;
        ImageView image, delete;
        TextView title;
        TextView description;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            user_image = (CircleImageView) itemView.findViewById(R.id.item_project_delete_user_photo);
            owner = (TextView) itemView.findViewById(R.id.item_project_delete_owner);
            image = (ImageView) itemView.findViewById(R.id.item_project_delete_image);
            title = (TextView) itemView.findViewById(R.id.item_project_delete_title);
            description = (TextView) itemView.findViewById(R.id.item_project_delete_description);
            cardView = (CardView) itemView.findViewById(R.id.item_project_delete_card);
            delete = (ImageView) itemView.findViewById(R.id.item_delete_projects_delete);
        }
    }
}
