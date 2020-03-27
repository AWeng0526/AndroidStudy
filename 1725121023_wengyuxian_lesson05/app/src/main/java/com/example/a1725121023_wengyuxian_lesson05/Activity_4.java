package com.example.a1725121023_wengyuxian_lesson05;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_4 extends AppCompatActivity {
    Button button9, button10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        Log.d("翁彧贤", toString());
        Log.d("翁彧贤", "It is the task" + getTaskId());

        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);

        button9.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Activity_4.this, Activity_4.class);
                        startActivity(intent);
                    }
                });

        button10.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Activity_4.this, Activity_1.class);
                        startActivity(intent);
                    }
                });
    }
}
