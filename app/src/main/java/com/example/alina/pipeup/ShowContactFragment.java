package com.example.alina.pipeup;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by alina on 11/12/17.
 */

public class ShowContactFragment extends Fragment implements View.OnClickListener {

    Contact contact;
    List<Contact> contacts;
    int pos;
    View view;

    public ShowContactFragment(Contact contact,List<Contact> contacts) {
        this.contact = contact;
        this.pos = contacts.indexOf(contact);
        this.contacts=contacts;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.show_contact_fragment,
                container, false);

        Button nextButton = (Button) view.findViewById(R.id.button_next);
        nextButton.setOnClickListener(this);
        Button prevButton = (Button) view.findViewById(R.id.button_prev);
        prevButton.setOnClickListener(this);
        Button backButton = (Button) view.findViewById(R.id.button_back);
        backButton.setOnClickListener(this);


        TextView nameView = (TextView) view.findViewById(R.id.name);

        TextView surnameView = (TextView) view.findViewById(R.id.surname);

        TextView emailView = (TextView) view.findViewById(R.id.email);

        //ImageView avaView = (ImageView) view.findViewById(R.id.ava);

        MultiPointTouchListener img = (MultiPointTouchListener) view.findViewById(R.id.ava);

        nameView.setText(contact.getName());
        surnameView.setText(contact.getSurname());
        emailView.setText(contact.getEmail());
        //avaView.setImageResource(contact.getAva());
        img.setImageResource(contact.getAva());
        return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                ((ContactList)getActivity()).UpdateList(this.contacts.get(this.pos));
                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.button_next:
                if(this.pos<contacts.size()-1) {
                    this.pos++;
                }
                else{
                    this.pos=0;
                }
                this.contact = this.contacts.get(this.pos);
                ChangeFragment(contact);
                break;
            case R.id.button_prev:
                if(this.pos>0) {
                    this.pos--;
                }
                else{
                        this.pos=contacts.size()-1;
                }
                this.contact = this.contacts.get(this.pos);
                ChangeFragment(contact);
                break;
        }
    }

    private void ChangeFragment(Contact contact){
        ((TextView)(this.view.findViewById(R.id.name))).setText(contact.getName());
        ((TextView)(this.view.findViewById(R.id.surname))).setText(contact.getSurname());
        ((TextView)(this.view.findViewById(R.id.email))).setText(contact.getEmail());
        ((ImageView)(this.view.findViewById(R.id.ava))).setImageResource(contact.getAva());
    }
}


