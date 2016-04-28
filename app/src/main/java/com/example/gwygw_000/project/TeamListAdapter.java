package com.example.gwygw_000.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jpardogo.listbuddies.lib.adapters.CircularLoopAdapter;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/22.
 */
public class TeamListAdapter extends CircularLoopAdapter {

    List<Team> teamList = new LinkedList<Team>();
    Context mContext;
    Library lib = new Library();
    ItemClickListener mItemClickListener;

    public TeamListAdapter (Context context, List<Team> list) {
        mContext = context;
        teamList = list;
    }

    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClcikListener(final ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    protected int getCircularCount() {
        return teamList.size();
    }

    @Override
    public Object getItem(int position) {
        return teamList.get(getCircularPosition(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_teamlogo, parent, false);
        Team team = teamList.get(getCircularPosition(position));
        if (team != null) {
            ImageView teamLogo = (ImageView) v.findViewById(R.id.teamLogo_teamlogo);
            TextView teamName = (TextView) v.findViewById(R.id.teamLogo_teamname);
            teamLogo.setImageResource(R.drawable.teamlogo_dallas);
            Picasso.with(mContext).load(lib.teamLogo.get(team.key)).into(teamLogo);
            teamName.setText(team.getName());
        }
        return v;
    }

    public void addBack(Team team) {
        teamList.add(team);
    }

    public void addFront(Team team) {
        teamList.add(0, team);
    }

}
