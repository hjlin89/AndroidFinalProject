package com.example.gwygw_000.project;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/24.
 */
public class VideoListFragment extends Fragment {

    VideoAdapter videoAdapter;

    public static VideoListFragment newInstance() {
        VideoListFragment fragment = new VideoListFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        final VideoListLoadFragment mListener = (VideoListLoadFragment)getContext();

        videoAdapter = new VideoAdapter(getActivity(), getActivity());
        videoAdapter.setOnVideoClickListener(new VideoAdapter.VideoClick() {
            @Override
            public void click(String youtubeKey, String key) {
                mListener.loadVideoFragment(key);
            }
        });
    }

    public interface VideoListLoadFragment {
        void loadVideoFragment(String key);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_videolist,container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.videoListLayout_videolist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(videoAdapter);
        MyDownloadVideolistTask myDownloadVideolistTask = new MyDownloadVideolistTask(videoAdapter);
        String url = FirebaseLib.PHP_SERVER + "videos/";
        myDownloadVideolistTask.execute(url);
        return view;
    }

    private class MyDownloadVideolistTask extends AsyncTask<String, Void, List<String>> {
        private final WeakReference<VideoAdapter> adapterWeakReference;

        public MyDownloadVideolistTask(VideoAdapter adapter) {
            adapterWeakReference = new WeakReference<VideoAdapter>(adapter);
        }

        @Override
        protected List<String> doInBackground(String... urls) {
            List<String> result = FirebaseLib.downloadVideoKey(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            if (adapterWeakReference != null) {
                final VideoAdapter adpter = adapterWeakReference.get();
                if (adpter != null) {
                    adpter.videoID.addAll(strings);
                    adpter.notifyDataSetChanged();
                }
            }
        }
    }
}
