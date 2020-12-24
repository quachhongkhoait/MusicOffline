package com.cj.musicoffline.ui.playmusic.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.eventbuss.SendLyrics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LyricsFragment extends Fragment {
    private List<String> mLyric = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LyricAdapter lyricAdapter;
    private TextView mTVNullLyrics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_lyrics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view);
        if (getArguments() != null) {
            String id = getArguments().getString("idLyrics");
            String path = getArguments().getString("Path");
            getLyric(id, path);
        }
    }

    private void getLyric(String id, String path) {
        mLyric.clear();
//        File file = new File("/storage/emulated/0/bluetooth/Lyrics/", "1079373359");
        File file = new File(path + "/Lyrics/", id);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            if ("".equals(br.readLine())) {
                return;
            }
            Log.d("nnn", "mLyric: " + br.read());

            while ((line = br.readLine()) != null) {

//                String lyric = line.replaceAll(line.substring(0, 10), "");
                String lyric = line.substring(line.lastIndexOf(']') + 1);
//                String lyric = line.substring(10);
                if (lyric.equals("")) {
                    lyric = "...";
                }
                mLyric.add(lyric);
                text.append(line);
                text.append('\n');
            }
            br.close();
            Log.d("nnn", "mLyric: " + text.toString());
        } catch (IOException e) {
            Log.d("nnn", "IOException: " + e.getMessage());
        }
        lyricAdapter.notifyDataSetChanged();
        if (mLyric.size() == 0) {
            mTVNullLyrics.setVisibility(View.VISIBLE);
        } else {
            mTVNullLyrics.setVisibility(View.GONE);
        }
    }

    private void setup(View view) {
        mRecyclerView = view.findViewById(R.id.mRecyclerView);
        mTVNullLyrics = view.findViewById(R.id.mTVNullLyrics);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        lyricAdapter = new LyricAdapter(mLyric);
        mRecyclerView.setAdapter(lyricAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void updateUI(SendLyrics lyrics) {
        getLyric(lyrics.getIdLyrics(), lyrics.getPath());
    }

    public class LyricAdapter extends RecyclerView.Adapter<LyricAdapter.ViewHolder> {
        private List<String> list;

        public LyricAdapter(List<String> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public LyricAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_lyric, parent, false);
            LyricAdapter.ViewHolder viewHolder = new LyricAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull LyricAdapter.ViewHolder holder, int position) {
            holder.mTVLyric.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTVLyric;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTVLyric = itemView.findViewById(R.id.mTVLyric);
            }
        }
    }
}