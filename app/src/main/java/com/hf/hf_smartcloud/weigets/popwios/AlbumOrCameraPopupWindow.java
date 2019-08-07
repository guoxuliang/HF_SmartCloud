package com.hf.hf_smartcloud.weigets.popwios;

import android.view.View;
import android.widget.Button;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.ui.activity.LoginActivity;

public class AlbumOrCameraPopupWindow extends BasePopupWindow {

    private Button btnCamera;
    private Button btnAlbum;
    private Button btnCancel;

    private OnCameraOrAlbumSelectListener onCameraOrAlbumSelectListener;
    public AlbumOrCameraPopupWindow(LoginActivity context, OnCameraOrAlbumSelectListener onCameraOrAlbumSelectListener) {
        super(context);
        this.onCameraOrAlbumSelectListener = onCameraOrAlbumSelectListener;
    }

    @Override
    public boolean isNeedBackgroundHalfTransition(){
        return true;
    }

    @Override
    protected int bindLayout() {
        return R.layout.popup_window_album_or_camera;
    }

    @Override
    protected void initView(View parentView) {
        btnCamera = (Button) parentView.findViewById(R.id.btnCamera);
        btnAlbum = (Button) parentView.findViewById(R.id.btnAlbum);
        btnCancel = (Button) parentView.findViewById(R.id.btnCancel);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onCameraOrAlbumSelectListener != null){
                    onCameraOrAlbumSelectListener.OnSelectCamera();
                }
            }
        });

        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onCameraOrAlbumSelectListener != null){
                    onCameraOrAlbumSelectListener.OnSelectAlbum();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}