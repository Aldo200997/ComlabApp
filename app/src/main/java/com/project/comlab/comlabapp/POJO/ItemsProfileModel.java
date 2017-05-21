package com.project.comlab.comlabapp.POJO;

/**
 * Created by aldodev20 on 18/05/17.
 */

public class ItemsProfileModel {

    private String name;
    private String image;

    public ItemsProfileModel(String name, String image){
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
