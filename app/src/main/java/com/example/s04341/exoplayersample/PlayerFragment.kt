package com.example.s04341.exoplayersample

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection




class PlayerFragment : Fragment() {

    var playerView: SurfaceView? = null

    val bandwidthMeter: DefaultBandwidthMeter by lazy {
        DefaultBandwidthMeter()
    }

    val exoPlayer: ExoPlayer? by lazy {
        ExoPlayerFactory.newSimpleInstance(
            context,
            DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerView = view.findViewById(R.id.player_view)

        val uri = Uri.parse("https://bestvpn.org/html5demos/assets/dizzy.mp4")
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "ExoPlayerSample"),bandwidthMeter)
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
        exoPlayer?.prepare(mediaSource)

        (exoPlayer as SimpleExoPlayer).setVideoSurfaceView(playerView)

        exoPlayer?.playWhenReady = true

        playerView?.setOnClickListener {
            exoPlayer?.let {
                it.playWhenReady = !it.playWhenReady
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }
}
