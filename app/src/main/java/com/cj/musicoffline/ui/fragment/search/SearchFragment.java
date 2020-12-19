package com.cj.musicoffline.ui.fragment.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.model.SearchContent;
import com.cj.musicoffline.ui.fragment.library.dialog.ShowBottomSheetDialog;
import com.cj.musicoffline.ui.fragment.library.favourite.AdapterFavourite;
import com.cj.musicoffline.ui.main.SearchActivity;
import com.cj.musicoffline.ui.searchdetail.DetailSearchActivity;
import com.cj.musicoffline.utils.PlayMusic;
import com.cj.musicoffline.viewmodel.ShareViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SearchFragment extends Fragment {
    private Button mBTDelete;
    private ShareViewModel mVM;
    private TextView mSearch;
    private List<AudioModel> arrayList = new ArrayList<>();
    AdapterSearch mAdapter;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mTVNullSongs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mVM = new ViewModelProvider(this, App.factory).get(ShareViewModel.class);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
        getAllSearch();
        mSearch.setOnClickListener(v -> startActivity(new Intent(getActivity(), DetailSearchActivity.class)));
        mBTDelete.setOnClickListener(v -> {
            mVM.deleteAllSearch();
            arrayList.clear();
            mAdapter.notifyDataSetChanged();
        });
    }

    private void getAllSearch() {
        mVM.getAllSearch().observe(getViewLifecycleOwner(), searchContents -> {
            arrayList.clear();
            Collections.sort(searchContents, (o1, o2) -> {
                Date date1 = new Date(o1.getTime());
                Date date2 = new Date(o2.getTime());
                return date2.compareTo(date1);
            });
            for (SearchContent it : searchContents) {
                mVM.getByID(it.getUrl()).observe(getViewLifecycleOwner(), model -> {
                    arrayList.add(model);
                    mAdapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void setUp(View view) {
        mRecyclerView = view.findViewById(R.id.search_recycler_view);
        mSearch = view.findViewById(R.id.searchViewItem);
        mBTDelete = view.findViewById(R.id.btn_delete_search);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AdapterSearch(getActivity(), arrayList, new AdapterSearch.OnClickSearch() {
            @Override
            public void onClickOpen(List<AudioModel> list, int pos) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(pos).equals(list.get(i))) {
                        PlayMusic.StartMusic(list, getActivity(), i);
                    }
                }
            }

            @Override
            public void onClickDelete(int pos, String url) {
                mVM.deleteSearch(url);
                arrayList.remove(pos);
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}