package com.project.comlab.comlabapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.comlab.comlabapp.POJO.ItemsProfileModel;
import com.project.comlab.comlabapp.ProfileConfig.EditProfileActivity;
import com.project.comlab.comlabapp.ProfileConfig.MyEventsActivity;
import com.project.comlab.comlabapp.ProfileConfig.MyNewsActivity;
import com.project.comlab.comlabapp.ProfileConfig.MyProjectsActivity;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldodev20 on 18/05/17.
 */

public class RecyclerProfileAdapter extends RecyclerView.Adapter<RecyclerProfileAdapter.ViewHolder> {

    List<ItemsProfileModel> itemList;
    Context context;
    Activity activity;

    int[] colors = {R.color.black, R.color.purple, R.color.indigo, R.color.blue, R.color.cyan,
            R.color.green, R.color.yellow, R.color.orange, R.color.brown};

    // Numero random del 0 a 9
    int randomNum = (int)(Math.floor(Math.random() * 10 ));

    public RecyclerProfileAdapter(Activity activity, Context context, List<ItemsProfileModel> itemList){
        this.itemList = itemList;
        this.context = context;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(itemList.get(position).getName());
        holder.frame.setBackgroundColor(context.getResources().getColor(colors[(position + randomNum) % 9]));
        Picasso.with(context).load(itemList.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        FrameLayout frame;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_profile_name);
            image = (ImageView) itemView.findViewById(R.id.item_profile_image);
            frame = (FrameLayout) itemView.findViewById(R.id.item_profile_frame);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    switch (getPosition()){
                        case 0:
                            intent = new Intent(activity, EditProfileActivity.class);
                            activity.startActivity(intent);
                            break;

                        case 1:
                            intent = new Intent(activity, MyNewsActivity.class);
                            activity.startActivity(intent);
                            break;


                        case  2:
                            intent = new Intent(activity, MyEventsActivity.class);
                            activity.startActivity(intent);
                            break;

                        case 3:
                            intent = new Intent(activity, MyProjectsActivity.class);
                            activity.startActivity(intent);
                            break;



                    }
                }
            });

        }
    }
}
