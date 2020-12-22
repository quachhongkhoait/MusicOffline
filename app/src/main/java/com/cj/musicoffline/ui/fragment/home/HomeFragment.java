package com.cj.musicoffline.ui.fragment.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.broadcast.NotificationActionService;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.PlayListModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    AdapterNew adapter;
    RecyclerView mRecyclerView;
    AdapterPL adapterPL;
    RecyclerView mRecyclerViewPL;
    private List<AudioModel> arrayList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private List<PlayListModel> arrayListPL = new ArrayList<>();
    private TextView mTVTTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view);
        getMusicNew();
        getPlayList();
    }

    private void getPlayList() {
        arrayListPL.add(new PlayListModel(1, " Tổng Hợp Playlist Đen Vâu", 1));
        arrayListPL.add(new PlayListModel(1, " Trời hôm nay nhiều mây cực!", 1));
        arrayListPL.add(new PlayListModel(3, " Tổng Hợp Playlist Lê Bảo Bình", 3));
        arrayListPL.add(new PlayListModel(1, "Ngày Khác Lạ", 1));
        arrayListPL.add(new PlayListModel(1, "Mười Năm", 1));
        arrayListPL.add(new PlayListModel(1, "Anh Đếch Cần Gì Ngoài Em", 1));
        arrayListPL.add(new PlayListModel(1, "Cảm ơn", 1));
        arrayListPL.add(new PlayListModel(1, "Đi Theo Bóng Mặt Trời", 1));
    }

    private void getMusicNew() {
        arrayList.add(new AudioModel("content://media/external/audio/media/2332", "lu khach qua thoi gian remix tuan nguyen thieu gia x phong tung 1503364", "198922", "1", "music", "a", "a"));
        arrayList.add(new AudioModel("content://media/external/audio/media/2332", "lu khach qua thoi gian remix tuan nguyen thieu gia x phong tung 1503364", "198922", "1", "music", "a", "a"));
        arrayList.add(new AudioModel("content://media/external/audio/media/2332", "lu khach qua thoi gian remix tuan nguyen thieu gia x phong tung 1503364", "198922", "1", "music", "a", "a"));
        arrayList.add(new AudioModel("content://media/external/audio/media/2332", "lu khach qua thoi gian remix tuan nguyen thieu gia x phong tung 1503364", "198922", "1", "music", "a", "a"));
        arrayList.add(new AudioModel("content://media/external/audio/media/2332", "lu khach qua thoi gian remix tuan nguyen thieu gia x phong tung 1503364", "198922", "1", "music", "a", "a"));
        arrayList.add(new AudioModel("content://media/external/audio/media/2332", "lu khach qua thoi gian remix tuan nguyen thieu gia x phong tung 1503364", "198922", "1", "music", "a", "a"));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setup(View view) {
        mRecyclerView = view.findViewById(R.id.recycleView_recommended);
        mRecyclerViewPL = view.findViewById(R.id.recycleView_Playlist);
        mTVTTest = view.findViewById(R.id.mTVTest);

        mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterNew(getContext(), arrayList, new AdapterNew.OnClickItemMusicListener() {
            @Override
            public void onclickItem(int position) {

            }
        });
        mRecyclerView.setAdapter(adapter);

        mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        mRecyclerViewPL.setLayoutManager(mLayoutManager);
        mRecyclerViewPL.setItemAnimator(new DefaultItemAnimator());
        adapterPL = new AdapterPL(getContext(), arrayListPL, new AdapterPL.OnClickItemMusicListener() {
            @Override
            public void onClickOpen(int pos, int id) {

            }

            @Override
            public void onClickDelete(int pos, int id) {

            }
        });
        mRecyclerViewPL.setAdapter(adapterPL);

        mTVTTest.setOnClickListener(v -> {
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intentClose = new Intent(getActivity(), NotificationActionService.class)
                    .setAction("ACTION_CLOSE");
            PendingIntent pendingIntentClose = PendingIntent.getBroadcast(getActivity(), 0,
                    intentClose, PendingIntent.FLAG_UPDATE_CURRENT);
            int ALARM_DELAY_IN_SECOND = 20;
            long alarmTimeOFF = System.currentTimeMillis() + ALARM_DELAY_IN_SECOND * 1_000L;
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimeOFF, pendingIntentClose);
        });
    }
}