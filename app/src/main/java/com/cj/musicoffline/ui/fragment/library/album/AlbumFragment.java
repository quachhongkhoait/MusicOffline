package com.cj.musicoffline.ui.fragment.library.album;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cj.musicoffline.R;

public class AlbumFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(), "Album", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_album, container, false);
    }
}