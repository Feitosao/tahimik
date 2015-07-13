package com.example.lucasfeitosa.loadimagetest;

/**
 * Created by Lucas Feitosa on 12/07/2015.
 */
public class User
{
    private String uid;
    private String name;
    private String email;
    public User(String uid, String name, String email)
    {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
