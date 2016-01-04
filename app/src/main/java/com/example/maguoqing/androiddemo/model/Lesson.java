package com.example.maguoqing.androiddemo.model;

/**
 * Created by maguoqing on 2016/1/4.
 */
public class Lesson {
    private String lessonTitle;
    private String teacherName;

    public Lesson () {}

    public Lesson(String lessonTitle, String teacherName) {
        this.lessonTitle = lessonTitle;
        this.teacherName = teacherName;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
