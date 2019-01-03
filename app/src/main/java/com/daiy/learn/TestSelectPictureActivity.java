package com.daiy.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.daiy.dylib.tools.PhotoTools;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class TestSelectPictureActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        imageView = findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(v ->
                PictureSelector.create(TestSelectPictureActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .compress(true)
                        .maxSelectNum(1)
                        .minSelectNum(1)
                        .forResult(PictureConfig.CHOOSE_REQUEST));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    Log.e("=====>>>", "onActivityResult: " + selectList.get(0).getPath());
                    imageView.setImageBitmap(PhotoTools.resizePhoto(selectList.get(0).getPath()));
//                    imageView.setImageURI(Uri.parse(selectList.get(0).getCompressPath()));
//                    Glide.with(getApplicationContext()).load(selectList.get(0).getPath()).into(imageView);
                    imageView.setOnClickListener(v ->
                            PictureSelector.create(TestSelectPictureActivity.this)
                                    .externalPicturePreview(0, selectList)
                    );
                    break;
            }
        }
    }
}
