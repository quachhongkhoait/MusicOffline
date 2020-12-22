package com.cj.musicoffline.ui.playlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.utils.Constain;
import com.cj.musicoffline.utils.PlayMusic;
import com.cj.musicoffline.viewmodel.ShareViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlayListDetailActivity extends AppCompatActivity {
    private List<AudioModel> arrayList = new ArrayList<>();
    private List<FavouriteModel> mListID = new ArrayList<>();
    AdapterListDetail adapter;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView mIVBack;
    private TextView mTVNameList;
    private ShareViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        mViewModel = new ViewModelProvider(this, App.factory).get(ShareViewModel.class);
        onSetUp();
        getList();
    }

    private void getList() {
        Intent intent = getIntent();
        PlayListModel playListModel = intent.getParcelableExtra(Constain.NAME_LIST);
        mTVNameList.setText(playListModel.getNameList() + "(" + playListModel.getCount() + ")");
        mViewModel.getFavourite(playListModel.getId()).observe(this, favouriteModels -> {
            mListID.clear();
            arrayList.clear();
            mListID.addAll(favouriteModels);
            for (FavouriteModel it : favouriteModels) {
                Log.d("nnn", "getList: " + it.getUrl());
                mViewModel.getAllByID(it.getUrl()).observe(this, audioModels -> {
                    arrayList.addAll(audioModels);
                    Log.d("nnn", "getList: " + audioModels.size());
                    adapter.notifyDataSetChanged();
                });
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void onSetUp() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mIVBack = findViewById(R.id.mIVBack);
        mTVNameList = findViewById(R.id.mTVNameList);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterListDetail(arrayList, this, new AdapterListDetail.OnClickItemDetailListener() {
            @Override
            public void onclickItem(int position) {
                PlayMusic.StartMusic(arrayList,getApplication(),position);
            }

            @Override
            public void onclickDelete(int pos) {
                mViewModel.deleteFavourite(mListID.get(pos).getId());
            }
        });
        mRecyclerView.setAdapter(adapter);
        //on Click
        mIVBack.setOnClickListener(v -> finish());
    }

}