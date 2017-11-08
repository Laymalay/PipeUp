package com.example.alina.pipeup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by alina on 11/8/17.
 */

public class Starter extends AppCompatActivity implements View.OnClickListener {
    public static final String APP_PREFERENCES = "PipeUpSettings";
    public static final String APP_PREFERENCES_USER = "userEmail";
    SharedPreferences mSettings;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                EditText enterEmail = (EditText)findViewById(R.id.enterEmail);
                String userEmail = enterEmail.getText().toString(); // здесь содержится текст, введенный в текстовом поле
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_USER, userEmail);
                editor.apply();
                Intent intent = new Intent(this, ContactList.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
