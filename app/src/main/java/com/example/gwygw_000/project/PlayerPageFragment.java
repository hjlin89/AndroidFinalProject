package com.example.gwygw_000.project;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayerPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerPageFragment extends Fragment {

    private HashMap<String, ?> playerInfo;

    public PlayerPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerPageFragment newInstance(HashMap<String, ?> player) {
        PlayerPageFragment fragment = new PlayerPageFragment();
        Bundle args = new Bundle();
        args.putSerializable("Player", player);
        Log.d("Test for player", player.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerInfo = (HashMap<String,?>) getArguments().getSerializable("Player");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_page, container, false);

        final TextView name = (TextView) view.findViewById(R.id.player_name);
        final TextView team = (TextView) view.findViewById(R.id.player_team);
        final TextView position = (TextView) view.findViewById(R.id.player_position);
        final TextView jersey = (TextView) view.findViewById(R.id.player_jersey);
        final TextView height = (TextView) view.findViewById(R.id.player_height);
        final TextView weight = (TextView) view.findViewById(R.id.player_weight);
        final TextView experience = (TextView) view.findViewById(R.id.player_experience);
        final TextView status = (TextView) view.findViewById(R.id.player_status);
        final ImageView imageView = (ImageView) view.findViewById(R.id.player_image);

        final ImageView toolbar_image = (ImageView) view.findViewById(R.id.toolbar_image);
        Picasso.with(getContext()).load("http://i1.hoopchina.com.cn/u/1604/25/480/3064480/155c5b8ajpg.jpg").into(toolbar_image);

        Picasso.with(getContext()).load(playerInfo.get("PhotoUrl").toString()).into(imageView);


        name.setText(playerInfo.get("FirstName") + " " + playerInfo.get("LastName"));
        team.setText(playerInfo.get("Team").toString());
        position.setText((playerInfo.get("Position")).toString());
        jersey.setText(playerInfo.get("Jersey").toString());
        height.setText(playerInfo.get("Height").toString());
        weight.setText(playerInfo.get("Weight").toString());
        experience.setText(playerInfo.get("Experience").toString());
        status.setText(playerInfo.get("Status").toString());

        return view;
    }

}
