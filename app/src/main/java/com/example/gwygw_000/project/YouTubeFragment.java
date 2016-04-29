package com.example.gwygw_000.project;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by gwygw_000 on 2016/4/29.
 */
public class YouTubeFragment extends YouTubePlayerSupportFragment
    implements OnInitializedListener{

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";

    private String mVideoId;

    public YouTubeFragment() {

    }

    public static YouTubeFragment newInstance(final String videoId) {
        YouTubeFragment youTubeFragment = new YouTubeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_VIDEO_ID, videoId);
        youTubeFragment.setArguments(bundle);
        return youTubeFragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (bundle != null && bundle.containsKey(KEY_VIDEO_ID)) {
            mVideoId = bundle.getString(KEY_VIDEO_ID);
        } else if (arguments != null && arguments.containsKey(KEY_VIDEO_ID)) {
            mVideoId = arguments.getString(KEY_VIDEO_ID);
        }
        initialize(YoutubeConfig.YOUTUBE_KEY, this);
    }

    public void setKeyVideoId (String videoId) {
        mVideoId = videoId;
        initialize(YoutubeConfig.YOUTUBE_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//        if (mVideoId != null) {
//            if (b) {
//                youTubePlayer.play();
//            } else {
//                youTubePlayer.loadVideo(mVideoId);
//            }
//        }
        if (mVideoId != null) {
            youTubePlayer.loadVideo(mVideoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            //Handle the failure
            Toast.makeText(getActivity(), "fail to init video", Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle bundle) {
//        super.onSaveInstanceState(bundle);
//        bundle.putString(KEY_VIDEO_ID,mVideoId);
//    }
}
