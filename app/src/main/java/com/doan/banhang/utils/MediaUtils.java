package com.doan.banhang.utils;

import android.media.MediaPlayer;

import com.doan.banhang.base.BaseApplication;

public class MediaUtils {
    public static MediaUtils mediaUtils;
    public static MediaPlayer mediaPlayer;

    public static MediaUtils getInstance() {
        if (mediaUtils == null) {
            mediaUtils = new MediaUtils();

        }


        return mediaUtils;
    }

    private void stop() {
        if (mediaPlayer.isPlaying() == true) {
            mediaPlayer.stop();
        }
    }


    public void play(int source) {

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(BaseApplication.getContext(), source);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            try {
                stop();
                mediaPlayer.prepareAsync();
           //     mediaPlayer.seekTo(200);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.start();
                        mp.seekTo(200);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        else {
            stop();
         //   mediaPlayer.release();
            mediaPlayer=null;
            //play(source);

        }

    }

}
