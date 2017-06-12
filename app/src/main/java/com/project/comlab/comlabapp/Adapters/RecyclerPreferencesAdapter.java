package com.project.comlab.comlabapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.comlab.comlabapp.POJO.PreferencesModel;
import com.project.comlab.comlabapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aldodev20 on 19/04/17.
 */

public class RecyclerPreferencesAdapter extends RecyclerView.Adapter<RecyclerPreferencesAdapter.ViewHolder> {
    List<PreferencesModel> preferencesList;
    Context context;

    int[] colors = {R.color.black, R.color.purple, R.color.indigo, R.color.blue, R.color.cyan,
            R.color.green, R.color.yellow, R.color.orange, R.color.brown};

    // Numero random del 0 a 9
    int randomNum = (int)(Math.floor(Math.random() * 10 ));


    public RecyclerPreferencesAdapter(Context context, List<PreferencesModel> interestedList){
        this.preferencesList = interestedList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_preferences, parent, false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(preferencesList.get(position).getTitle());
        Picasso.with(context).load(preferencesList.get(position).getPhoto()).into(holder.image);
        holder.fl.setBackgroundColor(context.getResources().getColor(colors[(position + randomNum) % 9]));
    }

    @Override
    public int getItemCount() {
        return preferencesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView image;
        FrameLayout fl;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_preferences_name);
            image = (ImageView) itemView.findViewById(R.id.item_preferences_image);
            fl = (FrameLayout) itemView.findViewById(R.id.item_preferences_frame);
        }
    }
}
