package com.daiy.dylib.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.daiy.dylib.R;
import com.daiy.dylib.permission.PermissionRuntimeManager;
import com.daiy.dylib.tools.FileUtil;
import com.daiy.dylib.tools.InputUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by LinW on 2016/3/15.
 * 用户修改头像
 */
public abstract class PhotoActivity extends BaseActivity implements View.OnClickListener {
    protected static final int TAKE = 10;
    protected static final int PICK = 11;
    protected static final int CUT = 12;
    protected boolean needCut = false;
    protected View location;
    protected int layoutId;
    protected ImageView im_head;
    protected String imPath;
    protected PhotoPopup photo;
    protected PermissionRuntimeManager runtimeManager;
    protected String authority = "com.devdaiyue.mylibrary";

    /**
     * 初始化
     *
     * @param viewId   头像ID
     * @param location 弹窗位置
     * @param needCut  是否裁剪
     * @param layoutId 弹窗布局ID  0使用默认
     */
    public void initPhoto(int viewId, View location, boolean needCut, int layoutId) {
        im_head = (ImageView) findViewById(viewId);
        im_head.setOnClickListener(this);
        this.needCut = needCut;
        this.location = location;
        this.layoutId = layoutId;
        runtimeManager = new PermissionRuntimeManager(this);
    }

    @Override
    public void onClick(View v) {
        InputUtils.hideInputView(this, v);
        photo = new PhotoPopup(layoutId);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        photo.showAtLocation(location, Gravity.BOTTOM, 0, 0);
        photo.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK:
                    if (needCut) {
                        startPhotoZoom(data.getData());
                    } else {
                        imPath = doPhoto(data);
                        photoCallBack(imPath);
                        photo.dismiss();
//                        changeHead();
                    }
                    break;
                case TAKE:
                    if (needCut) {
                        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        File mediaStorageDir = new File(file, "take_photo.jpg");
//                        Uri imageUri = Uri.fromFile(mediaStorageDir);
                        Uri imageUri = getImageContentUri(this, mediaStorageDir);
                        startPhotoZoom(imageUri);
                    } else {
                        photoCallBack(imPath);
//                        changeHead();
                        photo.dismiss();
                    }
                    break;
                case CUT:
//                     Bundle extras = data.getExtras();
//                     if(extras!=null){
//                         Bitmap bitmap = extras.getParcelable("data");//裁剪后的图片
//                         im_head.setImageBitmap(bitmap);
//                         imPath=savePhotoToSDCard("take_photo.jpg", bitmap);
//                         photoCallBack(imPath);
//                     }

//                     Bitmap bitmap= BitmapFactory.decodeFile(imPath);
//                     im_head.setImageBitmap(bitmap);
                    photoCallBack(imPath);
//                    changeHead();
                    photo.dismiss();
                    break;
                default:
                    onMyActivityResult(requestCode, resultCode, data);
                    break;
            }
//            onMyActivityResult(requestCode, resultCode, data);
        }
    }

    public abstract void onMyActivityResult(int requestCode, int resultCode, Intent data);

    public abstract void photoCallBack(String path);

    /**
     * 更新头像显示
     */
    protected void changeHead() {
        Bitmap bitmap = BitmapFactory.decodeFile(imPath);
        im_head.setImageBitmap(bitmap);
    }

    /**
     * 裁剪图片
     */
    private void startPhotoZoom(Uri uri) {
        /*
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
         * yourself_sdk_path/docs/reference/android/content/Intent.html
         * 直接在里面Ctrl+F搜：CROP ，之前没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
//        intent.setDataAndType(uri, "image/jpeg");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("circleCrop", "true");//圆形裁剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", im_head.getWidth());
        intent.putExtra("outputY", im_head.getHeight());
//        intent.putExtra("return-data", "true");


        //该方法会在MIUI中出错
//        intent.putExtra("return-data", true);
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File mediaStorageDir = new File(file, "gos_cut.jpg");
        try {
            file.mkdirs();
        } catch (Exception e) {
            // TODO: handle exception
        }
        Uri imageUri = Uri.fromFile(mediaStorageDir);
        imPath = mediaStorageDir.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        PhotoActivity.this.startActivityForResult(intent, CUT);
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param data
     * @return 图片地址  可能为空
     */
    public String doPhoto(Intent data) {
        Uri photoUri;
        String picPath = null;
        if (data == null) {
            Toast.makeText(this, "选择图片失败", Toast.LENGTH_SHORT).show();
            return null;
        }
        photoUri = data.getData();
        if (photoUri == null) {
            Toast.makeText(this, "选择图片失败", Toast.LENGTH_SHORT).show();
            return null;
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(photoUri, pojo, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            cursor.close();
        }
//		if(picPath != null){
//			Intent intent=new Intent();
//			intent.putExtra(KEY_PHOTO_PATH, picPath);
//			setResult(Activity.RESULT_OK, intent);
//			finish();
//		}else{
//			showToast(getString(R.string.photo_pic_fail));
//		}
        return picPath;
    }

    /**
     * 保存图片到SD卡
     *
     * @param bitmap 图片的bitmap对象
     * @return
     */
    public static String savePhotoToSDCard(String fileName, Bitmap bitmap) {
        // 图片在SD卡中的缓存路径
        String IMAGE_PATH = Environment
                .getExternalStorageDirectory().toString()
                + File.separator
                + "superdd" + File.separator + "Images" + File.separator;
        if (!FileUtil.isSdcardExist()) {
            return null;
        }
        FileOutputStream fileOutputStream = null;
        FileUtil.createDirFile(IMAGE_PATH);

        if (fileName == null)
            fileName = UUID.randomUUID().toString() + ".jpg";
        String newFilePath = IMAGE_PATH + fileName;
        File file = FileUtil.createNewFile(newFilePath);
        if (file == null) {
            return null;
        }
        try {
            fileOutputStream = new FileOutputStream(newFilePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e1) {
            return null;
        } finally {
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                return null;
            }
        }
        return newFilePath;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        runtimeManager.dealRequestResult(requestCode, permissions, grantResults);
    }

    private class PhotoPopup extends PopupWindow implements View.OnClickListener {

        public PhotoPopup(int layout) {
            View contentV;
            LayoutInflater inflater = (LayoutInflater) PhotoActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (layout == 0) {
                contentV = inflater.inflate(R.layout.photoselect, null);
            } else {
                contentV = inflater.inflate(layout, null);
            }
            setContentView(contentV);
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setOutsideTouchable(true);
            setFocusable(true);
            // 刷新状态
            this.update();
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
            this.setBackgroundDrawable(dw);

            // 设置SelectPicPopupWindow弹出窗体动画效果
            setAnimationStyle(R.style.AnimBottom);

            contentV.findViewById(R.id.pop_cancel).setOnClickListener(this);
            contentV.findViewById(R.id.pop_take).setOnClickListener(this);
            contentV.findViewById(R.id.pop_pick).setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.pop_pick) {
                runtimeManager.setOnPermissionResultListener(new PermissionRuntimeManager.OnPermissionResultListener() {
                    @Override
                    public void allPermissionGranted() {
                        pickPhoto();
                    }

                    @Override
                    public void somePermissionDenied() {

                    }
                });
                runtimeManager.checkAndRequestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            } else if (i == R.id.pop_take) {
                runtimeManager.setOnPermissionResultListener(new PermissionRuntimeManager.OnPermissionResultListener() {
                    @Override
                    public void allPermissionGranted() {
                        imPath = takePhoto();
                    }

                    @Override
                    public void somePermissionDenied() {

                    }
                });
                runtimeManager.checkAndRequestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

            } else if (i == R.id.pop_cancel) {
                dismiss();
            }
        }

        /**
         * 拍照
         *
         * @return 相片位置
         */
        private String takePhoto() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File mediaStorageDir = new File(file, "take_photo.jpg");
            try {
                file.mkdirs();
            } catch (Exception e) {
                // TODO: handle exception
            }
            Uri imageUri = Uri.fromFile(mediaStorageDir);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(PhotoActivity.this, authority, mediaStorageDir);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            PhotoActivity.this.startActivityForResult(intent, TAKE);
            return mediaStorageDir.getAbsolutePath();
        }

        /**
         * 选择照片
         */
        private void pickPhoto() {
//            Intent intent = new Intent();
//            intent.setType("image/jpeg");
////		intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            PhotoActivity.this.startActivityForResult(intent, PICK);
        }
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
