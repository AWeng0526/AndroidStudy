package com.example.a1725121023_wengyuxian_lesson03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
    Integer i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("activity_lifecycle","MainActivity onCreate()");
        textView = findViewById(R.id.textview_1);
        button = findViewById(R.id.button_1);

        if (savedInstanceState != null) {
            i = savedInstanceState.getInt("i");
            textView.setText("翁彧贤 click" + i + "times!");
        } else {
            textView.setText("Ready?Click!");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                textView.setText("翁彧贤 click" + i + "times!");
                if (i == 5) {
                    Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                    startActivity(intent);
                } else if (i >= 10) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("i", i);
        Log.d("activity_lifecycle", "MainActivity onSaveInstanceState()" + "i = " + i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activity_lifecycle", "MainActivity onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activity_lifecycle", "MainActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activity_lifecycle", "MainActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activity_lifecycle", "MainActivity onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("activity_lifecycle", "MainActivity onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity_lifecycle", "MainActivity onDestroy()");
    }
}
