package com.project.comlab.comlabapp.POJO;

/**
 * Created by aldodev20 on 19/04/17.
 */

public class PreferencesModel {

    public String title;
    public String photo;

    public PreferencesModel(String title, String photo){
        this.title = title;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String  getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
