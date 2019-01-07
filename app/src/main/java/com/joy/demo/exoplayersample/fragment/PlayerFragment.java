package com.joy.demo.exoplayersample.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.joy.demo.exoplayersample.MainActivity;
import com.joy.demo.exoplayersample.R;
import com.joy.demo.exoplayersample.playback.PlayManager;

/**
 * Created by lhf on 2018/12/14.
 */

public class PlayerFragment extends Fragment {

    private PlayerView playerView;
    private PlayManager playManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        playerView = view.findViewById(R.id.player_view);
        playManager = ((MainActivity)getActivity()).getPlayManager();
        playManager.setPlayerView(playerView);
        MediaControllerCompat.getMediaController(getActivity()).getTransportControls().play();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        playManager.release();
    }

    @Override
    public void onPause() {
        super.onPause();
        playManager.release();
    }

    @Override
    public void onStop() {
        super.onStop();
        playManager.release();
    }
}
