package com.example.alina.pipeup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alina on 11/9/17.
 */

public class ChatAdapter extends ArrayAdapter<Message> {
    private LayoutInflater inflater;
    private int layout;
    private List<Message> messages;

    public ChatAdapter(Context context, int resource, List<Message> messages) {
        super(context, resource, messages);
        this.messages = messages;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = (TextView) view.findViewById(R.id.name);

        TextView textView = (TextView) view.findViewById(R.id.text);

        TextView dateView = (TextView) view.findViewById(R.id.date);

        ImageView avaView = (ImageView) view.findViewById(R.id.ava);

        Message message = messages.get(position);


        nameView.setText(message.getSender().getName());
        dateView.setText(message.getDate());
        textView.setText(message.getText());
        avaView.setImageResource(message.getSender().getAva());
        return view;
    }
}
