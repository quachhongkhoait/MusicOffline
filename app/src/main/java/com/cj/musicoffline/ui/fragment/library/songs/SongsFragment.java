package com.cj.musicoffline.ui.fragment.library.songs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
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
import com.cj.musicoffline.ui.playmusic.PlayActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SongsFragment extends Fragment {
    private SongsViewModel mViewModel;
    public static List<AudioModel> arrayList = new ArrayList<>();
    AdapterAudio adapter;
    RecyclerView mRecyclerView;
    Uri urltest = null;
    String urlimage = null;
    private ProgressBar mProgressBar;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
//        ViewModelProvider.AndroidViewModelFactory.getInstance(
//                getActivity().getApplication()).create(SongsViewModel.class);
        mViewModel = new ViewModelProvider(this, App.factory).get(SongsViewModel.class);
        init(view);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllMusic();
//        checkPermission();
    }

    private void init(View view) {
        mProgressBar = view.findViewById(R.id.mPB);
        mRecyclerView = view.findViewById(R.id.mReCyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterAudio(getActivity(), arrayList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickItemMusicListener(position -> {
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            //start activity
            Intent mIntent = new Intent(getActivity(), PlayActivity.class);
            mIntent.putExtra("postion", position);
            mIntent.putExtra("list", json);
            startActivity(mIntent);
            //start service
            if (!isMyServiceRunning(PlayMusicService.class)) {
                Intent intent = new Intent(getActivity(), PlayMusicService.class);
                intent.putExtra("list", json);
                intent.putExtra("postion", position);
                ContextCompat.startForegroundService(getActivity(), intent);
            } else {
                EventBus.getDefault().post(new PlayAudio(position));
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getAllMusic() {
        mViewModel.getAllMusic().observe(getViewLifecycleOwner(), new Observer<List<AudioModel>>() {
            @Override
            public void onChanged(List<AudioModel> audioModels) {
                for (AudioModel audioModel : audioModels) {
                    arrayList.add(audioModel);
                }
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
        });
    }

//    private void checkPermission() {
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            } else {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            }
//        } else {
//            getMusic();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                            == PackageManager.PERMISSION_GRANTED) {
//                        getMusic();
//                    }
//                } else {
//                    getActivity().finish();
//                }
//                return;
//            }
//        }
//    }

//    private void getMusic() {
//        arrayList.clear();
//        ContentResolver contentResolver = getActivity().getContentResolver();
//        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
//        if (songCursor != null && songCursor.moveToFirst()) {
//            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
//            int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
//            int songID = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
//            do {
//                String currentTitle = songCursor.getString(songTitle);
//                String currentDuration = songCursor.getString(songDuration);
////                String currentDuration = HandlingMusic.convertDuration(songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
//                Long ur = songCursor.getLong(songID);
//                Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ur);
//                String albumId = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
//
//                insertRoom(new AudioModel(currentTitle, currentDuration, String.valueOf(trackUri), albumId));
//                arrayList.add(new AudioModel(currentTitle, currentDuration, String.valueOf(trackUri), albumId));
//                urltest = trackUri;
//                urlimage = albumId;
//            } while (songCursor.moveToNext());
//            adapter.notifyDataSetChanged();
//            mProgressBar.setVisibility(View.GONE);
//            mRecyclerView.setVisibility(View.VISIBLE);
//        }
//    }

//    private void insertRoom(AudioModel audioModel) {
//        MusicRepository mReponsitory = new MusicRepository(getActivity().getApplication());
//        mReponsitory.insert(audioModel);
//    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkIsPlay(SendStartAudio sendStartAudio) {

    }

}