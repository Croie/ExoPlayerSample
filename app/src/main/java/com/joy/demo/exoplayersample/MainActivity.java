package com.joy.demo.exoplayersample;

import android.os.Build;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.joy.demo.exoplayersample.fragment.PlayerFragment;
import com.joy.demo.exoplayersample.playback.PlayManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_SESSION = "ExoPlayer_demo";
    private static final String FRAGMENT_TAG = "demo_list_container";
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder builder;
    private PlayerFragment playerFragment;
    private PlayManager playManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playManager = new PlayManager(this);
        navigateToFragment();
        mMediaSession = new MediaSessionCompat(this, TAG_SESSION);

        //media session 能接收来自media controllers and media buttons的回调设置
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        //当app不可见的时候，不让MediaButton启用播放器 ,  仅仅对sdk本在5.0以上的设备有效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMediaSession.setMediaButtonReceiver(null);
        }

        builder = new PlaybackStateCompat.Builder()
                        .setActions(PlaybackStateCompat.ACTION_PLAY |
                                    PlaybackStateCompat.ACTION_PLAY_PAUSE);
        // 1.set up playback state
        mMediaSession.setPlaybackState(builder.build());

        // 2.set up Callback receive mediaController operation
        mMediaSession.setCallback(playManager.getMediaSessionCallback());

        MediaControllerCompat mediaController = new MediaControllerCompat(this, mMediaSession);
        MediaControllerCompat.setMediaController(this, mediaController);
    }

    /**
     * invoke the method, when user coming the interface
     *
     * */
    private void navigateToFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        playerFragment = getPlayFragment();
        if (playerFragment == null) {
            playerFragment = new PlayerFragment();
            fragmentTransaction.add(R.id.play_container, playerFragment, FRAGMENT_TAG);
        } else {
            fragmentTransaction.replace(R.id.play_container, playerFragment, FRAGMENT_TAG);
        }
        fragmentTransaction.commit();
    }

    private PlayerFragment getPlayFragment(){
        return (PlayerFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    public PlayManager getPlayManager(){
        return playManager;
    }
}
