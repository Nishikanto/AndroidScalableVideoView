package com.shadow.androidvideoview;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer audioPlayer;
    private ScalableVideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();

        mVideoView = findViewById(R.id.video_view);
        try {
            mVideoView.setRawData(R.raw.videoplayback);
            mVideoView.setVolume(0, 0);
            mVideoView.setLooping(true);
            mVideoView.prepare(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mVideoView.start();
                }
            });
        } catch (IOException ioe) {
            //ignore
        }

        audioPlayer = MediaPlayer.create(MainActivity.this, R.raw.audio);
        audioPlayer.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioPlayer.start();
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioPlayer.pause();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(null != audioPlayer)
            audioPlayer.release();
        audioPlayer = null;

        if(null != mVideoView)
            mVideoView.release();
        mVideoView = null;
    }

    private void hideSystemUI() {
        View mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }


    private void showSystemUI() {
        View mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}