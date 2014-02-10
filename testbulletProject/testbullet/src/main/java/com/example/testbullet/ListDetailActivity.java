package com.example.testbullet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Lanetteam on 2/8/14.
 */
public class ListDetailActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_detail_activity,container,false);
        return view;
    }

    public void setName(String name)
    {
        TextView view = (TextView) getView().findViewById(R.id.tvName);
        view.setText(name);
    }

    public void setAddress(String name)
    {
        TextView view = (TextView) getView().findViewById(R.id.tvAddress);
        view.setText(name);
    }

    public void setGender(String name)
    {
        TextView view = (TextView) getView().findViewById(R.id.tvGender);
        view.setText(name);
    }
}