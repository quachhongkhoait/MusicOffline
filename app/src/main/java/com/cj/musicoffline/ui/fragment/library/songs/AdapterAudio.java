package com.cj.musicoffline.ui.fragment.library.songs;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.ui.fragment.library.LibraryFragment;
import com.cj.musicoffline.ui.fragment.library.dialog.ShowBottomSheetDialog;
import com.cj.musicoffline.utils.HandlingMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterAudio extends RecyclerView.Adapter<AdapterAudio.ViewHolder> {
    private SongsFragment mSongsFragment;

    public AdapterAudio(SongsFragment fragment){
        this.mSongsFragment = fragment;
    }

    interface OnClickItemMusicListener {
        void onclickItem(int position);
    }

    private OnClickItemMusicListener onClickItemMusicListener;
    Context mContext;
    List<AudioModel> mLvAudioModel;


    public AdapterAudio(Context context, List<AudioModel> items,SongsFragment songsFragment) {
        this.mContext = context;
        this.mLvAudioModel = items;
        this.mSongsFragment = songsFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_music, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTvTitle.setText(mLvAudioModel.get(position).getTitle());
        holder.mTvDuration.setText(HandlingMusic.createTimerLabel(Integer.parseInt(mLvAudioModel.get(position).getDuration())));
        Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(mLvAudioModel.get(position).getIdAlbum()), mContext));
        if (bitmap == null) {
            holder.mImgAlbum.setImageResource(R.drawable.bg_musicerror);
        } else {
            holder.mImgAlbum.setImageBitmap(bitmap);
        }
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemMusicListener.onclickItem(position);
            }
        });
        holder.mRelaOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSongsFragment.showBottomSheetDialog();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvDuration = itemView.findViewById(R.id.tvDuration);
            mImgAlbum = itemView.findViewById(R.id.imageAlbum);
            mCardView = itemView.findViewById(R.id.cardview);
            mRelaOption = itemView.findViewById(R.id.relaOption);
        }
    }

    public void setOnClickItemMusicListener(OnClickItemMusicListener onClickItemMusicListener) {
        this.onClickItemMusicListener = onClickItemMusicListener;
    }
}
