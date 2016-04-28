package com.example.gwygw_000.project;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gwygw_000 on 2016/4/14.
 */
public class PlayerListFragment extends Fragment {
    private RecyclerView recyclerview;
    final Firebase ref = new Firebase(FirebaseLib.PLAYER_BY_TEAM);
    PlayerListFirebaseAdapter mRecyclerViewAdapter;
    PlayersData playersData;
    private OnFragmentInteractionListener mListener;


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
        playersData = new PlayersData(getArguments().getString(REF));
        Log.d("Test for player", playersData.getList().toString());
        mRecyclerViewAdapter = new PlayerListFirebaseAdapter(Player.class, R.layout.cardview_player, PlayerListFirebaseAdapter.PlayerViewHolder.class, ref.child(getArguments().getString(REF)), getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_playerlist, container, false);
        recyclerview = (RecyclerView)view.findViewById(R.id.palyerlist_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        mRecyclerViewAdapter.setOnItemClickListener(new PlayerListFirebaseAdapter.PlayerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.onListItemSelected(position, playersData.getItem(position));
            }
        });

        recyclerview.setAdapter(mRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onListItemSelected(int position, HashMap<String, String> player);
    }
}
