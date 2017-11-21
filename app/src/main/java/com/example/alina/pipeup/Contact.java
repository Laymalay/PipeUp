package com.example.alina.pipeup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alina on 11/7/17.
 */

public class Contact {
    private String name;
    private String surname;
    private  String email;
    public List<Message> messages;
    private int ava;
    private String ava_link;

    public Contact(String name, String surname,String email, int ava, String link){

        this.name=name;
        this.surname=surname;
        this.email=email;
        this.ava=ava;
        this.messages= new ArrayList<>();
        this.ava_link=link;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAva() {
        return this.ava;
    }

    public void setAva(int ava) {
        this.ava = ava;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAva_link() {
        return ava_link;
    }

    public void setAva_link(String ava_link) {
        this.ava_link = ava_link;
    }
}
