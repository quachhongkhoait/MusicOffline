package com.cj.musicoffline.ui.fragment.library;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cj.musicoffline.R;
import com.cj.musicoffline.itf.OnCurrentFragmentListener;
import com.cj.musicoffline.eventbuss.ChangeFragment;
import com.cj.musicoffline.ui.fragment.library.album.AlbumFragment;
import com.cj.musicoffline.ui.fragment.library.dialog.ShowBottomSheetDialog;
import com.cj.musicoffline.ui.fragment.library.favourite.FavouriteFragment;
import com.cj.musicoffline.ui.fragment.library.playlist.PlayListFragment;
import com.cj.musicoffline.ui.fragment.library.songs.SongsFragment;
import com.cj.musicoffline.utils.Constain;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;

public class LibraryFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mSongs;
    private RelativeLayout mAlbum;
    private RelativeLayout mPlaylist;
    private RelativeLayout mFavourite;
    private OnCurrentFragmentListener mListener;
    private ImageView mDialogOption;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnCurrentFragmentListener) context;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view);
        mSongs.setOnClickListener(this);
        mAlbum.setOnClickListener(this);
        mPlaylist.setOnClickListener(this);
        mFavourite.setOnClickListener(this);
    }


    private void setup(View view) {
        mSongs = view.findViewById(R.id.mSongs);
        mAlbum = view.findViewById(R.id.mAlbum);
        mPlaylist = view.findViewById(R.id.mPlaylist);
        mFavourite = view.findViewById(R.id.mFavourite);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mSongs:
                EventBus.getDefault().post(new ChangeFragment(new SongsFragment()));
                break;
            case R.id.mAlbum:
                EventBus.getDefault().post(new ChangeFragment(new AlbumFragment()));
                break;
            case R.id.mPlaylist:
                EventBus.getDefault().post(new ChangeFragment(new PlayListFragment()));
                break;
            case R.id.mFavourite:
                EventBus.getDefault().post(new ChangeFragment(new FavouriteFragment()));
                break;
        }
    }

}