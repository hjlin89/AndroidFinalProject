package com.example.gwygw_000.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.HashMap;


import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            FragmentNewsMain.OnFragmentInteractionListener,
            NewsPageFragment.OnFragmentInteractionListener,
            PlayerListFragment.OnFragmentInteractionListener,
            VideoListFragment.VideoListLoadFragment{

    Fragment mContent;

    private Toolbar toolbar;
    private NavigationView navigetionView;
    private DrawerLayout drawerLayout;
    private CircleImageView cImage;
    private TextView tv1;
    private TextView tv2;
    private BoomMenuButton boomMenuButton;
    private boolean init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boomMenuButton = (BoomMenuButton)findViewById(R.id.boom);

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

        //drawerLayout.openDrawer(Gravity.LEFT);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layout_main, FragmentNewsMain.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
//        } else {
//            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
//        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Use a param to record whether the boom button has been initialized
        // Because we don't need to init it again when onResume()
        if (init) return;
        init = true;

        int[] colors = new int[5];
        colors[0] = R.color.blue;
        colors[1] = R.color.green;
        colors[2] = R.color.purple;
        colors[3] = R.color.lime;
        colors[4] = R.color.green_teal;

        int[][] subButtonColors = new int[5][2];
        for (int i = 0; i < 5; i++) {
            subButtonColors[i][1] = ContextCompat.getColor(this, colors[i]);
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
        }

        // Now with Builder, you can init BMB more convenient
        new BoomMenuButton.Builder()
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.icon_news), subButtonColors[0], "")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.icon_palyer), subButtonColors[1], "")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.icon_team), subButtonColors[2], "")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.icon_schedule), subButtonColors[3], "")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.icon_video), subButtonColors[4], "")
                .button(ButtonType.CIRCLE)
                .boom(BoomType.LINE)
                .place(PlaceType.CIRCLE_5_1)
                .subButtonTextColor(ContextCompat.getColor(this, R.color.darkness))
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .init(boomMenuButton);

        boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                switch (buttonIndex) {
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout_main, FragmentNewsMain.newInstance())
                                .commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout_main, TeamPlayerListFragment.newInstance())
                                .commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout_main, TeamListFragment.newInstance())
                                .commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout_main, ScheduleListFragment.newInstance())
                                .commit();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout_main, VideoListFragment.newInstance())
                                .commit();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout_main, FragmentNewsMain.newInstance())
                                .commit();
                        break;
                }
            }
        });


//        boomMenuButton.init(
//                subButtonDrawables, // The drawables of images of sub buttons. Can not be null.
//                subButtonTexts,     // The texts of sub buttons, ok to be null.
//                subButtonColors,    // The colors of sub buttons, including pressed-state and normal-state.
//                ButtonType.HAM,     // The button type.
//                BoomType.PARABOLA,  // The boom type.
//                PlaceType.HAM_3_1,  // The place type.
//                null,               // Ease type to move the sub buttons when showing.
//                null,               // Ease type to scale the sub buttons when showing.
//                null,               // Ease type to rotate the sub buttons when showing.
//                null,               // Ease type to move the sub buttons when dismissing.
//                null,               // Ease type to scale the sub buttons when dismissing.
//                null,               // Ease type to rotate the sub buttons when dismissing.
//                null                // Rotation degree.
//        );
//
//        boomMenuButton.setTextViewColor(ContextCompat.getColor(this, R.color.black));
//        boomMenuButton.setSubButtonShadowOffset(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_menu_map:
                Intent intent = new Intent(this, GoogleMapActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    public void onListItemSelected(int position, HashMap<String, String> news, ImageView shareImage) {
        NewsPageFragment newsFragment = NewsPageFragment.newInstance(news);
        newsFragment.setSharedElementEnterTransition(new DetailTransition());
        newsFragment.setEnterTransition(new Fade());
        newsFragment.setExitTransition(new Fade());
        newsFragment.setSharedElementReturnTransition(new DetailTransition());

        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(shareImage, "photo")
                .addToBackStack(null)
                .replace(R.id.layout_main, newsFragment)
                .commit();
    }

    // news page
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // player list
    @Override
    public void onListItemSelected(int position, HashMap<String, String> player) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.layout_main, PlayerPageFragment.newInstance(player))
                .commit();
    }


    @Override
    public void loadVideoFragment(String key) {
        Fragment fragment = YouTubeFragment.newInstance(key);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.layout_main, fragment)
                .commit();
    }
}
