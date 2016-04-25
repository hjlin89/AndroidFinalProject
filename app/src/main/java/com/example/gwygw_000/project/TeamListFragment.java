package com.example.gwygw_000.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;

import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/22.
 */
public class TeamListFragment extends Fragment
    implements ListBuddiesLayout.OnBuddyItemClickListener {
    TeamListAdapter leftAdapter;
    TeamListAdapter rightAdapter;
    ListBuddiesLayout listbuddies;

    FirebaseLib firebaseLib = new FirebaseLib();

    public static TeamListFragment newInstance() {
        TeamListFragment fragment = new TeamListFragment();
        return fragment;
    }

    @Override
    public void onBuddyItemClicked(AdapterView<?> parent, View view, int buddy, int position, long id) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_teamlist, container, false);
        listbuddies = (ListBuddiesLayout)rootView.findViewById(R.id.team_list_listbuddieslayout);
        leftAdapter = new TeamListAdapter();
        rightAdapter = new TeamListAdapter();
        listbuddies.setAdapters(leftAdapter, rightAdapter);

        firebaseLib.setOnTeamListener(new FirebaseLib.TeamListener() {
            @Override
            public void onTabLayoutBinding(List<Team> teams) {
                for (int i = 0; i < teams.size(); i++) {
                    leftAdapter.addFront(teams.get(i));
                    rightAdapter.addBack(teams.get(i));
                }
                leftAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
            }
        });
        firebaseLib.getTeamList();
        return rootView;
    }
}
