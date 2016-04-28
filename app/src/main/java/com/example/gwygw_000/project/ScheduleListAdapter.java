package com.example.gwygw_000.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/27.
 */
public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ScheduleItemHolder> {

    List<Schedule> scheduleList;
    private Context mContext ;

    public ScheduleListAdapter(List<Schedule> list, Context context) {
        mContext = context;
        scheduleList = list;
    }

    public void addAll(List<Schedule> list) {
        scheduleList.addAll(list);
    }

    @Override
    public ScheduleItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_schedule, parent, false);
        ScheduleItemHolder itemHolder = new ScheduleItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleItemHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);

        Picasso.with(mContext).load(schedule.homeLogo).into(holder.homeTeamLogo);
        Picasso.with(mContext).load(schedule.awayLogo).into(holder.awayTeamLogo);
        holder.homeName.setText(schedule.homeName);
        holder.homeScore.setText(schedule.homeScore);
        holder.awayName.setText(schedule.awayName);
        holder.awayScore.setText(schedule.awayScore);
        holder.statu.setText(schedule.status);
        holder.data.setText(schedule.data);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ScheduleItemHolder extends RecyclerView.ViewHolder{

         ImageView homeTeamLogo;
         ImageView awayTeamLogo;
         TextView homeName;
         TextView awayName;
         TextView homeScore;
         TextView awayScore;
         TextView statu;
         TextView data;

        public ScheduleItemHolder(View itemView) {
            super(itemView);
            homeTeamLogo = (ImageView)itemView.findViewById(R.id.schedule_logo1);
            awayTeamLogo = (ImageView)itemView.findViewById(R.id.schedule_logo2);
            homeName = (TextView)itemView.findViewById(R.id.schedule_teamname1);
            awayName = (TextView)itemView.findViewById(R.id.schedule_teamname2);
            homeScore = (TextView)itemView.findViewById(R.id.schedule_teamscore1);
            awayScore = (TextView)itemView.findViewById(R.id.schedule_teamscore2);
            statu = (TextView)itemView.findViewById(R.id.schedule_status);
            data = (TextView)itemView.findViewById(R.id.schedule_data);
        }
    }

}
