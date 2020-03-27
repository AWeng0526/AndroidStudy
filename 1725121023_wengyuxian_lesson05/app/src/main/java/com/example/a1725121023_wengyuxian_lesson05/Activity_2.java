package com.example.a1725121023_wengyuxian_lesson05;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_2 extends AppCompatActivity {
    Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Log.d("翁彧贤", toString());
        Log.d("翁彧贤", "It is the task" + getTaskId());

        button6 = findViewById(R.id.button6);

        button6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Activity_2.this, Activity_2.class);
                        startActivity(intent);
                    }
                });
    }
}

