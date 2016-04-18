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
    public void onBindViewHolder(ItemRowHolder holder, int position) {

        holder.itemTitle.setText(list.get(position).getHeaderTitle());

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add click event here", Toast.LENGTH_SHORT).show();

            }
        });

        holder.recycler_view_list.setHasFixedSize(true);

        LinearLayoutManager oriLayout = new LinearLayoutManager(this.mContext);
        oriLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recycler_view_list.setLayoutManager(oriLayout);

        //oriLayout.setOrientation(LinearLayoutManager.HORIZONTAL);
//        playersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        othersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Firebase ref = new Firebase("https://luminous-heat-2520.firebaseio.com");
        Firebase news = ref.child("News");
        SmallNewsFirebaseRecylerAdapter myRecyclerViewAdapter = new SmallNewsFirebaseRecylerAdapter(News.class, R.layout.fragment_news_main,
                SmallNewsFirebaseRecylerAdapter.NewsSmallViewHolder.class, news, mContext);
        holder.recycler_view_list.setAdapter(myRecyclerViewAdapter);

//        playersRecyclerView.setAdapter(myRecyclerViewAdapter);
//        othersRecyclerView.setAdapter(myRecyclerViewAdapter);



//        if (newsData.getSize() == 0) {
//            newsData.setSamllnewsAdapter(myRecyclerViewAdapter);
//            newsData.setContext(getActivity());
//            newsData.initializeDataFromCloud();
//        }

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
