package com.example.guillermorosales.videochallenge;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnPreparedListener, VideoControllerView.OnPlayPauseListener, VideoControllerView.OnSeekBarChangeListener {

    private TextureView texture;
    private VideoControllerView controller;
    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    private Handler handler = new Handler();
    private static final String STREAM_URL = "http://distribution.bbb3d.renderfarming.net/video/mp4/bbb_sunflower_1080p_30fps_normal.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texture = (TextureView) findViewById(R.id.textureView);
        progressBar = (ProgressBar) findViewById(R.id.loading);
        texture.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

        Surface s = new Surface(surfaceTexture);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(STREAM_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setSurface(s);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(MediaPlayer mediaPlayer, TimedText timedText) {
                Log.d("hola", "onTimedText: ");

            }
        });
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnVideoSizeChangedListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
            // TODO implement this
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        // TODO implement this
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        // TODO implement this
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        // TODO implement this
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        // TODO implement this
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
        // TODO implement this
    }

    @Override
    public void onPrepared(final MediaPlayer mediaPlayer) {
        controller = new VideoControllerView(this, this, this);
        controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
        this.mediaPlayer.start();
        controller.show();
        controller.setLength(mediaPlayer.getDuration());
        progressBar.setVisibility(View.GONE);

        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer != null){
                    controller.OnTick(mediaPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onPlayPauseTouched(boolean isPlaying) {
        if(isPlaying) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    @Override
    public void onSeekBarChange(int newInt) {
        mediaPlayer.seekTo(newInt);
    }


}
