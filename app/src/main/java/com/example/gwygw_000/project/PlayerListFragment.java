package com.example.gwygw_000.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;


/**
 * Created by gwygw_000 on 2016/4/14.
 */
public class PlayerListFragment extends Fragment {
    private RecyclerView recyclerview;
    final Firebase ref = new Firebase(FirebaseLib.PLAYER_BY_TEAM);
    PlayerListFirebaseAdapter mRecyclerViewAdapter;
    private static final String REF = "REF";

    public static PlayerListFragment newInstance(String team) {
        PlayerListFragment fragment = new PlayerListFragment();
        Bundle args = new Bundle();
        args.putString(REF, team);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ref.child(getArguments().getString(REF));
        mRecyclerViewAdapter = new PlayerListFirebaseAdapter(Player.class, R.layout.cardview_player, PlayerListFirebaseAdapter.PlayerViewHolder.class, ref.child(getArguments().getString(REF)), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_playerlist, container, false);
        recyclerview = (RecyclerView)view.findViewById(R.id.palyerlist_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        recyclerview.setAdapter(mRecyclerViewAdapter);
        return view;
    }
}
