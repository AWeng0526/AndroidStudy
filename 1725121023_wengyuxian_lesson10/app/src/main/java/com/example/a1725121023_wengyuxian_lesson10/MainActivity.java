package com.example.a1725121023_wengyuxian_lesson10;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    String filename, image;
    File file;
    RadioButton radioButton3, radioButton4;
    Button button1, button2;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);

        if (!isExternalStroageWritable()) {
            radioButton3.setEnabled(false);
            radioButton4.setEnabled(false);
            Toast.makeText(MainActivity.this, "External Storage is unavailablel", Toast.LENGTH_SHORT).show();
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filename != null) {
                    downloadImage();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filename != null) {
                    readImageFIle();
                }
            }
        });
    }

    private void downloadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File write_file = new File(file, filename);
                    if (!write_file.exists()) {
                        URL url = new URL(image);
                        URLConnection connection = (URLConnection) url.openConnection();
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        FileOutputStream outputStream = new FileOutputStream(write_file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        inputStream.close();
                        outputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void readImageFIle() {
        try {
            File read_file = new File(file, filename);
            if (read_file.exists()) {
                FileInputStream inputStream = new FileInputStream(read_file);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(MainActivity.this, "This mage doesn't exits", Toast.LENGTH_SHORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isExternalStroageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked) {
                    image = "https://s1.ax1x.com/2020/04/24/JDMJfI.jpg";
                    filename = "persistent_file_internal";
                    file = getFilesDir();
                }
                break;
            case R.id.radioButton2:
                if (checked) {
                    image = "https://s1.ax1x.com/2020/04/24/JDMGtA.jpg";
                    filename = "cache_file_internal";
                    file = getCacheDir();
                }
                break;
            case R.id.radioButton3:
                if (checked) {
                    image = "https://s1.ax1x.com/2020/04/24/JDM1TH.jpg";
                    filename = "persistent_file_external";
                    file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                }
                break;
            case R.id.radioButton4:
                if (checked) {
                    image = "https://s1.ax1x.com/2020/04/24/JDM8kd.jpg";
                    filename = "cache_file_external";
                    file = getExternalCacheDir();
                }
                break;
            default:
                break;
        }
    }
}
