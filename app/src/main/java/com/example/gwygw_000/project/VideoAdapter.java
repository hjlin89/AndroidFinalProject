package com.example.gwygw_000.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.LinkedList;

/**
 * Created by gwygw_000 on 2016/4/24.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoInfoHolder> {

    Context mContext;
    Activity mactivity;
    VideoClick mVideoClick;

    LinkedList<String> videoID = new LinkedList<String>();


    public void addItem(String str) {
        videoID.add(str);
    }

    public VideoAdapter(Context context, Activity activity) {

        this.mContext = context;
        this.mactivity = activity;
    }


    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_youtubeplayer,parent,false);
        return  new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {
        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYoutubeThumbnailView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }
        };
        holder.youTubeThumbnailView.initialize(YoutubeConfig.YOUTUBE_KEY, new YouTubeThumbnailView.OnInitializedListener(){

            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(videoID.get(position));
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    public interface VideoClick {
        void click(String youtubeKey, String key);
    }

    public void setOnVideoClickListener(final VideoClick videoClick) {
        this.mVideoClick = videoClick;
    }

    @Override
    public int getItemCount() {
        return videoID.size();
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYoutubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton = (ImageView)itemView.findViewById(R.id.cardview_youtube_playButton);
            playButton.setOnClickListener(this);
            relativeLayoutOverYoutubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.cardview_youtube_relativelayout_over_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.cardview_youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {
            //mVideoClick.click("", videoID.get(getLayoutPosition()));
//            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity)mContext, YoutubeConfig.YOUTUBE_KEY, videoID.get(getLayoutPosition()),100,true, false);
//
//            mactivity.startActivity(intent);
            mContext.startActivity(YouTubeIntents.createPlayVideoIntent(mContext, videoID.get(getLayoutPosition())));
        }
    }
}
