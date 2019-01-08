package com.example.s04341.exoplayersample

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceEventListener
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.IOException


class PlayerFragment : Fragment() {

    var playerView: PlayerView? = null

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

        val uri =
            Uri.parse("https://manifest.googlevideo.com/api/manifest/hls_variant/mt/1546938329/id/e2fa08fe4325f20b/mn/sn-ogul7n7z,sn-ogueln7k/mm/31,29/fvip/2/ms/au,rdu/mv/m/source/youtube/ip/202.214.112.129/key/yt6/ipbits/0/sparams/ei,go,hfr,hls_chunk_host,id,initcwndbps,ip,ipbits,itag,maudio,mm,mn,ms,mv,pl,playlist_type,ratebypass,requiressl,source,tts_caps,expire/ratebypass/yes/maudio/1/keepalive/yes/requiressl/yes/tts_caps/1/initcwndbps/1203750/signature/7CCA5590707E20EC67784AEE3DF5FE2474AF1B86.2AD7C3EFE3796D6A9EB61BEBAEEB96F9CD48AAF5/itag/0/hfr/1/dover/11/hls_chunk_host/r2---sn-ogul7n7z.googlevideo.com/playlist_type/DVR/ei/T2g0XM3KCMfL4AKXsaXoAg/pl/16/go/1/expire/1546960047/file/index.m3u8")
        //hlsvp -> .m3u8取得
        //%3A -> :
        //%2F -> /
        //%25 -> %
        //%2C -> ,
        val dataSourceFactory =
            DefaultDataSourceFactory(context, Util.getUserAgent(context, "ExoPlayerSample"), bandwidthMeter)

        // Optional event handler
        val handler = Handler()
        val eventListener = object : AdaptiveMediaSourceEventListener {
            override fun onLoadStarted(
                windowIndex: Int,
                mediaPeriodId: MediaSource.MediaPeriodId?,
                loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
                mediaLoadData: MediaSourceEventListener.MediaLoadData?
            ) {

            }

            override fun onLoadCompleted(
                windowIndex: Int,
                mediaPeriodId: MediaSource.MediaPeriodId?,
                loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
                mediaLoadData: MediaSourceEventListener.MediaLoadData?
            ) {
//                //再生
//                exoPlayer?.playWhenReady = true
//                //リピートon
//                exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL
            }

            override fun onLoadCanceled(
                windowIndex: Int,
                mediaPeriodId: MediaSource.MediaPeriodId?,
                loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
                mediaLoadData: MediaSourceEventListener.MediaLoadData?
            ) {

            }

            override fun onLoadError(
                windowIndex: Int,
                mediaPeriodId: MediaSource.MediaPeriodId?,
                loadEventInfo: MediaSourceEventListener.LoadEventInfo?,
                mediaLoadData: MediaSourceEventListener.MediaLoadData?,
                error: IOException?,
                wasCanceled: Boolean
            ) {

            }

            override fun onUpstreamDiscarded(
                windowIndex: Int,
                mediaPeriodId: MediaSource.MediaPeriodId?,
                mediaLoadData: MediaSourceEventListener.MediaLoadData?
            ) {

            }

            override fun onDownstreamFormatChanged(
                windowIndex: Int,
                mediaPeriodId: MediaSource.MediaPeriodId?,
                mediaLoadData: MediaSourceEventListener.MediaLoadData?
            ) {
                Toast.makeText(context,"changed!",Toast.LENGTH_SHORT).show()
            }

            override fun onMediaPeriodCreated(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {

            }

            override fun onMediaPeriodReleased(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {

            }

            override fun onReadingStarted(windowIndex: Int, mediaPeriodId: MediaSource.MediaPeriodId?) {

            }
        }

        exoPlayer?.prepare(HlsMediaSource(uri, dataSourceFactory, handler, eventListener))

//        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
//        exoPlayer?.prepare(mediaSource)

        //SurfaceViewに反映
        //(exoPlayer as SimpleExoPlayer).setVideoSurfaceView(playerView)
        playerView?.player = exoPlayer
        //再生
        exoPlayer?.playWhenReady = true
        //リピートon
        exoPlayer?.repeatMode = Player.REPEAT_MODE_ALL

        val logBtn: Button =view.findViewById(R.id.log_btn)
        logBtn.setOnClickListener {
            Toast.makeText(context,exoPlayer?.currentWindowIndex.toString(),Toast.LENGTH_SHORT).show()
        }
        //画面クリック
//        playerView?.setOnClickListener {
//            exoPlayer?.let {
//                //停止←→再生
//                it.playWhenReady = !it.playWhenReady
//                //it.seekTo(0)
//            }
//        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }
}
