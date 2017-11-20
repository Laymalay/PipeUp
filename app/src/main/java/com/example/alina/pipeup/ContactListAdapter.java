package com.example.alina.pipeup;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alina on 11/7/17.
 */

public class ContactListAdapter extends ArrayAdapter<Contact> {
    private LayoutInflater inflater;
    private int layout;
    private List<Contact> contacts;

    public ContactListAdapter(Context context, int resource, List<Contact> contacts) {
        super(context, resource, contacts);
        this.contacts = contacts;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView nameView = (TextView) view.findViewById(R.id.name);

        TextView surnameView = (TextView) view.findViewById(R.id.surname);

        TextView emailView = (TextView) view.findViewById(R.id.email);

        ImageView avaView = (ImageView) view.findViewById(R.id.ava);

        Contact contact = contacts.get(position);


        nameView.setText(contact.getName());
        surnameView.setText(contact.getSurname());
        emailView.setText(contact.getEmail());
        //avaView.setImageResource(contact.getAva());
        Picasso.with(this.getContext()).load("https://i.imgur.com/tGbaZCY.jpg").fit().into(avaView);


        return view;
    }
}
