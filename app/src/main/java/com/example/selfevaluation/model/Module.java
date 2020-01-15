
package com.example.selfevaluation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Module {

    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = null;
    @SerializedName("id")
    @Expose
    private String id;

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}