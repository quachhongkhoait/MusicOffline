package com.cj.musicoffline.ui.searchdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cj.musicoffline.R;
import com.cj.musicoffline.app.App;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.SearchContent;
import com.cj.musicoffline.utils.PlayMusic;
import com.cj.musicoffline.viewmodel.ShareViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailSearchActivity extends AppCompatActivity {

    private AdapterSearchDetail mAdapter;
    private List<AudioModel> mList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ImageView mIVBack;
    private EditText mETSearch;
    private ShareViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);
        mViewModel = new ViewModelProvider(this, App.factory).get(ShareViewModel.class);
        setup();
        getData();
    }

    private void getData() {
        mViewModel.getAllMusic().observe(this, audioModels -> {
            mList.clear();
            mList.addAll(audioModels);
            mAdapter.notifyDataSetChanged();
        });
    }

    private void setup() {
        mRecyclerView = findViewById(R.id.mReCyclerView);
        mIVBack = findViewById(R.id.mIVBack);
        mETSearch = findViewById(R.id.mETSearch);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AdapterSearchDetail(this, mList, model -> {
            mViewModel.insertSearch(new SearchContent(model.getUrl(), System.currentTimeMillis()));
            for (int i = 0; i < mList.size(); i++) {
                if (model.equals(mList.get(i))) {
                    PlayMusic.StartMusic(mList, this, i);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mIVBack.setOnClickListener(v -> finish());

        //search
        mETSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s);
            }
        });
    }
}