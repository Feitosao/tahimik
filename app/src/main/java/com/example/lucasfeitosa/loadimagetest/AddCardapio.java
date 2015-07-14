package com.example.lucasfeitosa.loadimagetest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lucas Feitosa on 11/07/2015.
 */
public class AddCardapio extends Fragment
{
    Activity activity;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_addcardapio, container, false);
        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        User user = ((InterfaceAuxiliar)activity).getUser();
    }
}
