package com.project.comlab.comlabapp.POJO;

/**
 * Created by aldodev20 on 14/05/17.
 */

public class CommentsModel {

    private String key;
    private String text;
    private String emailOwner;

    public CommentsModel(){}

    public CommentsModel(String text, String emailOwner){
        this.text = text;
        this.emailOwner = emailOwner;
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

    public String getEmailOwner() {
        return emailOwner;
    }

    public void setEmailOwner(String emailOwner) {
        this.emailOwner = emailOwner;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValues(CommentsModel comments){
        this.text = comments.text;
    }
}
