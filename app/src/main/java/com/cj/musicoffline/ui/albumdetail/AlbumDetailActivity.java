package com.cj.musicoffline.ui.albumdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.ui.fragment.library.album.AlbumViewModel;
import com.cj.musicoffline.utils.Constain;
import com.cj.musicoffline.utils.PlayMusic;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailActivity extends AppCompatActivity {
    private List<AudioModel> arrayList = new ArrayList<>();
    AdapterAlbumDetail adapter;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView mIVBack;
    private TextView mTVNameList;
    private AlbumViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        mViewModel = new ViewModelProvider(this, App.factory).get(AlbumViewModel.class);
        onSetUp();
        getList();
    }

    private void getList() {
        Intent intent = getIntent();
        int idAlbum = Integer.parseInt(intent.getStringExtra(Constain.ID_ALBUM));
        String nameAlbum = intent.getStringExtra(Constain.NAME_ALBUM);
        mTVNameList.setText(nameAlbum);
        mViewModel.getAlbumDetail(idAlbum).observe(this, list -> {
            arrayList.clear();
            arrayList.addAll(list);
            adapter.notifyDataSetChanged();
        });
    }

    private void onSetUp() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mIVBack = findViewById(R.id.mIVBack);
        mTVNameList = findViewById(R.id.mTVNameList);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterAlbumDetail(arrayList, this, position -> {
            PlayMusic.StartMusic(arrayList, this, position);
        });
        mRecyclerView.setAdapter(adapter);
        //on Click
        mIVBack.setOnClickListener(v -> finish());

    }
}