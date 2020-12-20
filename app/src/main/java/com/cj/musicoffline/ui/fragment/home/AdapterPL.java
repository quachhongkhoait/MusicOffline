package com.cj.musicoffline.ui.fragment.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.PlayListModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

class AdapterPL extends RecyclerView.Adapter<AdapterPL.ViewHolder> {

    private Context mContext;
    private List<PlayListModel> mList;
    private OnClickItemMusicListener onClickItemMusicListener;
    
    public AdapterPL(Context mContext, List<PlayListModel> mList, OnClickItemMusicListener onClickItemMusicListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.onClickItemMusicListener = onClickItemMusicListener;
    }

    @NonNull
    @Override
    public AdapterPL.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_playlist, parent, false);
        AdapterPL.ViewHolder viewHolder = new AdapterPL.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterPL.ViewHolder holder, final int position) {
        holder.mTvTitle.setText(mList.get(position).getNameList());

        holder.mRIVPlayList.setImageResource(R.drawable.bg_musicerror);
        holder.mRIVPlayList.setOnClickListener(view -> onClickItemMusicListener.onClickOpen(position, mList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    interface OnClickItemMusicListener {
        void onClickOpen(int pos, int id);

        void onClickDelete(int pos, int id);
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
