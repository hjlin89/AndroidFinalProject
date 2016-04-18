package com.example.gwygw_000.project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/14.
 */
public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ItemViewHolder> {

    private List<Player> mPlayers;

    public PlayerListAdapter(List<Player> mPlayers) {
        this.mPlayers = mPlayers;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPhoto;
        private TextView mName;
        private TextView mPos;
        private TextView mData;
        private CheckBox mLike;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mPhoto = (ImageView)itemView.findViewById(R.id.cardview_playerPhoto);
            mName = (TextView)itemView.findViewById(R.id.cardview_playername);
            mPos = (TextView)itemView.findViewById(R.id.cardview_playerposition);
            mData = (TextView)itemView.findViewById(R.id.cardview_playerData);
            mLike = (CheckBox)itemView.findViewById(R.id.cardview_playerLike);
        }
    }
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_player, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
//        itemViewHolder.mName.setText(mPlayers.get(position).lastName + " " + mPlayers.get(position).firstName);
//        itemViewHolder.mPos.setText(mPlayers.get(position).team + "" + mPlayers.get(position).position);
//        itemViewHolder.mData.setText(mPlayers.get(position).height);
 //       itemViewHolder.mPhoto.setImageResource(R.drawable.jonasjerebko);
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }
}
