package com.example.alina.pipeup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ContactList extends AppCompatActivity {

    public static List<Contact> users = new ArrayList();
    Contact alina = new Contact ("Alina", "Zhukouskaya","alina@gmail.com", ((R.drawable.alina)));
    Contact gogi = new Contact ("Gogi", "Shap","gogi@gmail.com", (R.drawable.gogi));
    Contact nika = new Contact("Nika", "Zhukouskaya","nika@gmail.com", (R.drawable.nika));


    private List<Contact> contacts = new ArrayList();
    SharedPreferences mSettings;

    ListView contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_list);

        mSettings = getSharedPreferences(Starter.APP_PREFERENCES, Context.MODE_PRIVATE);
        setInitialData();
        // получаем элемент ListView
        contactList = (ListView) findViewById(R.id.contactList);
        // создаем адаптер
        ContactListAdapter contactListAdapter = new ContactListAdapter(this, R.layout.contact_item, contacts);
        // устанавливаем адаптер
        contactList.setAdapter(contactListAdapter);
        // слушатель выбора в списке
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = (Contact) parent.getItemAtPosition(position);
                Intent intent = new Intent(ContactList.this, Chat.class);
                intent.putExtra("interlocutor_email",selectedContact.getEmail());
                startActivity(intent);
            }
        });
    }
    public void setInitialData(){

        Message msg1 = new Message(alina,gogi,new Date(),"hi");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message msg2 = new Message(gogi,alina,new Date(),"Hi,how are you?");
        contacts.add(alina);
        contacts.add(gogi);
        contacts.add(nika);
        alina.messages.add(msg1);
        gogi.messages.add(msg2);
        users.add(alina);
        users.add(gogi);
        users.add(nika);

    }
}
