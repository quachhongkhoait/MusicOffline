package com.cj.musicoffline.ui.fragment.library.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cj.musicoffline.R;

public class TimeOffDialog extends DialogFragment {
    private View mView;
    private Switch mSwitchTimeOff;
    private RadioButton mRaBtn15,mRaBtn30,mRaBtn60,mRaBtn120;
    private EditText mEdtTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_time_off, container, false);
        initView();
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.colorPickerStyle);
    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        super.onResume();

    }

    private void initView() {
        mSwitchTimeOff = mView.findViewById(R.id.sW_Off);
        mRaBtn15 = mView.findViewById(R.id.rB_15);
        mRaBtn30 = mView.findViewById(R.id.rB_30);
        mRaBtn60 = mView.findViewById(R.id.rB_60);
        mRaBtn120 = mView.findViewById(R.id.rB_120);
        mEdtTime = mView.findViewById(R.id.edt_time_off);

    }
}
