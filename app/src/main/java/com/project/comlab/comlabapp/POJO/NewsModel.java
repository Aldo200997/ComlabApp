package com.project.comlab.comlabapp.POJO;

/**
 * Created by aldodev20 on 24/04/17.
 */

public class NewsModel {

    private String name;
    private String description;

    public NewsModel(){}

    public NewsModel(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
}
