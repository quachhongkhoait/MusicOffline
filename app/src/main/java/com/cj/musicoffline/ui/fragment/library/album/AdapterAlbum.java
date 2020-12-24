package com.cj.musicoffline.ui.fragment.library.album;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.utils.HandlingMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterAlbum extends RecyclerView.Adapter<AdapterAlbum.ViewHolder> {
    interface OnClickItemMusicListener {
        void onClickOpen(int pos);
    }

    private Context mContext;
    List<AudioModel> mList;
    private OnClickItemMusicListener onClickItemMusicListener;

    public AdapterAlbum(Context mContext, List<AudioModel> mList, OnClickItemMusicListener onClickItemMusicListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.onClickItemMusicListener = onClickItemMusicListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_playlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTvTitle.setText(mList.get(position).getNameAlbum());
        Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(mList.get(position).getIdAlbum()), mContext));
        if (bitmap == null) {
            holder.mRIVPlayList.setImageResource(R.drawable.bg_musicerror);
        } else {
            holder.mRIVPlayList.setImageBitmap(bitmap);
        }
        holder.mRIVPlayList.setOnClickListener(view -> onClickItemMusicListener.onClickOpen(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        RoundedImageView mRIVPlayList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.txt_nameSong);
            mRIVPlayList = itemView.findViewById(R.id.img_playlist);
        }
    }
}
