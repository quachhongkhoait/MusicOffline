package com.cj.musicoffline.ui.fragment.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
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
import com.cj.musicoffline.utils.HandlingMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterNew extends RecyclerView.Adapter<AdapterNew.ViewHolder> {

    Context mContext;
    List<AudioModel> mLvAudioModel;
    private AdapterNew.OnClickItemMusicListener onClickItemMusicListener;

    public AdapterNew(Context mContext, List<AudioModel> mLvAudioModel, AdapterNew.OnClickItemMusicListener onClickItemMusicListener) {
        this.mContext = mContext;
        this.mLvAudioModel = mLvAudioModel;
        this.onClickItemMusicListener = onClickItemMusicListener;
    }

    @NonNull
    @Override
    public AdapterNew.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_recommended, parent, false);
        AdapterNew.ViewHolder viewHolder = new AdapterNew.ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull AdapterNew.ViewHolder holder, final int position) {
        holder.mTvTitle.setText(mLvAudioModel.get(position).getTitle());
        holder.mTvDuration.setText(HandlingMusic.createTimerLabel(Integer.parseInt(mLvAudioModel.get(position).getDuration())));
        Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(mLvAudioModel.get(position).getIdAlbum()), mContext));
        if (bitmap == null) {
            holder.mRoundedImageView.setImageResource(R.drawable.bg_musicerror);
        } else {
            holder.mRoundedImageView.setImageBitmap(bitmap);
        }
        holder.mRoundedImageView.setOnClickListener(view -> onClickItemMusicListener.onclickItem(position));

    }

    @Override
    public int getItemCount() {
        return mLvAudioModel.size();
    }

    interface OnClickItemMusicListener {
        void onclickItem(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle, mTvDuration;
        RoundedImageView mRoundedImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.txt_nameSong);
            mTvDuration = itemView.findViewById(R.id.txt_nameSinger);
            mRoundedImageView = itemView.findViewById(R.id.img_recommended);
        }
    }
}
