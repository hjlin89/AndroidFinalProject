package com.example.gwygw_000.project;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by gwygw_000 on 2016/4/14.
 */
public class FirebaseLib {
    public static final String ROOT = "https://luminous-heat-2520.firebaseio.com/";
    public static final String TEAM_DATABASE_URL = "https://luminous-heat-2520.firebaseio.com/Teams";
    public static final String PLAYER_BY_TEAM = "https://luminous-heat-2520.firebaseio.com/Players";
    TeamListener teamListener;
    // t

    public interface TeamListener{
        void onTabLayoutBinding(List<Team> teams);
    }

    public void setOnTeamListener(TeamListener mTeamListener) {
        this.teamListener = mTeamListener;
    }

    public List<Team> getTeamList() {
        Log.d("getteamlist", "getteamlist");
        Firebase ref = new Firebase(TEAM_DATABASE_URL);

        final List<Team> result = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot teamSnapshot : dataSnapshot.getChildren()) {
                    Team team = teamSnapshot.getValue(Team.class);
                    result.add(team);
                    Log.d("team", team.getKey());
                }
                teamListener.onTabLayoutBinding(result);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return result;
    }
}
