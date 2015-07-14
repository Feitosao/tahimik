package com.example.lucasfeitosa.loadimagetest;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

/**
 * Created by Lucas Feitosa on 14/07/2015.
 */
public class Estabelecimento implements Place
{
    private String id;
    public Estabelecimento()
    {

    }
    @Override
    public String getId()
    {
        return null;
    }

    @Override
    public List<Integer> getPlaceTypes()
    {
        return null;
    }

    @Override
    public CharSequence getAddress()
    {
        return null;
    }

    @Override
    public Locale getLocale()
    {
        return null;
    }

    @Override
    public CharSequence getName()
    {
        return null;
    }

    @Override
    public LatLng getLatLng()
    {
        return null;
    }

    @Override
    public LatLngBounds getViewport()
    {
        return null;
    }

    @Override
    public Uri getWebsiteUri()
    {
        return null;
    }

    @Override
    public CharSequence getPhoneNumber()
    {
        return null;
    }

    @Override
    public boolean zzsT()
    {
        return false;
    }

    @Override
    public float getRating()
    {
        return 0;
    }

    @Override
    public int getPriceLevel()
    {
        return 0;
    }

    @Override
    public Place freeze()
    {
        return null;
    }

    @Override
    public boolean isDataValid()
    {
        return false;
    }
}
