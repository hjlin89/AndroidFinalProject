package com.example.gwygw_000.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.CubeInTransformer;
import com.eftimoff.viewpagertransformers.FlipVerticalTransformer;
import com.eftimoff.viewpagertransformers.RotateDownTransformer;
import com.eftimoff.viewpagertransformers.ZoomInTransformer;

import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/14.
 */
public class TeamPlayerListFragment extends Fragment {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TeamPlayerviewPagerAdapter adapter;
    private static final String TEAM = "TEAM";

    FirebaseLib firebaseLib = new FirebaseLib();

    public static TeamPlayerListFragment newInstance(String team) {
        TeamPlayerListFragment fragment = new TeamPlayerListFragment();
        Bundle args = new Bundle();
        args.putString(TEAM, team);
        fragment.setArguments(args);
        return fragment;
    }

    public static TeamPlayerListFragment newInstance() {
        TeamPlayerListFragment newFragment = new TeamPlayerListFragment();
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.layout_team_playerlist, container, false);
        toolbar = (Toolbar)rootView.findViewById(R.id.team_playerlist_toolbar);
        toolbar.setTitle("Plays");
        if (toolbar.getMenu().findItem(R.id.teamplayerlistsearch) == null) {
            toolbar.inflateMenu(R.menu.teamplayerlist_menu);
            SearchView search = (SearchView)toolbar.getMenu().findItem(R.id.teamplayerlistsearch).getActionView();
            if(search != null) {
                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        List<String> title = adapter.mFragmentTitle;
                        int i = 0;
                        while (i < title.size() && !title.get(i).equals(query.toUpperCase())) {
                            i++;
                        }
                        if (i == title.size()) {
                            return false;
                        }
                        viewPager.setCurrentItem(i);
                        return true;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
            }
            //setupShareAction();

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.AccordionTransformer:
                            viewPager.setPageTransformer(true, new AccordionTransformer());
                            return true;
                        case R.id.CubeInTransformer:
                            viewPager.setPageTransformer(true, new CubeInTransformer());
                            return true;
                        case R.id.ZoomInTransformer:
                            viewPager.setPageTransformer(true, new ZoomInTransformer());
                            return true;
                        case R.id.RotateDownTransformer:
                            viewPager.setPageTransformer(true, new RotateDownTransformer());
                            return true;
                        case R.id.FlipVerticalTransformer:
                            viewPager.setPageTransformer(true, new FlipVerticalTransformer());
                            return true;
                        case R.id.teamplayerlist_action_share:
                            String team = adapter.mFragmentTitle.get(viewPager.getCurrentItem());
                            Intent intentShare = new Intent(Intent.ACTION_SEND);
                            intentShare.setType("text/plain");
                            intentShare.putExtra(Intent.EXTRA_SUBJECT, "I like this team");
                            intentShare.putExtra(Intent.EXTRA_TEXT, team);
                            //mShareActionProvider.setShareIntent(intentShare);
                            startActivity(Intent.createChooser(intentShare, "Share via"));
                            return true;
                    }
                    return false;
                }
            });
        }

        //getActivity().setSupportActionBar(toolbar);

        viewPager = (ViewPager) rootView.findViewById(R.id.team_playerlist_viewpager);
        adapter = new TeamPlayerviewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) rootView.findViewById(R.id.team_playerlist_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        firebaseLib.setOnTeamListener(new FirebaseLib.TeamListener() {
            @Override
            public void onTabLayoutBinding(List<Team> teams) {
                for (Team t : teams) {
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

    private void setupShareAction() {
        MenuItem shareItem = toolbar.getMenu().findItem(R.id.teamplayerlist_action_share);
        shareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_SUBJECT, "I like this team");
                intentShare.putExtra(Intent.EXTRA_TEXT, "aaaaaa");
                //mShareActionProvider.setShareIntent(intentShare);
                startActivity(Intent.createChooser(intentShare, "Share via"));
                return true;
            }
        });
//        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
//
//        Intent intentShare = new Intent(Intent.ACTION_SEND);
//        intentShare.setType("text/plain");
//        intentShare.putExtra(Intent.EXTRA_SUBJECT, "I like this team");
//        intentShare.putExtra(Intent.EXTRA_TEXT, "aaaaaa");
//        //mShareActionProvider.setShareIntent(intentShare);
//        startActivity(Intent.createChooser(intentShare, "Share via"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem shareItem = toolbar.getMenu().findItem(R.id.teamplayerlist_action_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_SUBJECT, "I like this team");
        intentShare.putExtra(Intent.EXTRA_TEXT, "aaaaaa");
        mShareActionProvider.setShareIntent(intentShare);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
