package com.example.gwygw_000.project;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.firebase.client.Firebase;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentNewsMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentNewsMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNewsMain extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<NewsSectionData> newsSectionDatalist;
    private NewsData teamsNewsData;
    private NewsData PlayersNewsData;
    private NewsData othersNewsData;


    public FragmentNewsMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentNewsMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNewsMain newInstance() {
        FragmentNewsMain fragment = new FragmentNewsMain();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());

        PlayersNewsData = new NewsData("PlayersNews");
        othersNewsData = new NewsData("OthersNews");

        newsSectionDatalist = new ArrayList<>();

        setRetainInstance(true);
        setHasOptionsMenu(true);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_news_main, container, false);
        newsSectionDatalist.clear();

        final BoomMenuButton boomMenuButton = (BoomMenuButton) getActivity().findViewById(R.id.boom);
        boomMenuButton.setVisibility(BoomMenuButton.GONE);

        final NewsSectionData teams = new NewsSectionData("Teams", "Teams");
        NewsSectionData players = new NewsSectionData("Players", "Players");
        NewsSectionData others = new NewsSectionData("Others", "Others");

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("News");
        toolbar.inflateMenu(R.menu.drawer);
        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        newsSectionDatalist.add(teams);
        newsSectionDatalist.add(players);
        newsSectionDatalist.add(others);

        RecyclerView newsRecyclerView = (RecyclerView) view.findViewById(R.id.newslist_recycler_view);
        newsRecyclerView.setHasFixedSize(true);
        NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(getContext(), newsSectionDatalist, mListener);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        newsRecyclerView.setAdapter(adapter);

        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    boomMenuButton.setVisibility(boomMenuButton.VISIBLE);
                } else {
                    boomMenuButton.setVisibility(boomMenuButton.GONE);

                }
            }
        } );

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListItemSelected(int position, HashMap<String, String> news, ImageView imageView);
    }
}
