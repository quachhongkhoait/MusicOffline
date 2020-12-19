package com.cj.musicoffline.ui.fragment.library.playlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.PlayList;
import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.viewmodel.ShareViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParsePosition;
import java.util.List;

public class AdapterPlayList extends RecyclerView.Adapter<AdapterPlayList.ViewHolder> {

    interface OnClickItemMusicListener {
        void onClickOpen(int pos, int id);

        void onClickDelete(int pos, int id);
    }

    private Context mContext;
    private List<PlayList> mList;
    private ShareViewModel mModel;
    private AdapterPlayList.OnClickItemMusicListener onClickItemMusicListener;

    public AdapterPlayList(Context mContext, List<PlayList> mList, ShareViewModel mModel, OnClickItemMusicListener onClickItemMusicListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.mModel = mModel;
        this.onClickItemMusicListener = onClickItemMusicListener;
    }

    //    void setData(List<PlayListModel> list, List<String> listCount) {
//        mList.clear();
//        mListCount.clear();
//        this.mList.addAll(list);
//        this.mListCount.addAll(listCount);
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public AdapterPlayList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_playlist, parent, false);
        AdapterPlayList.ViewHolder viewHolder = new AdapterPlayList.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterPlayList.ViewHolder holder, final int position) {
        holder.mTvTitle.setText(mList.get(position).getNameList());
        holder.mTvCountSongs.setText(mList.get(position).getCount() + " bài");
//        if (mListCount.get(position) == 0) {
//            holder.mTvCountSongs.setText("0 bài");
//        } else {

//        Log.d("nnn", "onBindViewHolder: " + mListCount.size());
//        }

        holder.mRIVPlayList.setImageResource(R.drawable.bg_musicerror);
        holder.mLLClick.setOnClickListener(view -> onClickItemMusicListener.onClickOpen(position, mList.get(position).getId()));
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
