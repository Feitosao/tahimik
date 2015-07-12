package com.example.lucasfeitosa.loadimagetest;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

/**
 * Created by Lucas Feitosa on 11/07/2015.
 */
public class AddCardapio extends Fragment
{
    Firebase ref;
    AuthData auth;
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
        TextView tvName = (TextView)getView().findViewById(R.id.userName);

    }
}
