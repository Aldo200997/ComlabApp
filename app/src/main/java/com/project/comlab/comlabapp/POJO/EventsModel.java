package com.project.comlab.comlabapp.POJO;

/**
 * Created by aldodev20 on 30/04/17.
 */

public class EventsModel {

    private String title;
    private String description;
    private String image;
    private String owner;
    private String adress;
    private String date;
    private String time;
    private String tag;

    public EventsModel(){}

    public EventsModel(String title, String description, String image, String owner, String adress, String date, String time, String tag){
        this.title = title;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.adress = adress;
        this.date = date;
        this.time = time;
        this.tag = tag;
    }

    public EventsModel(String title, String description, String adress, String owner, String date, String time, String tag){
        this.title = title;
        this.description = description;
        this.adress = adress;
        this.owner = owner;
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

    public String getOwner(){
        return owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }
}
