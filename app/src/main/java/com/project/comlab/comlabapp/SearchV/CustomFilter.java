package com.project.comlab.comlabapp.SearchV;

import android.widget.Filter;

import com.project.comlab.comlabapp.Adapters.RecyclerNewsAdapter;
import com.project.comlab.comlabapp.POJO.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldodev20 on 08/06/17.
 */

public class CustomFilter extends Filter {

    RecyclerNewsAdapter adapter;
    List<NewsModel> newsList;

    public CustomFilter(List<NewsModel> newsList, RecyclerNewsAdapter adapter){
        this.newsList = newsList;
        this.adapter = adapter;
    }

    // Filtrado de datos
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        // Si hay texto en el searchview
        if(constraint != null && constraint.length() > 0){

            constraint = constraint.toString().toUpperCase();
            List<NewsModel> newsFilterList = new ArrayList<>();

            for (int i = 0; i < newsList.size(); i++){

                // Si en la lista general el titulo de una pub esta contenido en el texto del searchview
                if(newsList.get(i).getTitle().toUpperCase().contains(constraint) || newsList.get(i).getTag().toUpperCase().contains(constraint)){
                    // Entonces a la lista especifica agregarle lo que trajo la lista general
                    newsFilterList.add(newsList.get(i));
                }
            }

            results.count = newsFilterList.size();
            results.values = newsFilterList;

        }else{

            // Si no regresa la lista original
            results.count = newsList.size();
            results.values = newsList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.newsList = (List<NewsModel>) results.values;
        adapter.notifyDataSetChanged();
    }
}
