package com.example.guillermorosales.videochallenge;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by guillermo.rosales on 8/13/16.
 */
public class VideoControllerView extends FrameLayout {

    private ViewGroup view;
    private Context context;
    private ImageButton playPause;
    private SeekBar seekBar;
    private TextView currentTime;
    private TextView lengthTime;
    private OnPlayPauseListener playListener;
    private OnSeekBarChangeListener seekListener;
    private boolean isPlaying = true;

    public VideoControllerView(Context context, OnPlayPauseListener playListener, OnSeekBarChangeListener seekListener) {
        super(context);
        this.context = context;
        this.playListener = playListener;
        this.seekListener = seekListener;
    }

    public void setAnchorView(ViewGroup viewGroup) {
        view = viewGroup;
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        removeAllViews();
        View v = getControllerView();
        addView(v, frameParams);

    }

    private View getControllerView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View controlView = inflater.inflate(R.layout.player_controls, null);
        initViews(controlView);
        return controlView;
    }

    public void initViews(View view) {
        seekBar = (SeekBar) view.findViewById(R.id.bottom_seekbar);
        playPause = (ImageButton) view.findViewById(R.id.bottom_pause);
        lengthTime = (TextView) view.findViewById(R.id.bottom_time);
        currentTime = (TextView) view.findViewById(R.id.bottom_time_current);
        playPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                playListener.onPlayPauseTouched(isPlaying);
                togglePlayPauseImage();
                isPlaying = !isPlaying;
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekListener.onSeekBarChange(i);
                currentTime.setText(TimeUtil.intRawToTimeString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void togglePlayPauseImage() {
        if (!isPlaying) {
            playPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_media_pause));
        } else {
            playPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_media_play));
        }
    }

    public void setLength(int length) {
        seekBar.setMax(length);
        lengthTime.setText(TimeUtil.intRawToTimeString(length));

    }

    public void show() {
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.addView(VideoControllerView.this, tlp);
    }

    public void OnTick(int time) {
        currentTime.setText(TimeUtil.intRawToTimeString(time));
        seekBar.setProgress(time);

    }

    public interface OnPlayPauseListener {
        void onPlayPauseTouched(boolean isPlaying);
    }

    public interface OnSeekBarChangeListener {
        void onSeekBarChange(int newInt);
    }
}
