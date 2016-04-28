package com.example.gwygw_000.project;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/14.
 */
public class FirebaseLib {
    public static final String ROOT = "https://luminous-heat-2520.firebaseio.com/";
    public static final String TEAM_DATABASE_URL = "https://luminous-heat-2520.firebaseio.com/Teams";
    public static final String PLAYER_BY_TEAM = "https://luminous-heat-2520.firebaseio.com/Players";
    public static final String PHP_SERVER = "http://wenyuan.gao.com/";
    TeamListener teamListener;
    // t

    public interface TeamListener{
        void onTabLayoutBinding(List<Team> teams);
    }

    public void setOnTeamListener(TeamListener mTeamListener) {
        this.teamListener = mTeamListener;
    }

    public List<Team> getTeamList() {
        //Log.d("getteamlist", "getteamlist");
        Firebase ref = new Firebase(TEAM_DATABASE_URL);

        final List<Team> result = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot teamSnapshot : dataSnapshot.getChildren()) {
                    Team team = teamSnapshot.getValue(Team.class);
                    result.add(team);
                    //Log.d("team", team.getKey());
                }
                teamListener.onTabLayoutBinding(result);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return result;
    }

    public static List<String> downloadVideoKey(String url) {
        List<String> result = new ArrayList<>();
        String ans = MyUtility.downloadJSONusingHTTPGetRequest(url);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(ans);
        } catch (Exception e) {
            Log.d("can't creat JSONArray", e.getMessage());
        }
        if (jsonArray == null) {
            return result;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject newJson = null;
            try {
                newJson = jsonArray.getJSONObject(i);
                //Log.d("aaaaa", (String) newJson.get("VideoKey"));
                result.add((String) newJson.get("VideoKey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static List<Schedule> downloadSchedules(String url) {
        List<Schedule> result = new ArrayList<>();
        String str = MyUtility.downloadFromThirdParty(url);
        JSONArray jsonArray = null;
        Library lib = new Library();
        try {
            jsonArray = new JSONArray(str);
            if (jsonArray == null) {
                return result;
            }
            for (int i = jsonArray.length() - 1; i >= jsonArray.length() - 20; i--) {
                JSONObject newJson = jsonArray.getJSONObject(i);
                Schedule newSchedule = parseSchedule(newJson, lib.teamLogo);
                result.add(newSchedule);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Schedule parseSchedule(JSONObject j, HashMap<String, String> lib) {

        Schedule newSchedule = new Schedule();
        try {
            newSchedule.homeName = j.get("HomeTeam").toString();
            newSchedule.awayName = j.get("AwayTeam").toString();
            newSchedule.homeLogo = lib.get(newSchedule.homeName);
            newSchedule.awayLogo = lib.get(newSchedule.awayName);
            newSchedule.homeScore = j.get("HomeTeamScore").toString();
            newSchedule.awayScore = j.get("AwayTeamScore").toString();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(j.get("DateTime").toString());
            if (date.before(c.getTime())) {
                newSchedule.status = "FINISHED";
            } else {
                newSchedule.status = "WAITING";
            }
            newSchedule.data = df.format(date);
        } catch (JSONException e) {
            Log.d("error", e.getMessage());
            e.printStackTrace();
        } catch (ParseException e) {
            Log.d("error", e.getMessage());
            e.printStackTrace();
        }
    return newSchedule;
    }
}
