package com.project.comlab.comlabapp.POJO;

/**
 * Created by aldodev20 on 14/05/17.
 */

public class CommentsModel {

    private String text;
    private String owner;

    public CommentsModel(String text, String owner){
        this.text = text;
        this.owner = owner;
    }

    public CommentsModel(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
