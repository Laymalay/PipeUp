package com.example.alina.pipeup;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {

    private List<Contact> contacts = new ArrayList();
    SharedPreferences mSettings;
    ContactListAdapter contactListAdapter;

    ListView contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_list);

        mSettings = getSharedPreferences(Starter.APP_PREFERENCES, Context.MODE_PRIVATE);
        setInitialData();
        contactList = (ListView) findViewById(R.id.contactList);
        contactListAdapter = new ContactListAdapter(this, R.layout.contact_item, contacts);
        contactList.setAdapter(contactListAdapter);
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = (Contact) parent.getItemAtPosition(position);
                Intent intent = new Intent(ContactList.this, Chat.class);
                intent.putExtra("interlocutor_email",selectedContact.getEmail());
                startActivity(intent);
            }
        });
        registerForContextMenu(contactList);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.show:
                showContact(info.id);
                return true;
            case R.id.edit:
                editContact(info.id);
                return true;
            case R.id.delete:
                deleteContact(info.id);
                return true;
            case R.id.send_message:
                sendMessage(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void UpdateList(Contact recent_contact){
        for(int i=this.contacts.indexOf(recent_contact);i>0;){
            contacts.set(i,contacts.get(--i));
            Log.d("--------------------","==============");
            for (int j=0;j<contacts.size();j++){
                Log.d("dsf",contacts.get(j).getName());
            }
        }
        this.contacts.set(0,recent_contact);
        Log.d("--------------------","======================");
        for (int j=0;j<contacts.size();j++){
            Log.d("dsf",contacts.get(j).getName());
        }
        contactListAdapter = new ContactListAdapter(this, R.layout.contact_item, contacts);
        contactList.setAdapter(contactListAdapter);
    }

    private void showContact(long id) {
        Contact selectedContact = (Contact) contacts.get(((int) id));
        ShowContactFragment fragment = new ShowContactFragment(selectedContact, contacts);
        loadFragment(fragment);

    }

    private void loadFragment(ShowContactFragment showContactFragment) {
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, showContactFragment);
        fragmentTransaction.commit(); // save the changes
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    public void setInitialData(){
        contacts.addAll(Starter.users);
    }
    private void editContact(long id){

    }
    private void deleteContact(long id) {
        Contact selectedContact = (Contact) contacts.get(((int) id));
        Starter.users.remove(selectedContact);
        contacts.remove(selectedContact);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    private void sendMessage(long id) {
        Contact selectedContact = (Contact) contacts.get(((int) id));
        Intent intent = new Intent(ContactList.this, Chat.class);
        intent.putExtra("interlocutor_email",selectedContact.getEmail());
        startActivity(intent);
    }

}
