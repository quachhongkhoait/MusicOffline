package com.cj.musicoffline.ui.fragment.library.favourite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cj.musicoffline.R;

public class FavouriteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(), "Yêu thích", Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }
}