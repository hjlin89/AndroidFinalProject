package com.example.gwygw_000.project;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/26.
 */
public class GoogleMapActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    static final LatLng SYR = new LatLng(43.0481, 76.1474);
    private GoogleMap mMap;
    private SupportMapFragment map;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        if (mMap == null)
            map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maplayout_map);
        map.getMapAsync(this);
        toolbar = (Toolbar) findViewById(R.id.maplayout_toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(SYR).title("Marker in Syracuse"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYR,15));
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu.findItem(R.id.mapsearch) == null) {
            getMenuInflater().inflate(R.menu.maptoolbar_menu, menu);
        }
        SearchView search = (SearchView)menu.findItem(R.id.mapsearch).getActionView();
        if (search != null) {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    List<Address> addressList = null;
                    if (query != null && !query.equals("")) {
                        Geocoder geocoder = new Geocoder(GoogleMapActivity.this);
                        try {
                            addressList = geocoder.getFromLocationName(query, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.mapHyBrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.mapRoadMap:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.mapTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
