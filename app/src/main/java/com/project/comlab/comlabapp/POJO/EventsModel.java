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
    private String adrees_two;
    private String adress_three;
    private String activities;
    private String activities_two;
    private String activities_three;
    private String date;
    private String date_two;
    private String date_three;
    private String time;
    private String time_two;
    private String time_three;
    private String tag;
    private String key;

    public EventsModel(){}

    // Con imagen

    public EventsModel(String title, String description, String image, String owner, String emailOwner, String adress, String activities, String date, String time, String tag){
        this.title = title;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.emailOwner = emailOwner;
        this.adress = adress;
        this.activities = activities;
        this.date = date;
        this.time = time;
        this.tag = tag;
    }



    // Sin imagen
    public EventsModel(String title, String description, String adress, String activities, String owner, String emailOwner, String date, String time, String tag){
        this.title = title;
        this.description = description;
        this.adress = adress;
        this.activities = activities;
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

    public String getAdrees_two() {
        return adrees_two;
    }

    public void setAdrees_two(String adrees_two) {
        this.adrees_two = adrees_two;
    }

    public String getAdress_three() {
        return adress_three;
    }

    public void setAdress_three(String adress_three) {
        this.adress_three = adress_three;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getActivities_two() {
        return activities_two;
    }

    public void setActivities_two(String activities_two) {
        this.activities_two = activities_two;
    }

    public String getActivities_three() {
        return activities_three;
    }

    public void setActivities_three(String activities_three) {
        this.activities_three = activities_three;
    }

    public String getDate_two() {
        return date_two;
    }

    public void setDate_two(String date_two) {
        this.date_two = date_two;
    }

    public String getDate_three() {
        return date_three;
    }

    public void setDate_three(String date_three) {
        this.date_three = date_three;
    }

    public String getTime_two() {
        return time_two;
    }

    public void setTime_two(String time_two) {
        this.time_two = time_two;
    }

    public String getTime_three() {
        return time_three;
    }

    public void setTime_three(String time_three) {
        this.time_three = time_three;
    }

    public void setValues(EventsModel event){
        this.title = event.title;
        this.description = event.description;
        this.adress = event.adress;
        this.date = event.date;
        this.time = event.time;
    }

}
