package com.cj.musicoffline.ui.fragment.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.utils.HandlingMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {

    interface OnClickSearch {
        void onClickOpen(List<AudioModel> list, int pos);

        void onClickDelete(int pos, String url);
    }

    Context mContext;
    List<AudioModel> mLvAudioModel;
    private AdapterSearch.OnClickSearch onClickItemMusicListener;

    public AdapterSearch(Context mContext, List<AudioModel> mLvAudioModel, OnClickSearch onClickItemMusicListener) {
        this.mContext = mContext;
        this.mLvAudioModel = mLvAudioModel;
        this.onClickItemMusicListener = onClickItemMusicListener;
    }

    @NonNull
    @Override
    public AdapterSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_music, parent, false);
        AdapterSearch.ViewHolder viewHolder = new AdapterSearch.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.ViewHolder holder, final int position) {
        AudioModel model = mLvAudioModel.get(position);
        holder.mTvTitle.setText(model.getTitle());
        holder.mTvDuration.setText(HandlingMusic.createTimerLabel(Integer.parseInt(model.getDuration())));
        Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(model.getIdAlbum()), mContext));
        if (bitmap == null) {
            holder.mImgAlbum.setImageResource(R.drawable.bg_musicerror);
        } else {
            holder.mImgAlbum.setImageBitmap(bitmap);
        }
        holder.mCardView.setOnClickListener(view -> onClickItemMusicListener.onClickOpen(mLvAudioModel, position));
        holder.mRelaOption.setOnClickListener(view -> onClickItemMusicListener.onClickDelete(position, model.getUrl()));
    }

    public void sort() {
        Collections.sort(mLvAudioModel, new Comparator<AudioModel>() {
            @Override
            public int compare(AudioModel o1, AudioModel o2) {
                return 0;
            }
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
        ImageView mIVDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvDuration = itemView.findViewById(R.id.tvDuration);
            mImgAlbum = itemView.findViewById(R.id.imageAlbum);
            mCardView = itemView.findViewById(R.id.cardview);
            mRelaOption = itemView.findViewById(R.id.relaOption);
            mIVDelete = itemView.findViewById(R.id.mIVDelete);
            mIVDelete.setImageResource(R.drawable.ic_close_while);
        }
    }
}