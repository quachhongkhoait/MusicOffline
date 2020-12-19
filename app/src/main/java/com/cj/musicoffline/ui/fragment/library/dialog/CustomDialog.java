package com.cj.musicoffline.ui.fragment.library.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cj.musicoffline.R;
import com.cj.musicoffline.model.PlayListModel;
import com.cj.musicoffline.viewmodel.ShareViewModel;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private EditText mEDTNameList;
    private TextView mTVOK, mTVCancel;
    private ShareViewModel mShareViewModel;

    public CustomDialog(@NonNull Context context, ShareViewModel model) {
        super(context);
        this.mShareViewModel = model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_playlist);
        mEDTNameList = findViewById(R.id.mETNameList);
        mTVOK = findViewById(R.id.mTVOK);
        mTVCancel = findViewById(R.id.mTVCancel);
        mTVOK.setOnClickListener(this);
        mTVCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mTVOK) {
            String nameList = mEDTNameList.getText().toString();
            mShareViewModel.insertPlayList(new PlayListModel(0, nameList, 1));
        }
        dismiss();
    }
}
