package com.cj.musicoffline.ui.fragment.library.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cj.musicoffline.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ShowBottomSheetDialog extends BottomSheetDialogFragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_option_mi,container,false);
        return mView;
    }
}
