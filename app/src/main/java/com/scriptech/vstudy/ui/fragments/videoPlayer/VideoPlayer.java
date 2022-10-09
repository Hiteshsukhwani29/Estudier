package com.scriptech.vstudy.ui.fragments.videoPlayer;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.scriptech.vstudy.ui.activities.mainActivity2.Main2Activity;
import com.scriptech.vstudy.R;


public class VideoPlayer extends Fragment {

    SimpleExoPlayerView exoPlayerView;

    SimpleExoPlayer exoPlayer;

    String videoURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_videoplayer, container, false);

        ((Main2Activity) getActivity()).hideBottomNav();
        ((Main2Activity) getActivity()).landscape();
        exoPlayerView = v.findViewById(R.id.idExoPlayerVIew);
        videoURL = getArguments().getString("video_Link");

        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            Uri videouri = Uri.parse(videoURL);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);
            exoPlayerView.setPlayer(exoPlayer);

            exoPlayer.prepare(mediaSource);

            exoPlayer.setPlayWhenReady(true);

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Unable to Play Video", Toast.LENGTH_SHORT).show();

        }

        return v;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        exoPlayer.stop();
        ((Main2Activity) getActivity()).showBottomNav();
        ((Main2Activity) getActivity()).portrait();
    }

}
