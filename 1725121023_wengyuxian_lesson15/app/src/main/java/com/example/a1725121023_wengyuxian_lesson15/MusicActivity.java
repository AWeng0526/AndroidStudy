package com.example.a1725121023_wengyuxian_lesson15;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener{
    BroadcastReceiver broadcastReceiver;
    MusicService.LocalBinder binder;
    Button button1, button2, button3, button4, button5, button6;
    boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);

        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.a1725121023_wengyuxian_lesson15.control_music");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicService.LocalBinder) service;
            isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAndUnbindService();
        unregisterReceiver(broadcastReceiver);
    }

    private void stopAndUnbindService() {
        if (isConnected){
            unbindService(connection);
            Intent intent = new Intent(MusicActivity.this, MusicService.class);
            stopService(intent);
            isConnected = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                if (!isConnected){
                    Intent startServiceIntent = new Intent(MusicActivity.this, MusicService.class);
                    startService(startServiceIntent);
                    bindService(startServiceIntent, connection, Context.BIND_AUTO_CREATE);
                }
                break;
            case R.id.button2:
                if (isConnected){
                    binder.startMediaPlayer();
                }
                break;
            case R.id.button3:
                if (isConnected){
                    binder.pauseMediaPlayer();
                }
                break;
            case R.id.button4:
                if (isConnected){
                    binder.forwardMediaPlayer();
                }
                break;
            case R.id.button5:
                if (isConnected){
                    binder.stopMediaPlayer();
                }
                break;
            case R.id.button6:
                stopAndUnbindService();
                break;
            default:
                break;
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String musicControl = intent.getStringExtra("control");
            switch (musicControl){
                case "start":
                    binder.startMediaPlayer();
                    break;
                case "pause":
                    binder.pauseMediaPlayer();
                    break;
                case "forward":
                    binder.forwardMediaPlayer();
                    break;
                case "stop":
                    binder.stopMediaPlayer();
                    break;
                case "close":
                    stopAndUnbindService();
                    break;
                default:
                    break;
            }
        }
    }
}
