package com.example.gwygw_000.project;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by HuijunLin on 4/15/16.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ItemRowHolder> {

    private Context mContext ;
    private ArrayList<NewsSectionData> list;

    public NewsRecyclerAdapter(Context mContext,  ArrayList<NewsSectionData> list) {
        this.mContext = mContext;
        this.list = list;

    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, final int position) {

        holder.itemTitle.setText(list.get(position).getHeaderTitle());

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add click event here", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        // team
                        break;
                    case 1:
                        // player
                        break;
                    case 2:
                        // others
                        break;
                    default:
                        // team
                }
             }
        });

        holder.recycler_view_list.setHasFixedSize(true);

        LinearLayoutManager oriLayout = new LinearLayoutManager(this.mContext);
        oriLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recycler_view_list.setLayoutManager(oriLayout);

        Firebase ref = new Firebase("https://luminous-heat-2520.firebaseio.com");
        Firebase news = ref.child("News");

        FirebaseRecyclerAdapter myRecyclerViewAdapter;
        switch (position) {
            case 0:
                news = news.child("TeamsNews");
                myRecyclerViewAdapter = new SmallNewsFirebaseRecylerAdapter(News.class, R.layout.fragment_news_main,
                        SmallNewsFirebaseRecylerAdapter.NewsSmallViewHolder.class, news, mContext);
                break;
            case 1:
                news = news.child("PlayersNews");
                myRecyclerViewAdapter = new SmallNewsPlayerAdapter(News.class, R.layout.fragment_news_main,
                        SmallNewsPlayerAdapter.NewsSmallViewHolder.class, news, mContext);
                break;
            case 2:
                news = news.child("OthersNews");
                myRecyclerViewAdapter = new SmallNewsOthersAdapter(News.class, R.layout.fragment_news_main,
                        SmallNewsOthersAdapter.NewsSmallViewHolder.class, news, mContext);
                break;
            default:
                news = news.child("TeamsNews");
                myRecyclerViewAdapter = new SmallNewsFirebaseRecylerAdapter(News.class, R.layout.fragment_news_main,
                        SmallNewsFirebaseRecylerAdapter.NewsSmallViewHolder.class, news, mContext);

        }

        holder.recycler_view_list.setAdapter(myRecyclerViewAdapter);

    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;

        protected RecyclerView recycler_view_list;

        protected Button btnMore;



        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.news_recycler_view);
            this.btnMore= (Button) view.findViewById(R.id.btnMore);
        }
    }
}
