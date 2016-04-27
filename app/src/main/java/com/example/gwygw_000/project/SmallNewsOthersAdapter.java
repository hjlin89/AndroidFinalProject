package com.example.gwygw_000.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

public class SmallNewsOthersAdapter extends FirebaseRecyclerAdapter<News, SmallNewsOthersAdapter.NewsSmallViewHolder> {

    private Context mContext ;
    static OnItemClickListener mItemClickListener;

    public SmallNewsOthersAdapter(Class<News> modelClass, int modelLayout,
                                           Class<NewsSmallViewHolder> holder, Query ref, Context context) {
        super(modelClass,modelLayout,holder,ref);
        this.mContext = context;
    }


    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onOverflowMenuClick(View view, int position);
    }

    @Override
    public NewsSmallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_cardview_small, parent, false);


        NewsSmallViewHolder vh = new NewsSmallViewHolder(v);
        return vh;
    }

    @Override
    protected void populateViewHolder(NewsSmallViewHolder newsSmallViewHolder, News news, int i) {

        //TODO : set the Image by url using piccaso
        Picasso.with(this.mContext).load(news.getUrl()).into(newsSmallViewHolder.vIcon);
        newsSmallViewHolder.vTitle.setText(news.getUpdated());
        newsSmallViewHolder.vDetail.setText(news.getTitle());
    }

    public static class NewsSmallViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDetail;
        public ImageView vMenu;

        public NewsSmallViewHolder(View v){
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.news_img);
            vTitle = (TextView) v.findViewById(R.id.news_title);
            vDetail = (TextView) v.findViewById(R.id.news_details);

            vTitle.setTextSize(10);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });


            if(vMenu!=null){
                vMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mItemClickListener != null)
                            mItemClickListener.onOverflowMenuClick(v,getLayoutPosition());
                    }
                });
            }


        }

    }

}
