package com.example.lucasfeitosa.loadimagetest;

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
public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    public HomeFragment(){}

    private static String TAG;
    //Latitude de um ponto qqr em florianopolis
    //Para mostrar florianopolis no mapa, quando o GPS nao estiver abilitado
    double latitudeFloripa = -27.603007;
    double longitudeFloripa = -48.521171;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    // Google Map
    private GoogleMap googleMap;
    //
    private MapFragment mapFragment;
    //location
    private Location mLastLocation = null;

    boolean mConnected = false;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        buildGoogleApiClient();
        TAG = getActivity().toString();
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null)
        {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mapFragment).commit();
        }
    }
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap()
    {
        //if (googleMap == null)
        //{
            //googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // check if map is created successfully or not
            if (googleMap == null)
            {
                googleMap = mapFragment.getMap();
                //Toast.makeText(this.getActivity().getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
                //getActivity().finish();
            }
            //else
            if(googleMap != null)
            {
                googleMap.setMyLocationEnabled(true);
                LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    buildAlertMessageNoGps();
                }
            }
        }
    //}
    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    private void buildAlertMessageNoGps ()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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
    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Toast.makeText(getActivity(),"This device is not supported.", Toast.LENGTH_LONG).show();
                //finish();
            }
            return false;
        }
        return true;
    }
    private void setMapCameraToUserLocation() {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng latLng;
        float zoom;
        if (mLastLocation != null)
        {
            latLng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            zoom = 16.0f;
        }
        else
        {
            latLng = new LatLng(latitudeFloripa,longitudeFloripa);
            zoom = 10f;
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

    }
    @Override
    public void onStart()
    {
        super.onStart();
        if (mGoogleApiClient != null)
        {
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult arg0)
    {
        mConnected = false;
        Log.d(TAG,"conexao com o server localizador falhou");
    }
    @Override
    public void onConnectionSuspended(int i)
    {
        Log.d(TAG, "Location services suspended. Please reconnect.");
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle connectionHint)
    {
        setMapCameraToUserLocation();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        checkPlayServices();
        initilizeMap();
    }
}
