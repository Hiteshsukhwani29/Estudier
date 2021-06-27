package com.scriptech.vstudy;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


public class VideoPlayer extends Fragment {

    private static String VIDEO_SAMPLE =
            "https://media.geeksforgeeks.org/wp-content/uploads/20210113001807/Screenrecorder-2021-01-13-00-17-04-402.mp4?_=1";

    private VideoView mVideoView;
    private int mCurrentPosition = 0;

    private static final String PLAYBACK_TIME = "play_time";


    public VideoPlayer() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag_videoplayer, container, false);
        mVideoView = v.findViewById(R.id.videoview);

        VIDEO_SAMPLE=getArguments().getString("video_Link");

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        MediaController controller = new MediaController(getActivity());
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    private void initializePlayer() {
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);

        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            mVideoView.seekTo(1);
                        }

                        mVideoView.start();
                    }
                });

        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getActivity(),
                                "Hello",
                                Toast.LENGTH_SHORT).show();

                        mVideoView.seekTo(0);
                    }
                });
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            return Uri.parse(mediaName);
        } else {


            return Uri.parse("android.resource://" + getActivity().getPackageName() +
                    "/raw/" + mediaName);


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Main2Activity.hideBottomNav();
        ((Main2Activity) getActivity()).landscape();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Main2Activity.showBottomNav();
        ((Main2Activity) getActivity()).portrait();
    }

}
