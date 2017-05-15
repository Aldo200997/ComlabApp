package com.project.comlab.comlabapp.POJO;

import java.util.List;

/**
 * Created by aldodev20 on 24/04/17.
 */

public class NewsModel {

    private String title;
    private String description;
    private String image;
    private String owner;
    private String source;
    private String tag;

    private List<CommentsModel> commentsList;



    public NewsModel(){}

    public NewsModel(String title, String description, String image, String owner, String source, String tag){
        this.title = title;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.source = source;
        this.tag = tag;

    }

    public NewsModel(String title, String description, String image, String owner, String source, String tag, List<CommentsModel> commentsList){
        this.title = title;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.source = source;
        this.tag = tag;
        this.commentsList = commentsList;
    }


    public NewsModel(String title, String description, String image, String owner, String tag){
        this.title = title;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.tag = tag;

    }

    public NewsModel(String title, String description, String owner, String tag){
        this.title = title;
        this.description = description;
        this.owner = owner;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCommentsList(List<CommentsModel> commentsList){
        this.commentsList = commentsList;
    }

    public List<CommentsModel> getCommentsList(){
        return commentsList;
    }


}
