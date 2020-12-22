package com.cj.musicoffline.ui.playlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.ui.fragment.library.favourite.AdapterFavourite;
import com.cj.musicoffline.utils.HandlingMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class AdapterListDetail extends RecyclerView.Adapter<AdapterListDetail.ViewHolder> {
    private List<AudioModel> mLvAudioModel;
    private Context context;
    private OnClickItemDetailListener onClickItemDetailListener;

    interface OnClickItemDetailListener {
        void onclickItem(int position);

        void onclickDelete(int pos);
    }

    public AdapterListDetail(List<AudioModel> mLvAudioModel, Context context, OnClickItemDetailListener onClickItemDetailListener) {
        this.mLvAudioModel = mLvAudioModel;
        this.context = context;
        this.onClickItemDetailListener = onClickItemDetailListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_music, parent, false);
        AdapterListDetail.ViewHolder viewHolder = new AdapterListDetail.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTvTitle.setText(mLvAudioModel.get(position).getTitle());
        holder.mTvDuration.setText(HandlingMusic.createTimerLabel(Integer.parseInt(mLvAudioModel.get(position).getDuration())));
        Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(mLvAudioModel.get(position).getIdAlbum()), context));
        if (bitmap == null) {
            holder.mImgAlbum.setImageResource(R.drawable.bg_musicerror);
        } else {
            holder.mImgAlbum.setImageBitmap(bitmap);
        }
        holder.mCardView.setOnClickListener(view -> onClickItemDetailListener.onclickItem(position));
        holder.mRelaOption.setOnClickListener(v -> {
            onClickItemDetailListener.onclickDelete(position);
            if (mLvAudioModel.size() == 1) {
                notifyDataSetChanged();
            }
        });
        holder.mCardView.setOnClickListener(view -> onClickItemDetailListener.onclickItem(position));
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
