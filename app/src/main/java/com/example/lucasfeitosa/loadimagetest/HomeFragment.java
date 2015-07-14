package com.example.lucasfeitosa.loadimagetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Lucas Feitosa on 08/05/2015.
 */
public class HomeFragment extends Fragment
{
    Activity activity;
    private static String TAG;
    MapManager mapManager;
    public HomeFragment(){}

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mapManager = new MapManager(activity);
        mapManager.buildGoogleApiClient();
        TAG = getActivity().toString();
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapManager.showMap(fm,R.id.mapHome);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if (mapManager.mGoogleApiClient != null)
        {
            mapManager.mGoogleApiClient.connect();
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        mapManager.checkPlayServices();
        mapManager.initilizeMap();
    }
}
