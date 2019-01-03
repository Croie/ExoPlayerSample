package com.joy.demo.exoplayersample.playback;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

/**
 * Created by lhf on 2018/12/28.
 */

public class PlayManager implements Playback{
    private MediaSessionCompat.Callback mCallback;
    private SimpleExoPlayer exoPlayer;
    private PlayerView playerView;
    private Context context;

    public PlayManager(Context context, PlayerView playView){
        mCallback = new MediaSessionCallback();
        playerView = playView;
        this.context = context;

    }

    public PlayManager(Context context){
        mCallback = new MediaSessionCallback();
        this.context = context;

    }
    public void setPlayerView(PlayerView playView){
        mCallback = new MediaSessionCallback();
        playerView = playView;
    }
    public MediaSessionCompat.Callback getMediaSessionCallback(){
        return mCallback;
    }

    @Override
    public void release() {
        releasePlayer();
    }

    private void releasePlayer(){
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        exoPlayer = null;
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback{
        @Override
        public void onPlay() {
            initializePlayer();
        }

        @Override
        public void onSkipToQueueItem(long queueId) {

        }

        @Override
        public void onSeekTo(long position) {
            if (exoPlayer != null) {
                exoPlayer.seekTo(position);
            }
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {

        }

        @Override
        public void onPause() {
            if (exoPlayer != null) {
                exoPlayer.setPlayWhenReady(false);
            }
//            releasePlayer();
        }

        @Override
        public void onStop() {
            releasePlayer();
        }

        @Override
        public void onSkipToNext() {

        }

        @Override
        public void onSkipToPrevious() {

        }

        @Override
        public void onCustomAction(@NonNull String action, Bundle extras) {

        }

        /**
         * Handle free and contextual searches.
         * <p/>
         * All voice searches on Android Auto are sent to this method through a connected
         * {@link android.support.v4.media.session.MediaControllerCompat}.
         * <p/>
         * Threads and async handling:
         * Search, as a potentially slow operation, should run in another thread.
         * <p/>
         * Since this method runs on the main thread, most apps with non-trivial metadata
         * should defer the actual search to another thread (for example, by using
         * an {@link AsyncTask} as we do here).
         **/
        @Override
        public void onPlayFromSearch(final String query, final Bundle extras) {

        }
    }

    private void initializePlayer(){
        TrackSelector trackSelector = new DefaultTrackSelector();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        playerView.setPlayer(exoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, "ExoPlayerSample");
        MediaSource videoMedia = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse("https://html5demos.com/assets/dizzy.mp4"));
        exoPlayer.prepare(videoMedia);
        exoPlayer.setPlayWhenReady(true);
    }
}
