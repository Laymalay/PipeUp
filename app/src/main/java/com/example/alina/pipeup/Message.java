package com.example.alina.pipeup;

import android.provider.ContactsContract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alina on 11/8/17.
 */

public class Message {
    private Contact sender;
    private Contact receiver;
    private Date date;
    private String text;
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa");


    public Message(Contact sender,Contact receiver, java.util.Date date, String text){
        this.sender=sender;
        this.receiver=receiver;
        this.date=date;
        this.text=text;
    }


    public String getDate() {
        return sdf.format(date);
    }

    public Date getFormatDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Contact getSender() {
        return sender;
    }

    public void setSender(Contact sender) {
        this.sender = sender;
    }

    public Contact getReceiver() {
        return receiver;
    }

    public void setReceiver(Contact receiver) {
        this.receiver = receiver;
    }
}
