package swaggy.com.moneysea.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

/**
 * Created by zxc on 2018/3/22 0022.
 */

public class UpFile {

    public static void uploadImg(Context context,File file, String loadStr,String classId,String className,String fieldName) {


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(loadStr);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        OkGo.<String>post("")
                .tag(context)
                .isMultipart(true) //强制使用multipart/form-data 表单上传,默认是false
                .params("file", file)
                .params("classId", classId)
                .params("className",className)
                .params("fieldName",fieldName)
                .params("access_token",SharedPreUtils.getString(context, "", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }

                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        super.uploadProgress(progress);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                });
    }

}
