package com.cj.musicoffline.ui.playmusic.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.eventbuss.SendInfo;
import com.cj.musicoffline.eventbuss.UpdateVolum;
import com.cj.musicoffline.utils.HandlingMusic;
import com.cj.musicoffline.utils.SessionManager;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class InfoFragment extends Fragment {
    private TextView mTvTitle;
    private RoundedImageView mImgAlbum;
    private ImageView mIVVolume, mIVRepeat;

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
            if (!title.equals("")) {
                updateInfo(new SendInfo(title, image));
            }
        }
        updateUIVolume();
    }

    private void setUp(View view) {
        mTvTitle = view.findViewById(R.id.mTvTitle);
        mImgAlbum = view.findViewById(R.id.mImgAlbum);
        mIVRepeat = view.findViewById(R.id.mIVRepeat);
        mIVVolume = view.findViewById(R.id.mIVVolume);

        mIVRepeat.setOnClickListener(v -> {

        });
        mIVVolume.setOnClickListener(v -> {
            boolean set = false;
            if (SessionManager.getInstance().getKeyUpdateVolume()) {
                set = false;
                SessionManager.getInstance().setKeyUpdateVolume(set);
            } else {
                set = true;
                SessionManager.getInstance().setKeyUpdateVolume(set);
            }
            EventBus.getDefault().post(new UpdateVolum(set));
            updateUIVolume();
        });
    }

    private void updateUIVolume() {
        if (SessionManager.getInstance().getKeyUpdateVolume()) {
            mIVVolume.setImageResource(R.drawable.ic_volume_off);
        } else {
            mIVVolume.setImageResource(R.drawable.ic_volume_up);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateInfo(SendInfo sendInfo) {
        if (sendInfo != null) {
            mTvTitle.setText(sendInfo.getTitle());
            Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(sendInfo.getImage()), getContext()));
            if (bitmap == null) {
                mImgAlbum.setImageResource(R.drawable.bg_musicerror);
            } else {
                mImgAlbum.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
}