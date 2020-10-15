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
            Uri.parse("https://manifest.googlevideo.com/api/manifest/hls_variant/expire/1602769782/ei/Fv-HX8zYBqyG1d8P3ru22Ak/ip/126.255.82.97/id/aef931b5592f6b07/source/youtube/requiressl/yes/playback_host/r3---sn-ogul7n7s.googlevideo.com/mh/12/mm/31%2C26/mn/sn-ogul7n7s%2Csn-3pm7kn7l/ms/au%2Conr/mv/m/mvi/3/pl/16/tx/23935847/txs/23935842%2C23935843%2C23935844%2C23935845%2C23935846%2C23935847%2C23935848%2C23935849%2C23935850/hfr/1/ctier/A/maxh/1080/tts_caps/1/maudio/1/pfa/5/initcwndbps/1101250/hightc/yes/vprv/1/go/1/mt/1602748012/fvip/3/keepalive/yes/fexp/23915654/dover/11/itag/0/playlist_type/DVR/sparams/expire%2Cei%2Cip%2Cid%2Csource%2Crequiressl%2Ctx%2Ctxs%2Chfr%2Cctier%2Cmaxh%2Ctts_caps%2Cmaudio%2Cpfa%2Chightc%2Cvprv%2Cgo%2Citag%2Cplaylist_type/sig/AOq0QJ8wRgIhAOJvzPrrNM2O6yGy5moApMwT-l2vGQ93gIsq9s0B2DhMAiEAgR13KsXS-Je1ax5FdcNQUNKH8jYZCODdDU5xxxxEp-E%3D/lsparams/playback_host%2Cmh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps/lsig/AG3C_xAwRQIhAMe7Rb7OD09riDCF6G49_ZBV4vc9xlKFwmAA-5IAJFwZAiBBBAwA8UGE47UOl3O_OssPxbxJiz5McGvN574Z-rz3-Q%3D%3D/file/index.m3u8")
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
