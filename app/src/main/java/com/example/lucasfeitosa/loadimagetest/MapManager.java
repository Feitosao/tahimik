package com.example.lucasfeitosa.loadimagetest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Lucas Feitosa on 13/07/2015.
 */
public class MapManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    Activity activity;
    //Localizacao de um ponto qqr em florianopolis
    //Para mostrar florianopolis no mapa, quando o GPS nao estiver abilitado
    public static LatLng latLngFloripa = new LatLng(-27.603007,-48.521171);
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    // Google client to interact with Google API
    public GoogleApiClient mGoogleApiClient;
    // Google Map
    private GoogleMap googleMap;
    private MapFragment mapFragment;
    //location
    private Location mLastLocation = null;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private boolean mConnected = false;
    public MapManager(Activity activity)
    {
        this.activity = activity;
    }
    public synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    public void showMap(FragmentManager fm, int mapFragmentCode)
    {
        //FragmentManager fm = getChildFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(mapFragmentCode);
        if (mapFragment == null)
        {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().replace(mapFragmentCode, mapFragment).commit();
        }
    }
    public void initilizeMap()
    {
        if (googleMap == null)
        {
            googleMap = mapFragment.getMap();
            //Toast.makeText(this.getActivity().getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            //getActivity().finish();
        }
        if(googleMap != null)
        {
            googleMap.setMyLocationEnabled(true);
            LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                buildAlertMessageNoGps();
            }
        }
    }
    private void buildAlertMessageNoGps ()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id)
                    {
                        activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Toast.makeText(activity, "This device is not supported.", Toast.LENGTH_LONG).show();
                //finish();
            }
            return false;
        }
        return true;
    }
    public void setMapUserLocation()
    {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }
    public Location getMapUserLocation()
    {
        return mLastLocation;
    }
    public void setMapCameraToUserLocation()
    {
        setMapUserLocation();
        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng latLng;
        float zoom;
        if (mLastLocation != null)
        {
            latLng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            zoom = 16.0f;
        }
        else
        {
            latLng = latLngFloripa;
            zoom = 10f;
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }
    @Override
    public void onConnectionFailed(ConnectionResult arg0)
    {
        mConnected = false;
        Log.d(activity.toString(), "conexao com o server localizador falhou");
    }
    @Override
    public void onConnectionSuspended(int i)
    {
        Log.d(activity.toString(), "Location services suspended. Please reconnect.");
        mGoogleApiClient.connect();

    }
    @Override
    public void onConnected(Bundle connectionHint)
    {
        mConnected = true;
        setMapCameraToUserLocation();
    }

}
