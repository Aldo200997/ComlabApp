package com.project.comlab.comlabapp.POJO;

/**
 * Created by aldodev20 on 30/04/17.
 */

public class ProjectsModel {

    private String title;
    private String description;
    private String image;
    private String owner;
    private String documentation;
    private String tag;
    private String key;

    public ProjectsModel(){}

    public ProjectsModel(String title, String description, String image, String owner, String tag){
        this.title = title;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.tag = tag;
    }

    public ProjectsModel(String title, String description, String owner, String tag){
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

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
