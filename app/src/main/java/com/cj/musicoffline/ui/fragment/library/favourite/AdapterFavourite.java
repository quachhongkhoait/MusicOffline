package com.cj.musicoffline.ui.fragment.library.favourite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.utils.HandlingMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterFavourite extends RecyclerView.Adapter<AdapterFavourite.ViewHolder> {

    interface OnClickItemMusicListener {
        void onclickItem(int position);
    }

    Context mContext;
    List<AudioModel> mLvAudioModel;
    List<FavouriteModel> mID;
    private FavouriteFragment mFragment;
    private OnClickItemMusicListener onClickItemMusicListener;

    public AdapterFavourite(Context mContext, List<AudioModel> mLvAudioModel, List<FavouriteModel> mID, FavouriteFragment mFragment, OnClickItemMusicListener onClickItemMusicListener) {
        this.mContext = mContext;
        this.mLvAudioModel = mLvAudioModel;
        this.mID = mID;
        this.mFragment = mFragment;
        this.onClickItemMusicListener = onClickItemMusicListener;
    }

    @NonNull
    @Override
    public AdapterFavourite.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_music, parent, false);
        AdapterFavourite.ViewHolder viewHolder = new AdapterFavourite.ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull AdapterFavourite.ViewHolder holder, final int position) {
        holder.mTvTitle.setText(mLvAudioModel.get(position).getTitle());
        holder.mTvDuration.setText(HandlingMusic.createTimerLabel(Integer.parseInt(mLvAudioModel.get(position).getDuration())));
        Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(mLvAudioModel.get(position).getIdAlbum()), mContext));
        if (bitmap == null) {
            holder.mImgAlbum.setImageResource(R.drawable.bg_musicerror);
        } else {
            holder.mImgAlbum.setImageBitmap(bitmap);
        }
        holder.mCardView.setOnClickListener(view -> onClickItemMusicListener.onclickItem(position));
        holder.mRelaOption.setOnClickListener(v -> {
            if (mLvAudioModel.size() == 1) {
                notifyDataSetChanged();
            }
            mFragment.showBottomSheetDialog(mLvAudioModel.get(position), "remove", mID.get(position).getId());
        });

    }

    @Override
    public int getItemCount() {
        return mLvAudioModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle, mTvDuration;
        RoundedImageView mImgAlbum;
        CardView mCardView;
        RelativeLayout mRelaOption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvDuration = itemView.findViewById(R.id.tvDuration);
            mImgAlbum = itemView.findViewById(R.id.imageAlbum);
            mCardView = itemView.findViewById(R.id.cardview);
            mRelaOption = itemView.findViewById(R.id.relaOption);
        }
    }

    public void setOnClickItemMusicListener(AdapterFavourite.OnClickItemMusicListener onClickItemMusicListener) {
        this.onClickItemMusicListener = onClickItemMusicListener;
    }
}