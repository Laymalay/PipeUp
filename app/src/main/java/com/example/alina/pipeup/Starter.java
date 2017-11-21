package com.example.alina.pipeup;


import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    Button btnStart, btnStartService, btnCleanDB;
    public static boolean isService = false;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    public static List<Contact> users = new ArrayList();
    Contact alina = new Contact("Alina",
                                "Zhukouskaya",
                                "alina@gmail.com", ((R.drawable.alina)),
                                "https://dl.dropboxusercontent.com/s/llcc1a3l8yzzpzi/alina%40gmail.com.jpg");

    Contact gogi = new Contact("Gogi",
                               "Shap",
                               "gogi@gmail.com", (R.drawable.gogi),
                               "https://dl.dropboxusercontent.com/s/e9fcs8h652fatpx/gogi%40gmail.com.jpg");

    Contact nika = new Contact("Nika",
                               "Zhukouskaya",
                               "nika@gmail.com", (R.drawable.nika),
                               "https://dl.dropboxusercontent.com/s/2ptved6rzfk0xxu/nika%40gmail.com.jpg");
    NotificationManager mNotificationManager;
    MediaPlayer mp;
    NotificationCompat.Builder mBuilder;
    public static DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.starter);
        setInitialData();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        mp = MediaPlayer.create(this, R.raw.btn);

        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(this);

        btnCleanDB = (Button) findViewById(R.id.btnCleanDB);
        btnCleanDB.setOnClickListener(this);

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
            case R.id.btnCleanDB:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Log.d("DATABASE", "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount1 = db.delete("users", null, null);
                Log.d("DATABASE", "deleted rows count = " + clearCount1);
                int clearCount2 = db.delete("messages", null, null);
                Log.d("DATABASE", "deleted rows count = " + clearCount2);
                dbHelper.close();
                break;
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
        Log.d("DADADA", dbHelper.toString());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues user1 = new ContentValues();
        user1.put("name", alina.getName());
        user1.put("email", alina.getEmail());
        user1.put("surname", alina.getSurname());
        user1.put("imagelink",alina.getAva_link());
        long rowID1 = db.insert("users", null, user1);
        Log.d("DATABASE", "row inserted, ID = " + rowID1);

        ContentValues user2 = new ContentValues();
        user2.put("name", gogi.getName());
        user2.put("email", gogi.getEmail());
        user2.put("surname", gogi.getSurname());
        user2.put("imagelink",gogi.getAva_link());
        long rowID2 = db.insert("users", null, user2);
        Log.d("DATABASE", "row inserted, ID = " + rowID2);

        ContentValues user3 = new ContentValues();
        user3.put("name", nika.getName());
        user3.put("email", nika.getEmail());
        user3.put("surname", nika.getSurname());
        user3.put("imagelink",nika.getAva_link());
        long rowID3 = db.insert("users", null, user3);
        Log.d("DATABASE", "row inserted, ID = " + rowID3);

        Message msg1 = new Message(alina, gogi, new Date(), "hi");
        Message msg2 = new Message(gogi, alina, new Date(), "Hi,how are you?");


        alina.messages.add(msg1);
        gogi.messages.add(msg2);
        users.add(alina);
        users.add(gogi);
        users.add(nika);

        ContentValues message1 = new ContentValues();
        message1.put("sender", msg1.getSender().getEmail());
        message1.put("receiver", msg1.getReceiver().getEmail());
        message1.put("txt", msg1.getText());
        message1.put("date", msg1.getDate());
        long messages_rowID1 = db.insert("messages", null, message1);
        Log.d("DATABASE", "row inserted, ID = " + messages_rowID1);

        ContentValues message2 = new ContentValues();
        message2.put("sender", msg2.getSender().getEmail());
        message2.put("receiver", msg2.getReceiver().getEmail());
        message2.put("txt", msg2.getText());
        message2.put("date", msg2.getDate());
        long messages_rowID2 = db.insert("messages", null, message2);
        Log.d("DATABASE", "row inserted, ID = " + messages_rowID2);





        Log.d("DATABASE", "--- Rows in users: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query("users", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int emailColIndex = c.getColumnIndex("email");
            int surnameColIndex = c.getColumnIndex("surname");
            int avaColIndex = c.getColumnIndex("imagelink");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("DATABASE",
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", surname = " + c.getString(surnameColIndex) +
                                ", imageLink = " + c.getString(avaColIndex) +
                                ", email = " + c.getString(emailColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("DATABASE", "0 rows");
        c.close();


        Log.d("DATABASE", "--- Rows in messages: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor m = db.query("messages", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (m.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = m.getColumnIndex("id");
            int textColIndex = m.getColumnIndex("txt");
            int dateColIndex = m.getColumnIndex("date");
            int senderColIndex = m.getColumnIndex("sender");
            int receiverColIndex = m.getColumnIndex("receiver");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("DATABASE",
                        "ID = " + m.getInt(idColIndex) +
                                ", txt = " + m.getString(textColIndex) +
                                ", sender = " + m.getString(senderColIndex) +
                                ", date = " + m.getString(dateColIndex) +
                                ", receiver = " + m.getString(receiverColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (m.moveToNext());
        } else
            Log.d("DATABASE", "0 rows");
        m.close();

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