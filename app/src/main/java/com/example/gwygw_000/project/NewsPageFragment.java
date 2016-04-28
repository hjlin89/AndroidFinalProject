package com.example.gwygw_000.project;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private HashMap<String, ?> news;

    private OnFragmentInteractionListener mListener;

    public NewsPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsPageFragment newInstance(HashMap<String, ?> news) {
        NewsPageFragment fragment = new NewsPageFragment();
        Bundle args = new Bundle();
        args.putSerializable("NEWS", news);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            news = (HashMap<String,?>) getArguments().getSerializable("NEWS");
        }
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.news_page_fragment, container, false);
        final TextView title = (TextView) view.findViewById(R.id.news_page_title);
        final TextView content = (TextView) view.findViewById(R.id.news_page_content);
        final ImageView photo = (ImageView) view.findViewById(R.id.news_page_photo);
        final TextView time = (TextView) view.findViewById(R.id.news_page_time_authors);

        title.setText(news.get("Title").toString());
        content.setText(news.get("Content").toString());
        String updated = news.get("Updated").toString();
        time.setText(updated);
        photo.setTransitionName("photo");
        Picasso.with(getContext()).load(news.get("Url").toString()).into(photo);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
