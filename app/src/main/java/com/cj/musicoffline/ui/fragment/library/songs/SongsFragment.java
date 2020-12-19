package com.cj.musicoffline.ui.fragment.library.songs;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.eventbuss.PlayAudio;
import com.cj.musicoffline.eventbuss.SendStartAudio;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.service.PlayMusicService;
import com.cj.musicoffline.ui.fragment.library.dialog.ShowBottomSheetDialog;
import com.cj.musicoffline.ui.main.MainActivity;
import com.cj.musicoffline.ui.playmusic.PlayActivity;
import com.cj.musicoffline.utils.CheckService;
import com.cj.musicoffline.utils.Constain;
import com.cj.musicoffline.utils.PlayMusic;
import com.cj.musicoffline.viewmodel.ShareViewModel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SongsFragment extends Fragment {
    private ShareViewModel mViewModel;
    private List<AudioModel> arrayList = new ArrayList<>();
    AdapterAudio adapter;
    RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        mViewModel = new ViewModelProvider(this, App.factory).get(ShareViewModel.class);
        init(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllMusic();
    }

    private void init(View view) {
        mProgressBar = view.findViewById(R.id.mPB);
        mRecyclerView = view.findViewById(R.id.mReCyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterAudio(getActivity(), arrayList, SongsFragment.this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickItemMusicListener(position -> {
            PlayMusic.StartMusic(arrayList, getActivity(), position);
        });
    }

    public void showBottomSheetDialog(AudioModel model, String check) {
        ShowBottomSheetDialog showBottomSheetDialog = new ShowBottomSheetDialog(model, check, mViewModel, 1);
        showBottomSheetDialog.show(getFragmentManager(), Constain.keyBottomSheet);
        Fragment fragment = getFragmentManager().findFragmentByTag(Constain.keyBottomSheet);
        if (fragment != null) {
//            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllMusic() {
        mViewModel.getAllMusic().observe(getViewLifecycleOwner(), audioModels -> {
            arrayList.clear();
            arrayList.addAll(audioModels);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkIsPlay(SendStartAudio sendStartAudio) {

    }

}