package com.cj.musicoffline.ui.fragment.library.playlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.viewmodel.ShareViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterPlayList extends RecyclerView.Adapter<AdapterPlayList.ViewHolder> {

    interface OnClickItemMusicListener {
        void onClickOpen(int pos, int id);

        void onClickDelete(int pos, int id);
    }

    private Context mContext;
    private List<PlayListModel> mList;
    private ShareViewModel mModel;
    private AdapterPlayList.OnClickItemMusicListener onClickItemMusicListener;

    public AdapterPlayList(Context mContext, List<PlayListModel> mList, ShareViewModel mModel, OnClickItemMusicListener onClickItemMusicListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mModel = mModel;
        this.onClickItemMusicListener = onClickItemMusicListener;
    }

    @NonNull
    @Override
    public AdapterPlayList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_playlist, parent, false);
        return new AdapterPlayList.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterPlayList.ViewHolder holder, final int position) {
        holder.mTvTitle.setText(mList.get(position).getNameList());
        holder.mTvCountSongs.setText(mList.get(position).getCount() + " bài");

        holder.mRIVPlayList.setImageResource(R.drawable.bg_musicerror);
        holder.mLLClick.setOnClickListener(view -> {
            onClickItemMusicListener.onClickOpen(position, mList.get(position).getId());
        });
        holder.mIVDelete.setOnClickListener(v -> onClickItemMusicListener.onClickDelete(position, mList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle, mTvCountSongs;
        RoundedImageView mRIVPlayList;
        ImageView mIVDelete;
        LinearLayout mLLClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            mTvCountSongs = itemView.findViewById(R.id.tvCountSongs);
            mRIVPlayList = itemView.findViewById(R.id.mRIVPlayList);
            mIVDelete = itemView.findViewById(R.id.mIVDelete);
            mLLClick = itemView.findViewById(R.id.mLLClick);
        }
    }
}
