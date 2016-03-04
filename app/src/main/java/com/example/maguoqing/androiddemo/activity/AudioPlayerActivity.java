package com.example.maguoqing.androiddemo.activity;

import android.os.Environment;

import com.example.maguoqing.androiddemo.R;
import com.example.maguoqing.androiddemo.base.BaseActivity;
import com.example.maguoqing.androiddemo.view.AudioPlayerView;

/**
 * Created by magq on 16/2/26.
 */
public class AudioPlayerActivity extends BaseActivity {

    @ViewId(R.id.audio_player)
    private AudioPlayerView audioPlayerView;

    @Override
    protected void setListeners() {

    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls() {
        String path = Environment.getExternalStorageDirectory() + "/Music/Download/mp3/test.mp3";
        audioPlayerView.setContent(path);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_audio_player;
    }
}
