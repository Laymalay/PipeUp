package com.example.alina.pipeup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by alina on 11/9/17.
 */

public class Chat extends AppCompatActivity {

    private Contact sender;
    private Contact receiver;
    private List<Contact> users;
    private List<Message> messages;
    SharedPreferences mSettings;


    ListView chatView;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getSharedPreferences(Starter.APP_PREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.chat);
        users = ContactList.users;
        messages = new ArrayList();
        mSettings = getSharedPreferences(Starter.APP_PREFERENCES, Context.MODE_PRIVATE);
        String email = getIntent().getStringExtra("interlocutor_email");

        for (int i=0; i<users.size();i++) {

            if (Objects.equals(users.get(i).getEmail(), mSettings.getString(Starter.APP_PREFERENCES_USER, ""))){
                sender = users.get(i);
            }
            if (Objects.equals(users.get(i).getEmail(), email)){
                receiver = users.get(i);
            }
        }

        for(int j=0;j<sender.messages.size();j++)
        {
            if(Objects.equals(sender.messages.get(j).getReceiver(),receiver)){
                messages.add(sender.messages.get(j));
            }
        }
        for(int k=0;k<receiver.messages.size();k++)
        {
            if(Objects.equals(receiver.messages.get(k).getReceiver(),sender)){
                messages.add(receiver.messages.get(k));
            }
        }
        
        chatView = (ListView) findViewById(R.id.messages);
        ChatAdapter chatAdapter = new ChatAdapter(this, R.layout.message_item, messages);
        chatView.setAdapter(chatAdapter);
    }
}
