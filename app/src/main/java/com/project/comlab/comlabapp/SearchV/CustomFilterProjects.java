package com.project.comlab.comlabapp.SearchV;

import android.widget.Filter;

import com.project.comlab.comlabapp.Adapters.RecyclerProjectsAdapter;
import com.project.comlab.comlabapp.POJO.ProjectsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldodev20 on 09/06/17.
 */

public class CustomFilterProjects extends Filter {

    List<ProjectsModel> projectsList;
    RecyclerProjectsAdapter adapter;

    public CustomFilterProjects(List<ProjectsModel> projectsList, RecyclerProjectsAdapter adapter){
        this.projectsList = projectsList;
        this.adapter = adapter;

    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            List<ProjectsModel> projectsFilterList = new ArrayList<>();

            for(int i = 0; i < projectsList.size(); i++){
                if(projectsList.get(i).getTitle().toUpperCase().contains(constraint) || projectsList.get(i).getTag().toUpperCase().contains(constraint)){
                    projectsFilterList.add(projectsList.get(i));
                }
            }

            results.count = projectsFilterList.size();
            results.values = projectsFilterList;
        }else{
            results.count = projectsList.size();
            results.values = projectsList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.projectList = (List<ProjectsModel>) results.values;
        adapter.notifyDataSetChanged();
    }
}
