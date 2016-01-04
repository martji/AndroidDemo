package com.example.maguoqing.androiddemo.model;

import java.util.ArrayList;

/**
 * Created by maguoqing on 2016/1/4.
 */
public class CourseItem {

    private String courseName;
    private ArrayList<Lesson> lessons;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }
}
