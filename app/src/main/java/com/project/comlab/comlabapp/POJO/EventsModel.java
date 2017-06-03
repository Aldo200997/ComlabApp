package com.project.comlab.comlabapp.POJO;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ThrowOnExtraProperties;

import java.util.List;

/**
 * Created by aldodev20 on 30/04/17.
 */


public class EventsModel {

    private String title;
    private String description;
    private String image;
    private String owner;
    private String emailOwner;
    private String adress;
    private String date;
    private String time;
    private String tag;
    private String key;

    public EventsModel(){}

    public EventsModel(String title, String description, String image, String owner, String emailOwner, String adress, String date, String time, String tag){
        this.title = title;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.emailOwner = emailOwner;
        this.adress = adress;
        this.date = date;
        this.time = time;
        this.tag = tag;
    }

    public EventsModel(String title, String description, String adress, String owner, String emailOwner, String date, String time, String tag){
        this.title = title;
        this.description = description;
        this.adress = adress;
        this.owner = owner;
        this.emailOwner = emailOwner;
        this.date = date;
        this.time = time;
        this.tag = tag;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getEmailOwner() {
        return emailOwner;
    }

    public void setEmailOwner(String emailOwner) {
        this.emailOwner = emailOwner;
    }

    public String getOwner(){
        return owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValues(EventsModel event){
        this.title = event.title;
        this.description = event.description;
        this.adress = event.adress;
        this.date = event.date;
        this.time = event.time;
    }

}
