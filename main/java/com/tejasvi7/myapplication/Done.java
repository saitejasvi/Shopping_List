package com.tejasvi7.myapplication;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;




public class Done extends Fragment {

    LoginDataBaseAdapter dbAdapter;
    List<Item> dbList;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    @TargetApi(23)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_done, container, false);


        dbAdapter = LoginDataBaseAdapter.getInstance(getContext());
        dbList= new ArrayList<Item>();
        dbList = dbAdapter.getUserDoneItems(User.getInstance());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleview);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DoneItemsAdapter(getContext(), dbList);

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }



}