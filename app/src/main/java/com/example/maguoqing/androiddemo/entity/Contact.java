package com.example.maguoqing.androiddemo.entity;

import com.orm.SugarRecord;

/**
 * Created by magq on 16/3/3.
 */
public class Contact extends SugarRecord {
    private String name;
    private int age;
    private String phone_number;

    public Contact() {

    }

    public Contact(String name, int age, String phone_number) {
        this.name = name;
        this.age = age;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
