package com.example.a1725121023_wengyuxian_lesson04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        String email = intent.getStringExtra("key_email");

        Bundle bundle = intent.getExtras();
        String name = bundle.getString("key_name");
        int age = bundle.getInt("key_age");
        Boolean is_married = bundle.getBoolean("key_is_married");

        if (email != null) {
            textView.setText("Email:" + email);
        } else {
            textView.setText("Name:" + name + "\n" + "Age:" + age + "\n" + "is married" + is_married);
        }

    }
}
