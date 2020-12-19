package com.cj.musicoffline.ui.fragment.library.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cj.musicoffline.R;
import com.cj.musicoffline.eventbuss.ChangeFragment;
import com.cj.musicoffline.model.AudioModel;
import com.cj.musicoffline.model.FavouriteModel;
import com.cj.musicoffline.ui.fragment.library.playlist.PlayListFragment;
import com.cj.musicoffline.viewmodel.ShareViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

public class ShowBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private View mView;
    private TextView tvAddLove, tvAddDeleteSong, tvAddSong, tvTitle, tvSinger;
    private ImageView mIVAlbum;
    private AudioModel audioModel;
    private String checkFarvorite;
    private ShareViewModel mModel;
    private int idFavourite;
    private int idDelete;

    public ShowBottomSheetDialog(AudioModel audioModel, String checkFarvorite, ShareViewModel mModel, int idFavourite) {
        this.audioModel = audioModel;
        this.checkFarvorite = checkFarvorite;
        this.mModel = mModel;
        this.idFavourite = idFavourite;
    }

    public void addInFavourite(AudioModel audioModel, String checkFarvorite, ShareViewModel mModel, int idFavourite, int idDelete) {
        this.audioModel = audioModel;
        this.checkFarvorite = checkFarvorite;
        this.mModel = mModel;
        this.idFavourite = idFavourite;
        this.idDelete = idDelete;
    }


    public ShowBottomSheetDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_option_mi, container, false);
        setUp(mView);
        setData();
        setOnClick();
        return mView;
    }

    private void setOnClick() {
        tvAddSong.setOnClickListener(this);
        tvAddLove.setOnClickListener(this);
        tvAddDeleteSong.setOnClickListener(this);
    }

    private void setData() {
        tvTitle.setText(audioModel.getTitle());
        tvSinger.setText(audioModel.getNameAlbum());
        if (checkFarvorite.equals("add")) {
            tvAddLove.setText("Thêm vào Yêu Thích");
            tvAddLove.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_love, 0, 0, 0);
        } else {
            tvAddLove.setText("Bỏ khỏi Yêu Thích");
            tvAddLove.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_love_ok, 0, 0, 0);
        }
    }

    private void setUp(View mView) {
        tvAddLove = mView.findViewById(R.id.tvAddLove);
        tvAddDeleteSong = mView.findViewById(R.id.tvAddDeleteSong);
        tvTitle = mView.findViewById(R.id.tvTitle);
        tvSinger = mView.findViewById(R.id.tvSinger);
        mIVAlbum = mView.findViewById(R.id.mIVAlbum);
        tvAddSong = mView.findViewById(R.id.tvAddSong);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddLove:
                updateFavourite();
                break;
            case R.id.tvAddSong:
                EventBus.getDefault().post(new ChangeFragment(new PlayListFragment()));
                PlayListFragment.isInsert = true;
                break;
            case R.id.tvAddDeleteSong:
                Toast.makeText(getActivity(), "tvAddDeleteSong", Toast.LENGTH_SHORT).show();
                break;
        }
        dismiss();
    }

    private void updateFavourite() {
        if (checkFarvorite.equals("add")) {
            mModel.insertFavourite(new FavouriteModel(0, audioModel.getUrl(), idFavourite));
            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
        } else {
            mModel.deleteFavourite(idDelete);
            Toast.makeText(getActivity(), "Đã xóa", Toast.LENGTH_SHORT).show();
        }
    }

    public AudioModel getAudioModel() {
        return audioModel;
    }

    public void setAudioModel(AudioModel audioModel) {
        this.audioModel = audioModel;
    }

    public String getCheckFarvorite() {
        return checkFarvorite;
    }

    public void setCheckFarvorite(String checkFarvorite) {
        this.checkFarvorite = checkFarvorite;
    }

    public ShareViewModel getmModel() {
        return mModel;
    }

    public void setmModel(ShareViewModel mModel) {
        this.mModel = mModel;
    }

    public int getIdFavourite() {
        return idFavourite;
    }

    public void setIdFavourite(int idFavourite) {
        this.idFavourite = idFavourite;
    }

    public int getIdDelete() {
        return idDelete;
    }

    public void setIdDelete(int idDelete) {
        this.idDelete = idDelete;
    }
}
