package com.example.a1725121023_wengyuxian_lesson15;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class MusicService extends Service {
    MediaPlayer mediaPlayer = new MediaPlayer();
    LocalBinder binder = new LocalBinder();
    NotificationCompat.Builder builder;
    String CHANNEL_ID = "com.example.a1725121023_wengyuxian_lesson15.music_channel";

    public MusicService() {
    }

    public class LocalBinder extends Binder{
        public void startMediaPlayer(){
            mediaPlayer.start();
        }

        public void pauseMediaPlayer(){
            mediaPlayer.pause();
        }
        public void forwardMediaPlayer(){
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+1000);
        }

        public void stopMediaPlayer(){
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private void createNotificationChannel_normal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Music Channel";
            String description = "This is a music notification channel!";
            int important = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, important);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel_normal();
        Intent startMusic = new Intent();
        startMusic.setAction("com.example.a1725121023_wengyuxian_lesson15.control_music");
        startMusic.putExtra("control","start");
        PendingIntent startMusicPrndingIntent = PendingIntent.getBroadcast(MusicService.this,1,startMusic,0);

        Intent pauseMusic = new Intent();
        pauseMusic.setAction("com.example.a1725121023_wengyuxian_lesson15.control_music");
        pauseMusic.putExtra("control","pause");
        PendingIntent pauseMusicPrndingIntent = PendingIntent.getBroadcast(MusicService.this,2,pauseMusic,0);

        Intent forwardMusic = new Intent();
        forwardMusic.setAction("com.example.a1725121023_wengyuxian_lesson15.control_music");
        forwardMusic.putExtra("control","forward");
        PendingIntent forwardMusicPrndingIntent = PendingIntent.getBroadcast(MusicService.this,3,forwardMusic,0);

        Intent stopMusic = new Intent();
        stopMusic.setAction("com.example.a1725121023_wengyuxian_lesson15.control_music");
        stopMusic.putExtra("control","stop");
        PendingIntent stopMusicPrndingIntent = PendingIntent.getBroadcast(MusicService.this,4,stopMusic,0);

        Intent closeMusic = new Intent();
        closeMusic.setAction("com.example.a1725121023_wengyuxian_lesson15.control_music");
        closeMusic.putExtra("control","close");
        PendingIntent closeMusicPrndingIntent = PendingIntent.getBroadcast(MusicService.this,5,closeMusic,0);

        builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_note_black_24dp)
                .addAction(R.drawable.ic_play_arrow_black_24dp,"Play",startMusicPrndingIntent)
                .addAction(R.drawable.ic_pause_black_24dp,"Pause",pauseMusicPrndingIntent)
                .addAction(R.drawable.ic_forward_10_black_24dp,"Forward 10s",forwardMusicPrndingIntent)
                .addAction(R.drawable.ic_stop_black_24dp,"Stop",stopMusicPrndingIntent)
                .addAction(R.drawable.ic_close_black_24dp,"Close",closeMusicPrndingIntent)
                .setContentTitle("Counting star")
                .setContentText("OneRepublic")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.one_republic))
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        startForeground(1, builder.build());
        return super.onStartCommand(intent, flags, startId);
    }
}
