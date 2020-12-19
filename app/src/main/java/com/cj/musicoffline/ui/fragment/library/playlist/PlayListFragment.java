package com.cj.musicoffline.ui.fragment.library.playlist;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.eventbuss.BackFragment;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.model.PlayList;
import com.cj.musicoffline.ui.fragment.library.dialog.CustomDialog;
import com.cj.musicoffline.viewmodel.ShareViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayListFragment extends Fragment implements View.OnClickListener {
    private TextView mTVNullPlayList;
    private ImageView mIVBack, mIVAddList;
    private AdapterPlayList mAdapter;
    private RecyclerView mRecyclerView;
    private List<PlayList> arrayList = new ArrayList<>();
    //    private List<String> mListCount = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    public static boolean isInsert = false;
    private ShareViewModel mViewModel;
    private int cout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this, App.factory).get(ShareViewModel.class);
        return inflater.inflate(R.layout.fragment_play_list, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
        getPlayList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getPlayList() {
        AtomicBoolean isCount = new AtomicBoolean(false);
        mViewModel.getPlayList().observe(getViewLifecycleOwner(), playListModels -> {
//            arrayList.addAll(playListModels);
            arrayList.clear();
//            mListCount.clear();
            playListModels.forEach(it -> {
                cout = 0;
                isCount.set(true);
                mViewModel.getCountFavourite(it.getId()).observe(getViewLifecycleOwner(), integer -> {
                    arrayList.add(new PlayList(it.getId(), it.getNameList(), integer));
                    isCount.set(false);
                });
                if (isCount.get()) {
                    arrayList.add(new PlayList(it.getId(), it.getNameList(), 0));
                }
//                mAdapter.setData(playListModels, mListCount);
            });
            mAdapter.notifyDataSetChanged();
//            for (PlayListModel it : playListModels) {
//                mViewModel.getCountFavourite(it.getId()).observe(getViewLifecycleOwner(), integer -> {
//                    mListCount.add("" + integer);
//                });
//                mListCount.add("0");
//            }
//            for (String a : mListCount) {
//                Log.d("nnn", "onClickOpen: " + a);
//            }
            if (playListModels.size() == 0) {
//                mAdapter.notifyDataSetChanged();
                mTVNullPlayList.setVisibility(View.VISIBLE);
            } else {
                mTVNullPlayList.setVisibility(View.GONE);
            }
        });
    }

    private void setUp(View view) {
        mTVNullPlayList = view.findViewById(R.id.mTVNullPlayList);
        mTVNullPlayList.setVisibility(View.VISIBLE);
        mIVBack = view.findViewById(R.id.mIVBack);
        mIVAddList = view.findViewById(R.id.mIVAddList);
        mRecyclerView = view.findViewById(R.id.mReCyclerView);
        mIVBack = view.findViewById(R.id.mIVBack);
        mIVAddList = view.findViewById(R.id.mIVAddList);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AdapterPlayList(getActivity(), arrayList, mViewModel, new AdapterPlayList.OnClickItemMusicListener() {
            @Override
            public void onClickOpen(int pos, int id) {

//                Log.d("nnn", "onClickOpen: " + mListCount.size());
//                for (String a : mListCount) {
//                    Log.d("nnn", "onClickOpen: " + a);
//                }
                if (isInsert) {
                    isInsert = false;
                    EventBus.getDefault().post(new BackFragment());
                    mViewModel.insertFavourite(new FavouriteModel(0, "content://media/external/audio/media/31318", id));
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickDelete(int pos, int id) {
                mViewModel.deletePlayList(id);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mIVBack.setOnClickListener(this);
        mIVAddList.setOnClickListener(this);
    }

    private void dialogCreatePlaylist() {
        CustomDialog mDL = new CustomDialog(getActivity(), mViewModel);
        mDL.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDL.setCancelable(false);
        mDL.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mIVAddList:
                dialogCreatePlaylist();
                break;
            case R.id.mIVBack:
                EventBus.getDefault().post(new BackFragment());
                break;
        }
    }
}