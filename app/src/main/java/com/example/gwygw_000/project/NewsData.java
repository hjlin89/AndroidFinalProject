package com.example.gwygw_000.project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsData {

    List<Map<String,?>> newsList;
    Firebase mRef;
    SmallNewsFirebaseRecylerAdapter smallNewsFirebaseRecylerAdapter;
    Context mContext;

    public List<Map<String, ?>> getNewsList() {
        return newsList;
    }

    public int getSize(){
        return newsList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < newsList.size()){
            return (HashMap) newsList.get(i);
        } else return null;
    }

	public void addItem(int position, HashMap<String,?> hashMap){
        newsList.add(position, hashMap);
	}

	public void removeItem(int position){
        newsList.remove(position);
	}


    public void setSamllnewsAdapter(SmallNewsFirebaseRecylerAdapter adapter) {
        this.smallNewsFirebaseRecylerAdapter = adapter;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Firebase getFirebaseRef() {
        return mRef;
    }

    public NewsData(String newstype){
        newsList = new ArrayList<Map<String,?>>();
        mRef = new Firebase("https://luminous-heat-2520.firebaseio.com");
        initializeDataFromCloud(newstype);
    }


	public void initializeDataFromCloud(String name) {
        newsList.clear();

        mRef.child("News").child(name).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Log.d("MyTest: onChildAdded", dataSnapshot.toString());
                HashMap<String, ?> news = (HashMap<String, ?>) dataSnapshot.getValue();

//                for (String key : news.keySet()) {
//                    Log.d("Key: ", key);
//                    Log.d("Test : ", news.get(key).getClass().toString());
//                }

                onItemAddedToCloud(news);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: onChildChanged", dataSnapshot.toString());
                HashMap<String, String> movie = (HashMap<String, String>) dataSnapshot.getValue();
                onItemUpdatedInCloud(movie);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("MyTest: onChildRemoved", dataSnapshot.toString());
                HashMap<String, String> movie = (HashMap<String, String>) dataSnapshot.getValue();
                onItemRemovedFromCloud(movie);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: onChildMoved", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

	}

    private void onItemRemovedFromCloud(HashMap<String, String> item) {
        int position = -1;

        String id = (String) item.get("id");
        for (int i=0; i<newsList.size(); i++) {
            HashMap movie = (HashMap) newsList.get(i);
            String mid = (String) movie.get("id");
            if (mid.equals(id)) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            newsList.remove(position);
            Log.d("MyTest: NotifyRemoved", id);
            Toast.makeText(mContext, "Item Removed" + id, Toast.LENGTH_SHORT);
            if (smallNewsFirebaseRecylerAdapter != null) {
                //myFirebaseRecylerAdapter.notifyItemRemoved(position);
            }
        }
    }

    private void onItemUpdatedInCloud(HashMap<String, String> item) {
        String id = (String) item.get("id");
        for (int i=0; i<newsList.size(); i++) {
            HashMap movie = (HashMap) newsList.get(i);
            String mid = (String) movie.get("id");
            if (mid.equals(id)) {
                newsList.remove(i);
                newsList.add(i, item);
                Log.d("MyTest: NotifyChanged", id);
                if (smallNewsFirebaseRecylerAdapter != null) {
                    //myFirebaseRecylerAdapter.notifyItemChanged(i);
                }
                break;
            }
        }
    }

    private void onItemAddedToCloud(HashMap<String, ?> item) {
        Long id = (Long) item.get("NewsID");
        int insertPostion = 0;
        for (int i=0; i<newsList.size(); i++) {
            HashMap news = (HashMap) newsList.get(i);
            Long mid = (Long) news.get("NewsID");
            if (mid == id) {
                return;
            }

            if (mid != id) {
                insertPostion = i+1;
            } else {
                break;
            }
        }

        newsList.add(insertPostion, item);
        //Log.d("My Test: notifyInsert", "" + id);

        if (smallNewsFirebaseRecylerAdapter != null) {
            //smallNewsFirebaseRecylerAdapter.notifyItemInserted(insertPostion);
        }
    }
}
