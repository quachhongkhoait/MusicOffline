package com.cj.musicoffline.ui.fragment.library.album;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.eventbuss.BackFragment;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.ui.albumdetail.AlbumDetailActivity;
import com.cj.musicoffline.ui.fragment.home.AdapterNew;
import com.cj.musicoffline.ui.fragment.home.HomeViewModel;
import com.cj.musicoffline.ui.playlist.PlayListDetailActivity;
import com.cj.musicoffline.utils.Constain;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    private AlbumViewModel mViewModel;
    AdapterAlbum adapter;
    RecyclerView mRecyclerView;
    private List<AudioModel> arrayList = new ArrayList<>();
    private ImageView mIVBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this, App.factory).get(AlbumViewModel.class);
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view);
        getAlbum();
    }

    private void getAlbum() {
        mViewModel.getAllMusic().observe(getViewLifecycleOwner(), list -> {
            arrayList.clear();
            arrayList.addAll(list);
            adapter.notifyDataSetChanged();
        });
    }

    private void setup(View view) {
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        mIVBack = view.findViewById(R.id.mIVBack);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterAlbum(getContext(), arrayList, position -> {
            Intent intent = new Intent(getActivity(), AlbumDetailActivity.class);
            intent.putExtra(Constain.ID_ALBUM, arrayList.get(position).getIdAlbum());
            intent.putExtra(Constain.NAME_ALBUM, arrayList.get(position).getNameAlbum());
            startActivity(intent);
        });
        mRecyclerView.setAdapter(adapter);

        mIVBack.setOnClickListener(v -> {
            EventBus.getDefault().post(new BackFragment());
        });
    }
}