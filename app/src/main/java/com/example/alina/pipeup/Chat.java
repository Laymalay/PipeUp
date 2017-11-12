package com.example.alina.pipeup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
        users = Starter.users;
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
        Collections.sort(messages, new MessageComparator());
        chatView = (ListView) findViewById(R.id.messages);
        ChatAdapter chatAdapter = new ChatAdapter(this, R.layout.message_item, messages);
        chatView.setAdapter(chatAdapter);

        Button sendButton = (Button) findViewById(R.id.sendBth);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                Message newMessage = new Message(sender,receiver,new Date(),input.getText().toString());
                messages.add(newMessage);
                sender.messages.add(newMessage);
                input.setText("");
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
}

class MessageComparator implements Comparator<Message> {
    public int compare(Message msg1, Message msg2) {
        if (msg1.getFormatDate().before(msg2.getFormatDate())) {
            return -1;
        } else if (msg1.getFormatDate().after(msg2.getFormatDate())){
            return 1;
        } else {
            return 0;
        }
    }
}
