package com.example.a1725121023_wengyuxian_lesson14;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String CHANNEL_NORMAL = "com.example.a1725121023_wengyuxian_lesson14.channel_normal";
    String CHANNEL_HEADSUP = "com.example.a1725121023_wengyuxian_lesson14.channel_headsup";


    private void createNotificationChannel_headsup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Heads-Up Channel";
            String description = "This is a heads-up channel";
            int important = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_HEADSUP, name, important);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannel_normal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Normal Channel";
            String description = "This is a normal channel";
            int important = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_NORMAL, name, important);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        createNotificationChannel_normal();
        createNotificationChannel_headsup();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        final NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(MainActivity.this);
        switch (v.getId()) {
            case R.id.button:
                final NotificationCompat.Builder builder1 =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_NORMAL)
                                .setSmallIcon(R.drawable.ic_android_black_24dp)
                                .setContentTitle("Basic Notification")
                                .setContentText("This is a basic notification!")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                notificationManagerCompat.notify(1, builder1.build());
                break;

            case R.id.button2:
                NotificationCompat.Builder builder2 =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_NORMAL)
                                .setSmallIcon(R.drawable.ic_android_black_24dp)
                                .setContentTitle("Large Icon and Color")
                                .setContentText(
                                        "This is a notification with setLargeIcon() and setColor()!")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .setLargeIcon(
                                        BitmapFactory.decodeResource(
                                                getResources(), R.drawable.img))
                                .setColor(Color.parseColor("#A52A2A"));
                notificationManagerCompat.notify(2, builder2.build());
                break;

            case R.id.button3:
                NotificationCompat.Builder builder3 =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_NORMAL)
                                .setSmallIcon(R.drawable.ic_star_border_black_24dp)
                                .setContentTitle("My Notification")
                                .setContentText("I'm wengyuxian")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .setLargeIcon(
                                        BitmapFactory.decodeResource(
                                                getResources(), R.drawable.img))
                                .setColor(Color.parseColor("#1E90FF"));
                notificationManagerCompat.notify(3, builder3.build());
                break;
            case R.id.button4:
                NotificationCompat.Builder builder4 =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_HEADSUP)
                                .setSmallIcon(R.drawable.ic_android_black_24dp)
                                .setContentTitle("Heads-up Notification")
                                .setContentText("This is a heads-up notification!")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setDefaults(Notification.DEFAULT_VIBRATE);
                notificationManagerCompat.notify(4, builder4.build());
                break;
            case R.id.button5:
                String GROUP_KEY = "com.example.a1725121023_wengyuxian_lesson14.group_key";
                Notification notificationSummary =
                        new NotificationCompat.Builder(this, CHANNEL_NORMAL)
                                .setSmallIcon(R.drawable.ic_android_black_24dp)
                                .setGroup(GROUP_KEY)
                                .setGroupSummary(true)
                                .build();
                notificationManagerCompat.notify(5, notificationSummary);
                NotificationCompat.Builder builderChild =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_NORMAL)
                                .setSmallIcon(R.drawable.ic_android_black_24dp)
                                .setContentTitle("Child Notification")
                                .setContentText("This is the child notification1!")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .setGroup(GROUP_KEY);
                notificationManagerCompat.notify(6, builderChild.build());
                builderChild.setContentText("This is the child notification2!");
                notificationManagerCompat.notify(7, builderChild.build());
                builderChild.setContentText("This is the child notification3!");
                notificationManagerCompat.notify(8, builderChild.build());

                break;
            case R.id.button6:
                notificationManagerCompat.cancelAll();
                break;
            case R.id.button7:
                final NotificationCompat.Builder builder7 =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_NORMAL)
                                .setSmallIcon(R.drawable.ic_android_black_24dp)
                                .setContentText("An Operation")
                                .setOnlyAlertOnce(true)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .setProgress(100, 0, false);
                notificationManagerCompat.notify(9, builder7.build());
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                int PROGRESS_CURRENT = 0;
                                try {
                                    for (int i = 0; i < 10; i++) {
                                        PROGRESS_CURRENT += 10;
                                        builder7.setProgress(100, PROGRESS_CURRENT, false);
                                        notificationManagerCompat.notify(
                                                9, builder7.build());
                                        Thread.sleep(1000);
                                    }
                                    builder7.setProgress(0, 0, false)
                                            .setContentText("Operation Completed!");
                                    notificationManagerCompat.notify(9, builder7.build());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .start();
                break;
            default:
                break;
        }
    }
}
