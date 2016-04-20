package com.example.maguoqing.androiddemo.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by magq on 16/4/19.
 */
public class ClipItem {

    private Date date;
    private List<String> clips;

    public ClipItem (Date date) {
        this.date = date;
        clips = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getClips() {
        return clips;
    }

    public void setClips(List<String> clips) {
        this.clips = clips;
    }
}
