package com.cj.musicoffline.ui.fragment.library.favourite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.eventbuss.BackFragment;
import com.cj.musicoffline.eventbuss.PlayAudio;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.service.PlayMusicService;
import com.cj.musicoffline.ui.fragment.library.dialog.ShowBottomSheetDialog;
import com.cj.musicoffline.ui.fragment.library.songs.AdapterAudio;
import com.cj.musicoffline.ui.fragment.library.songs.SongsFragment;
import com.cj.musicoffline.ui.playmusic.PlayActivity;
import com.cj.musicoffline.utils.CheckService;
import com.cj.musicoffline.utils.Constain;
import com.cj.musicoffline.viewmodel.ShareViewModel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {
    private ShareViewModel mModel;
    private List<AudioModel> arrayList = new ArrayList<>();
    private List<FavouriteModel> mListID = new ArrayList<>();
    AdapterFavourite adapter;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ShowBottomSheetDialog showBottomSheetDialog;
    private TextView mTVNullSongs;
    private ImageView mIVBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mModel = new ViewModelProvider(this, App.factory).get(ShareViewModel.class);
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
        getAllFavourite();
    }

    private void setUp(View view) {
        mRecyclerView = view.findViewById(R.id.mReCyclerView);
        mIVBack = view.findViewById(R.id.mIVBack);
        mTVNullSongs = view.findViewById(R.id.mTVNullSongs);
        mTVNullSongs.setVisibility(View.GONE);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterFavourite(getActivity(), arrayList, mListID, FavouriteFragment.this, position -> {
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            //start activity
            Intent mIntent = new Intent(getActivity(), PlayActivity.class);
            mIntent.putExtra("postion", position);
            mIntent.putExtra("list", json);
            startActivity(mIntent);
            //start service
            if (!CheckService.isMyServiceRunning(getActivity(), PlayMusicService.class)) {
                Intent intent = new Intent(getActivity(), PlayMusicService.class);
                intent.putExtra("list", json);
                intent.putExtra("postion", position);
                ContextCompat.startForegroundService(getActivity(), intent);
            } else {
                EventBus.getDefault().post(new PlayAudio(position));
            }
        });
        mRecyclerView.setAdapter(adapter);
        //bottomsheet
        showBottomSheetDialog = new ShowBottomSheetDialog();

        mIVBack.setOnClickListener(v -> {
            EventBus.getDefault().post(new BackFragment());
        });
    }

    private void getAllFavourite() {
        mModel.getFavourite(1).observe(this, favouriteModels -> {
            mListID.clear();
            arrayList.clear();
            mListID.addAll(favouriteModels);
            if (favouriteModels.size() == 0) {
                adapter.notifyDataSetChanged();
                mTVNullSongs.setVisibility(View.VISIBLE);
            } else {
                mTVNullSongs.setVisibility(View.GONE);
            }
            for (FavouriteModel it : favouriteModels) {
                mModel.getAllByID(it.getUrl()).observe(this, audioModels -> {
                    arrayList.addAll(audioModels);
                    adapter.notifyDataSetChanged();
                });
            }
        });
    }

    public void showBottomSheetDialog(AudioModel model, String check, int id) {
        showBottomSheetDialog.show(getFragmentManager(), Constain.keyBottomSheet);
        Fragment fragment = getFragmentManager().findFragmentByTag(Constain.keyBottomSheet);
        if (fragment != null) {
//            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        showBottomSheetDialog.addInFavourite(model, check, mModel, 1, id);
    }
}