package com.example.gwygw_000.project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/27.
 */
public class ScheduleListFragment extends Fragment {

    RecyclerView recyclerView;
    private final static String URL = "https://api.fantasydata.net/nba/v2/JSON/Games/2016";
    ScheduleListAdapter adapter;

    public static ScheduleListFragment newInstance() {
        ScheduleListFragment fragment = new ScheduleListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ScheduleListAdapter(new ArrayList<Schedule>(), getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_schedulelist, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.layoutScheduleList_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        DownloadSchedule newDownload = new DownloadSchedule(adapter);
        newDownload.execute(URL);

        return view;
    }

    private class DownloadSchedule extends AsyncTask<String, Void, List<Schedule>> {

        private final WeakReference<ScheduleListAdapter> adapterWeakReference;

        public DownloadSchedule(ScheduleListAdapter adapter) {
            adapterWeakReference = new WeakReference<ScheduleListAdapter>(adapter);
        }

        @Override
        protected List<Schedule> doInBackground(String... urls) {
            List<Schedule> scheduleList = FirebaseLib.downloadSchedules(urls[0]);
            return scheduleList;
        }

        @Override
        protected void onPostExecute(List<Schedule> schedules) {
            if (adapterWeakReference != null) {
                final ScheduleListAdapter adapter = adapterWeakReference.get();
                if (adapter != null) {
                    adapter.addAll(schedules);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
