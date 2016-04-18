package com.example.gwygw_000.project;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/14.
 */
public class TeamPlayerListFragment extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    FirebaseLib firebaseLib = new FirebaseLib();

    public static TeamPlayerListFragment newInstance() {
        TeamPlayerListFragment newFragment = new TeamPlayerListFragment();
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_team_playerlist, container, false);
        toolbar = (Toolbar)rootView.findViewById(R.id.team_playerlist_toolbar);
        //getActivity().setSupportActionBar(toolbar);

        viewPager = (ViewPager) rootView.findViewById(R.id.team_playerlist_viewpager);
        final TeamPlayerviewPagerAdapter adapter = new TeamPlayerviewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) rootView.findViewById(R.id.team_playerlist_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        firebaseLib.setOnTeamListener(new FirebaseLib.TeamListener() {
            @Override
            public void onTabLayoutBinding(List<Team> teams) {
                for (Team t : teams) {
                    Log.d("call", t.getKey());
                    adapter.addFragment(PlayerListFragment.newInstance(t.getKey()), t.getKey());
                }
                //adapter.addFragment(PlayerListFragment.newInstance("GS"),"GS");
                adapter.notifyDataSetChanged();
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        firebaseLib.getTeamList();
        return rootView;
    }
}
