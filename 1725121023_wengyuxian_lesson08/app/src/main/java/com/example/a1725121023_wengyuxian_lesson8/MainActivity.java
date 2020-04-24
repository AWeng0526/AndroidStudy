package com.example.a1725121023_wengyuxian_lesson8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;
    FloatingActionButton floatingActionButton;
    TextView textView1, textView2;
    Boolean observeAirPlaneMode, observeWifi;
    Switch aSwitch;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        observeWifi = true;
        observeAirPlaneMode = true;
        aSwitch = findViewById(R.id.switch1);
        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView3);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        broadcastReceiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    registerReceiver(broadcastReceiver, intentFilter);
                } else {
                    unregisterReceiver(broadcastReceiver);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            floatingActionButton.setTooltipText("Share device statue");
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "AIRPLANE_MODE:" + textView1.getText() + "\n" + "WIFI:" + textView2.getText();
                Uri sms_to = Uri.parse("smsto:1725121023");
                Intent intent = new Intent(Intent.ACTION_SENDTO, sms_to);
                intent.putExtra("sms_body", message);
                startActivity(intent);
            }
        });

    }

    public void onObserveStateChange(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkBox:
                if (checked) {
                    Log.d("Yuxian Weng", "Observe AIRPLANE_MODE");
                    observeAirPlaneMode = true;
                } else {
                    Log.d("Yuxian Weng", "Don't Observe AIRPLANE_MODE");
                    observeAirPlaneMode = false;
                    textView1.setText("unknown");
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    Log.d("Yuxian Weng", "Observe WIFI");
                    observeWifi = true;
                } else {
                    Log.d("Yuxian Weng", "Don't WIFI AIRPLANE_MODE");
                    observeWifi = false;
                    textView2.setText("unknown");
                }
                break;
            default:
                break;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                    if (observeAirPlaneMode) {
                        String state;
                        if (intent.getBooleanExtra("state", false)) {
                            state = "enable";
                        } else {
                            state = "disable";
                        }
                        textView1.setText(state);
                        Log.d("Yuxian Weng", "AIRPLANE_MODE:" + state);
                    }
                    break;
                case WifiManager.WIFI_STATE_CHANGED_ACTION:
                    if (observeWifi) {
                        String state;
                        int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                        if (wifiState == WifiManager.WIFI_STATE_DISABLED) {
                            state = "disable";
                        } else if (wifiState == WifiManager.WIFI_STATE_DISABLING) {
                            state = "disabling";
                        } else if (wifiState == WifiManager.WIFI_STATE_ENABLED) {
                            state = "enable";
                        } else {
                            state = "other state";
                        }
                        textView2.setText(state);
                        Log.d("Yuxian Weng", "WIFI:" + state);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
