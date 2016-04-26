package com.example.gwygw_000.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private NavigationView navigetionView;
    private DrawerLayout drawerLayout;
    private CircleImageView cImage;
    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_in_main);
        setSupportActionBar(toolbar);

        navigetionView = (NavigationView) findViewById(R.id.navigation_view);
        navigetionView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        final String userEmail;
        if (extras != null) {
            View view = navigetionView.getHeaderView(0);
            userEmail = extras.getString("Email");
            cImage = (CircleImageView) view.findViewById(R.id.profile_image);
            tv1 = (TextView) view.findViewById(R.id.header_tv1);
            tv2 = (TextView) view.findViewById(R.id.header_tv2);
            Firebase ref = new Firebase(FirebaseLib.ROOT);
            ref = ref.child("UserData");

            ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    HashMap<String, ?> map = (HashMap) dataSnapshot.getValue();
                    if (map != null) {
                        String Email = (String) map.get("Email");
                        if (Email.equals(userEmail)) {
                            tv1.setText(dataSnapshot.getKey().toString());
                            tv2.setText(map.get("description").toString());
                            if (map.get("headimage") != null) {
                                // reverse string bitmap to image
                                String imageFile = map.get("headimage").toString();
                                byte[] decodedString = Base64.decode(imageFile, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                Log.d("TestInput", decodedByte.toString());

                                cImage.setImageBitmap(decodedByte);
                            }
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
                    @Override
                    public void onDrawerClosed(View drawerView){
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView){
                        super.onDrawerOpened(drawerView);
                    }
                };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();




        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_main, TeamPlayerListFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
