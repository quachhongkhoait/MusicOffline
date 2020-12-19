package com.cj.musicoffline.ui.searchdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.utils.HandlingMusic;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterSearchDetail extends RecyclerView.Adapter<AdapterSearchDetail.ViewHolder> implements Filterable {

    interface OnClickSearch {
        void onclickItem(AudioModel audioModel);
    }

    Context mContext;
    List<AudioModel> mLvAudioModel;
    List<AudioModel> mAudioFilter;
    private AdapterSearchDetail.OnClickSearch onClickItemMusicListener;

    public AdapterSearchDetail(Context mContext, List<AudioModel> mLvAudioModel, OnClickSearch onClickItemMusicListener) {
        this.mContext = mContext;
        this.mLvAudioModel = mLvAudioModel;
        this.mAudioFilter = this.mLvAudioModel;
        this.onClickItemMusicListener = onClickItemMusicListener;
    }

    @NonNull
    @Override
    public AdapterSearchDetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_music, parent, false);
        AdapterSearchDetail.ViewHolder viewHolder = new AdapterSearchDetail.ViewHolder(view, mAudioFilter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchDetail.ViewHolder holder, final int position) {
        AudioModel model = mAudioFilter.get(position);
        holder.mTvTitle.setText(model.getTitle());
        holder.mTvDuration.setText(HandlingMusic.createTimerLabel(Integer.parseInt(model.getDuration())));
        Bitmap bitmap = BitmapFactory.decodeFile(HandlingMusic.getCoverArtPath(Long.parseLong(model.getIdAlbum()), mContext));
        if (bitmap == null) {
            holder.mImgAlbum.setImageResource(R.drawable.bg_musicerror);
        } else {
            holder.mImgAlbum.setImageBitmap(bitmap);
        }
        holder.mCardView.setOnClickListener(view -> onClickItemMusicListener.onclickItem(model));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mAudioFilter = mLvAudioModel;
                } else {
                    List<AudioModel> filteredList = new ArrayList<>();
                    for (AudioModel row : mAudioFilter) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    mAudioFilter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mAudioFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mAudioFilter = (ArrayList<AudioModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mAudioFilter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle, mTvDuration;
        RoundedImageView mImgAlbum;
        CardView mCardView;
        RelativeLayout mRelaOption;

        public ViewHolder(@NonNull View itemView, List<AudioModel> mLvAudioModel) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvDuration = itemView.findViewById(R.id.tvDuration);
            mImgAlbum = itemView.findViewById(R.id.imageAlbum);
            mCardView = itemView.findViewById(R.id.cardview);
            mRelaOption = itemView.findViewById(R.id.relaOption);
            mRelaOption.setVisibility(View.GONE);
//            itemView.setOnClickListener(v -> {
//                onClickItemMusicListener.onclickItem(mAudioFilter, getAdapterPosition());
//            });
        }
    }
}
