package swaggy.com.moneysea.utils;

import android.app.Activity;
import android.util.Log;

import com.awen.photo.photopick.bean.PhotoResultBean;
import com.awen.photo.photopick.controller.PhotoPickConfig;

import java.io.File;
import java.util.ArrayList;

/**
 * Writer：GuoWei_aoj on 2018/4/26 0026 14:26
 * description:
 */
public class PhotoUtil {



    public static void  pickPhoto(Activity activity){
        new PhotoPickConfig.Builder(activity)
                .pickMode(PhotoPickConfig.MODE_SINGLE_PICK)
                .showCamera(true)
                .setOnPhotoResultCallback(new PhotoPickConfig.Builder.OnPhotoResultCallback() {
                    @Override
                    public void onResult(PhotoResultBean result) {
                        ArrayList<String> photoLists = result.getPhotoLists();
                        Log.e("MainActivity", "result = " + photoLists.get(0));
                        File file = new File(photoLists.get(0));
                        if (file.exists()) {
                            //上传图片
//                            UpFile.uploadImg(this, file, PandaApplication.getContext().getResources().getString(R.string.picture_uploading), userBean.getId(), "user", "PandaUserPhoto");

                        }
                    }
                })
                .build();

    }
}
