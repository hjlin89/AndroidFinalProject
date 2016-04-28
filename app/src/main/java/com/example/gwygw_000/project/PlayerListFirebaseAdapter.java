package com.example.gwygw_000.project;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by gwygw_000 on 2016/4/17.
 */
public class PlayerListFirebaseAdapter extends FirebaseRecyclerAdapter<Player, PlayerListFirebaseAdapter.PlayerViewHolder> {

    private Context mContext;
    static PlayerViewHolder.OnItemClickListener mItemClickListener;


    public PlayerListFirebaseAdapter(Class<Player> playerClass, int modelLayout,
                                     Class<PlayerListFirebaseAdapter.PlayerViewHolder> holder, Query ref, Context context) {
        super(playerClass, modelLayout, holder, ref);
        this.mContext = context;
    }

    public void setOnItemClickListener(final PlayerViewHolder.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    @Override
    protected void populateViewHolder(PlayerViewHolder itemViewHolder, Player mPlayers, int i) {
        itemViewHolder.mName.setText(mPlayers.getLastName() + " " + mPlayers.getLastName());
        itemViewHolder.mPos.setText(mPlayers.getTeam() + "|" + mPlayers.getPosition());
        itemViewHolder.mData.setText(String.valueOf(mPlayers.getHeight()));
        Picasso.with(mContext).load(mPlayers.getPhotoUrl()).into(itemViewHolder.mPhoto);
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPhoto;
        private TextView mName;
        private TextView mPos;
        private TextView mData;
        private CheckBox mLike;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            mPhoto = (ImageView)itemView.findViewById(R.id.cardview_playerPhoto);
            mName = (TextView)itemView.findViewById(R.id.cardview_playername);
            mPos = (TextView)itemView.findViewById(R.id.cardview_playerposition);
            mData = (TextView)itemView.findViewById(R.id.cardview_playerData);
            mLike = (CheckBox)itemView.findViewById(R.id.cardview_playerLike);

            itemView.setOnClickListener(new View.OnClickListener() {

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });

        }

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }


    }
}
