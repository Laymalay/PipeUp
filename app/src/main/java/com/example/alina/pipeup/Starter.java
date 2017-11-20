package com.example.alina.pipeup;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alina on 11/8/17.
 */

public class Starter extends AppCompatActivity implements View.OnClickListener {
    public static final String APP_PREFERENCES = "PipeUpSettings";
    public static final String APP_PREFERENCES_USER = "userEmail";
    SharedPreferences mSettings;
    Button btnStart, btnStartService;
    public static boolean isService = false;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    public static List<Contact> users = new ArrayList();
    Contact alina = new Contact("Alina", "Zhukouskaya", "alina@gmail.com", ((R.drawable.alina)));
    Contact gogi = new Contact("Gogi", "Shap", "gogi@gmail.com", (R.drawable.gogi));
    Contact nika = new Contact("Nika", "Zhukouskaya", "nika@gmail.com", (R.drawable.nika));
    NotificationManager mNotificationManager;
    MediaPlayer mp;
    NotificationCompat.Builder mBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starter);
        setInitialData();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        mp = MediaPlayer.create(this, R.raw.btn);

        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(this);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
            .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Content Title")
            .setContentText("Content Text");

    }

    @Override
    public void onClick(View v) {
        Log.d("not","notify");
        int mNotificationId = 1;

        Log.d("asa",  v.getId()+"");
        mNotificationManager.notify(mNotificationId, mBuilder.build());
        switch (v.getId()) {
            case R.id.btnStartService:
                mp.start();
                Log.d("asa","Serviceee");
                startService(new Intent(Starter.this,BackgroundService.class));
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                isService = true;
                TextView tv = (TextView) findViewById(R.id.TextViewService);
                tv.setText("Service Start");
                break;
            case R.id.btnStart:
                mp.start();
                EditText enterEmail = (EditText) findViewById(R.id.enterEmail);
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

    public void setInitialData() {

        Message msg1 = new Message(alina, gogi, new Date(), "hi");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message msg2 = new Message(gogi, alina, new Date(), "Hi,how are you?");
        alina.messages.add(msg1);
        gogi.messages.add(msg2);
        users.add(alina);
        users.add(gogi);
        users.add(nika);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();

                return true;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    Log.d("xvd", "Action was SWIPE");
                    EditText enterEmail = (EditText) findViewById(R.id.enterEmail);
                    String userEmail = enterEmail.getText().toString();
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_USER, userEmail);
                    editor.apply();
                    Intent intnt = new Intent(this, ContactList.class);
                    startActivity(intnt);

                    return true;
                }
                else
                {
                    return false;
                }

        }

            return super.onTouchEvent(event);


        }


    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(Starter.this,
                BackgroundService.class));
        if(isService)
        {
            TextView tv = (TextView) findViewById(R.id.TextViewService);
            tv.setText("Service Resumed");
            isService = false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        return true;
    }
}