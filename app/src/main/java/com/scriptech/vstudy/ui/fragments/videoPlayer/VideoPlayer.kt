package com.scriptech.vstudy.ui.fragments.videoPlayer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.scriptech.vstudy.databinding.FragVideoplayerBinding
import com.scriptech.vstudy.ui.activities.mainActivity2.Main2Activity

class VideoPlayer : Fragment() {

    private var _binding: FragVideoplayerBinding? = null

    private val binding get() = _binding!!

    private val args by navArgs<VideoPlayerArgs>()

    private lateinit var exoPlayerView: SimpleExoPlayerView

    private lateinit var exoPlayer: SimpleExoPlayer

    private val videoURL by lazy {
        args.link
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragVideoplayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        exoPlayerView = binding.idExoPlayerVIew

        try {
            val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()

            val trackSelector: TrackSelector =
                DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))

            exoPlayer = ExoPlayerFactory.newSimpleInstance(activity, trackSelector)

            val videoURI: Uri = Uri.parse(videoURL)

            val dataSourceFactory =
                DefaultHttpDataSourceFactory("Exoplayer_video")

            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()

            val mediaSource: MediaSource =
                ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null)

            exoPlayerView.player = exoPlayer

            exoPlayer.prepare(mediaSource)

            exoPlayer.playWhenReady = true

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as Main2Activity?)!!.hideBottomNav()
        (activity as Main2Activity?)!!.landscape()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.stop()
        (activity as Main2Activity?)!!.showBottomNav()
        (activity as Main2Activity?)!!.portrait()
    }
}