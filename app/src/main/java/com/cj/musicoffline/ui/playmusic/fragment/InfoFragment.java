package com.cj.musicoffline.ui.playmusic.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.eventbuss.SendInfo;
import com.cj.musicoffline.eventbuss.SendUI;
import com.cj.musicoffline.utils.HandlingMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class InfoFragment extends Fragment {
    private TextView mTvTitle;
    private RoundedImageView mImgAlbum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String image = getArguments().getString("image");
            updateInfo(new SendInfo(title, image));
        }
    }

    private void setUp(View view) {
        mTvTitle = view.findViewById(R.id.mTvTitle);
        mImgAlbum = view.findViewById(R.id.mImgAlbum);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateInfo(SendInfo sendInfo) {
        Log.d("nnn", "updateInfo: ");
        mTvTitle.setText(sendInfo.getTitle());
        Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(sendInfo.getImage()), getContext()));
        if (bitmap == null) {
            mImgAlbum.setImageResource(R.drawable.bg_musicerror);
        } else {
            mImgAlbum.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}