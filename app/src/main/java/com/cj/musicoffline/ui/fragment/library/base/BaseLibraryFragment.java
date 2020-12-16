package com.cj.musicoffline.ui.fragment.library.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cj.musicoffline.R;
import com.cj.musicoffline.eventbuss.ChangeFragment;
import com.cj.musicoffline.itf.OnCurrentFragmentListener;
import com.cj.musicoffline.ui.fragment.library.LibraryFragment;
import com.cj.musicoffline.ui.fragment.library.songs.SongsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseLibraryFragment extends Fragment {
    public FragmentTransaction transaction;
    private OnCurrentFragmentListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        replaceFragment(new LibraryFragment(), false);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.setCustomAnimations(R.anim.slide_in_right, 0);
        transaction.replace(R.id.mFrameLayoutHome, fragment);
        transaction.commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnCurrentFragmentListener) context;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d("nnn", "onDestroy: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ChangeFragment(ChangeFragment cf) {

        replaceFragment(cf.getFm(), true);
    }
}