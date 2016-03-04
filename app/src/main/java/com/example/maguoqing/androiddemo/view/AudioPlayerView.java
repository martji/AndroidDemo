package com.example.maguoqing.androiddemo.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.maguoqing.androiddemo.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by magq on 16/2/26.
 */
public class AudioPlayerView extends FrameLayout {

    private RelativeLayout playerBg;
    private ImageView playerBtn;
    private SeekBar playerSeek;

    private String audioPath;
    private MediaPlayer player = new MediaPlayer();
    private boolean isPlayerCompleted = false;
    private boolean isPaused = true;

    private Timer mTimer;
    private TimerTask mTimerTask;

    public AudioPlayerView(Context context) {
        super(context);
        initView(context);
    }

    public AudioPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_audio_player, this);
        playerBg = (RelativeLayout) findViewById(R.id.audio_bg);
        playerBtn = (ImageView) findViewById(R.id.audio_btn);
        playerSeek = (SeekBar) findViewById(R.id.audio_seek);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                isPlayerCompleted = true;
                player.seekTo(0);
                playerBg.setBackgroundColor(getResources().getColor(R.color.audio_pause_bg));
                playerBtn.setImageResource(R.drawable.icon_audio_play);
                playerSeek.setProgress(0);
            }
        });

        playerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPaused) {
                    play();
                } else {
                    pause();
                }
            }
        });

        playerSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setTimer() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (!isPlayerCompleted) {
                    playerSeek.setProgress(player.getCurrentPosition());
                } else {
                    mTimer.cancel();
                }
            }
        };
        mTimer.schedule(mTimerTask, 0, 10);
    }

    public void setContent(String audioPath) {
        this.audioPath = audioPath;
        File file = new File(audioPath);
        try {
            player.setDataSource(audioPath);
            player.prepare();
            playerSeek.setMax(getAudioLength());
            setTimer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAudioLength() {
        return player.getDuration();
    }

    public void play() {
        if (isPaused) {
            playerBg.setBackgroundColor(getResources().getColor(R.color.audio_play_bg));
            playerBtn.setImageResource(R.drawable.icon_audio_pause);
            isPaused = !isPaused;

            if (player != null) {
                if (!player.isPlaying()) {
                    player.start();
                }
            }
        }
    }

    public void pause() {
        if (!isPaused) {
            playerBg.setBackgroundColor(getResources().getColor(R.color.audio_pause_bg));
            playerBtn.setImageResource(R.drawable.icon_audio_play);
            isPaused = ! isPaused;

            if (player != null) {
                if (player.isPlaying()) {
                    player.pause();
                }
            }
        }
    }


}
