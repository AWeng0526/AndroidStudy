package com.example.a1725121023_wengyuxian_lesson05;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_1 extends AppCompatActivity {
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        Log.d("翁彧贤", toString());
        Log.d("翁彧贤", "It is the task" + getTaskId());

        button5 = findViewById(R.id.button5);

        button5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Activity_1.this, Activity_1.class);
                        startActivity(intent);
                    }
                });
    }
}

