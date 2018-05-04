package swaggy.com.moneysea.utils;

import android.app.Activity;

import com.awen.photo.photopick.bean.PhotoResultBean;
import com.awen.photo.photopick.controller.PhotoPickConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import swaggy.com.moneysea.model.UploadEvent;

/**
 * Writer：GuoWei_aoj on 2018/4/26 0026 14:26
 * description:
 */
public class PhotoUtil {



    public static void  pickPhoto(Activity activity, final int type, final int flag){
        new PhotoPickConfig.Builder(activity)
                .pickMode(PhotoPickConfig.MODE_SINGLE_PICK)
                .showCamera(true)
                .setOnPhotoResultCallback(new PhotoPickConfig.Builder.OnPhotoResultCallback() {
                    @Override
                    public void onResult(PhotoResultBean result) {
                        ArrayList<String> photoLists = result.getPhotoLists();

                            EventBus.getDefault().post(new UploadEvent(type,flag,photoLists.get(0)));
                            //上传图片
//                            UpFile.uploadImg(this, file, PandaApplication.getContext().getResources().getString(R.string.picture_uploading), userBean.getId(), "user", "PandaUserPhoto");


                    }
                })
                .build();

    }
}
