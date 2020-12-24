package com.cj.musicoffline.ui.fragment.library.dialog;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.cj.musicoffline.R;
import com.cj.musicoffline.broadcast.NotificationActionService;
import com.cj.musicoffline.utils.SessionManager;

import java.text.Format;
import java.util.Date;

public class TimeOffDialog extends DialogFragment {
    private View mView;
    private Switch mSwitchTimeOff;
    private RadioButton mRB15, mRB30, mRB60, mRB120;
    private EditText mEdtTime;
    private TextView mTVTimeOff, mTVOK;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_timer, container, false);
        initView();
        checkTimeOff();
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.colorPickerStyle);
    }

    @SuppressLint("SetTextI18n")
    private void checkTimeOff() {
        if (!SessionManager.getInstance().getKeyAlarmManagerment().equals("") && SessionManager.getInstance().getKeyTimeOfft() != 0) {
            Date now = new Date();
            long time = Long.parseLong(SessionManager.getInstance().getKeyAlarmManagerment());
            long noDay = (now.getTime() - time) / (60 * 1000);//
            Log.d("zzz", "checkTimeOff: " + noDay);
            int timeoff = SessionManager.getInstance().getKeyTimeOfft();
            int timeRemain = (int) (timeoff - noDay);
            if (timeoff > noDay) {
                mTVTimeOff.setText(timeRemain + "'");
                mSwitchTimeOff.setChecked(true);
            }
        }
    }

    @Override
    public void onResume() {
        getDialog().getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        super.onResume();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private void initView() {
        mSwitchTimeOff = mView.findViewById(R.id.mSwitch);
        mRB15 = mView.findViewById(R.id.mRB15);
        mRB30 = mView.findViewById(R.id.mRB30);
        mRB60 = mView.findViewById(R.id.mRB60);
        mRB120 = mView.findViewById(R.id.mRB120);
        mEdtTime = mView.findViewById(R.id.mETInput);
        mTVTimeOff = mView.findViewById(R.id.mTVTimeOff);
        mTVOK = mView.findViewById(R.id.mIVOK);

        mRB15.setOnCheckedChangeListener(listenerRadio);
        mRB30.setOnCheckedChangeListener(listenerRadio);
        mRB60.setOnCheckedChangeListener(listenerRadio);
        mRB120.setOnCheckedChangeListener(listenerRadio);

        mEdtTime.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                mTVTimeOff.setText(mEdtTime.getText().toString() + "'");
                return true;
            }
            return false;
        });

        mSwitchTimeOff.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                mRB30.setChecked(true);
//                mTVTimeOff.setText("30'");
//            } else {
//                mRB15.setChecked(false);
//                mRB30.setChecked(false);
//                mRB60.setChecked(false);
//                mRB120.setChecked(false);
//                mTVTimeOff.setText("");
//            }
        });

        mTVOK.setOnClickListener(v -> {
            String edt = mEdtTime.getText().toString();
            if (edt.equals("")) {
                int timeoff = Integer.parseInt(mTVTimeOff.getText().toString().replace("'", ""));
                if (timeoff != 0) {
                    timer(timeoff);
                    dismiss();
                }
            } else {
                timer(Integer.parseInt(edt));
                dismiss();
            }
        });
    }

    private void changeSwitch() {
        mSwitchTimeOff.setChecked(true);
    }

    CompoundButton.OnCheckedChangeListener listenerRadio = (buttonView, isChecked) -> {
        if (isChecked) {
            mTVTimeOff.setText(buttonView.getText().toString());
            changeSwitch();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void timer(int s) {
        Date date = new Date();
        String time = String.valueOf(date.getTime());
        SessionManager.getInstance().setKeyTimeOff(s);
        SessionManager.getInstance().setKeyAlarmManagerment(time);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intentClose = new Intent(getActivity(), NotificationActionService.class)
                .setAction("ACTION_CLOSE");
        PendingIntent pendingIntentClose = PendingIntent.getBroadcast(getActivity(), 99,
                intentClose, PendingIntent.FLAG_UPDATE_CURRENT);
        int ALARM_DELAY_IN_SECOND = 60;
        if (s > 1) {
            ALARM_DELAY_IN_SECOND *= (s - 1);
        }
        long alarmTimeOFF = System.currentTimeMillis() + ALARM_DELAY_IN_SECOND * 1_000L;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimeOFF, pendingIntentClose);
    }
}
