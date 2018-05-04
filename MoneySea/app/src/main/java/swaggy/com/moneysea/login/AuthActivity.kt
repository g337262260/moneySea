package swaggy.com.moneysea.login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_auth.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import swaggy.com.moneysea.MainActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.model.FinishEvent
import swaggy.com.moneysea.model.UploadEvent
import swaggy.com.moneysea.utils.MyDialog
import swaggy.com.moneysea.utils.PhotoUtil
import swaggy.com.moneysea.utils.StatusBarHeightUtil
import swaggy.com.moneysea.utils.TypeConverter
import java.io.File
import java.util.*

class AuthActivity : Activity(), View.OnClickListener {


    private var imagePath1 : String? = null
    private var imagePath2 : String? = null
    private var imagePath3 : String? = null

    private var dialog: MyDialog? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_auth)
        EventBus.getDefault().register(this)
        initView()
    }



    private fun initView() {

        auth_back.setOnClickListener(this)
        auth_skip.setOnClickListener(this)
        auth_commit.setOnClickListener(this)
        auth_idcard1_image.setOnClickListener(this)
        auth_idcard2_image.setOnClickListener(this)
        auth_idcard3_image3.setOnClickListener(this)
        dialog = MyDialog.showDialog(this);
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.auth_back -> {
                finish()
            }
            R.id.auth_skip -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.auth_commit -> {
                commit()
            }
            R.id.auth_idcard1_image -> {
                //图片1
                PhotoUtil.pickPhoto(this, UploadEvent.TYPE_IMAGE, UploadEvent.FLAG_CARD1)
            }
            R.id.auth_idcard2_image -> {
                //图片2
                PhotoUtil.pickPhoto(this, UploadEvent.TYPE_IMAGE, UploadEvent.FLAG_CARD2)
            }
            R.id.auth_idcard3_image3 -> {
                //图片3
                PhotoUtil.pickPhoto(this, UploadEvent.TYPE_IMAGE, UploadEvent.FLAG_CARD3)
            }
        }
    }

    private fun commit() {
        Log.e("commit","开始提交")
        dialog?.show()
        if (imagePath1==null) {
            Toast.makeText(this,"请上传身份证正面照",Toast.LENGTH_SHORT)
            return
        }
        if (imagePath2==null) {
            Toast.makeText(this,"请上传身份证正面照",Toast.LENGTH_SHORT)
            return
        }
        if (imagePath3==null) {
            Toast.makeText(this,"请上传身份证正面照",Toast.LENGTH_SHORT)
            return
        }
        if (!(auth_name.text!=null&&auth_name.text.length>0)) {
            Toast.makeText(this,"姓名不能为空",Toast.LENGTH_SHORT)
            return
        }
        if (!(auth_idcard_num.text!=null&&auth_idcard_num.text.length>0)) {
            Toast.makeText(this,"身份证号不能为空",Toast.LENGTH_SHORT)
            return
        }
        Log.e("commit",TypeConverter.encodeBase64File(imagePath1.toString()))
        Log.e("commit",TypeConverter.encodeBase64File(imagePath2.toString()))
        Log.e("commit",TypeConverter.encodeBase64File(imagePath3.toString()))
        val stringMap = HashMap<String, String>()
        stringMap.put("mobile","13261561970")
        stringMap.put("name","郭伟")
        stringMap.put("idCard","130723199210173034")
        stringMap.put("fontImg", TypeConverter.encodeBase64File(imagePath1.toString()))
        stringMap.put("backImg",TypeConverter.encodeBase64File(imagePath2.toString()))
        stringMap.put("handImg",TypeConverter.encodeBase64File(imagePath3.toString()))

        OkGo.post<WSResult<String>>(HttpContants.PERFECT)
                .params(stringMap)
                .execute(object : JsonCallback<WSResult<String>>() {
                    override fun onSuccess(response: Response<WSResult<String>>) {
                        Log.e("commit","success")
                        if (response.body().code==ErrorCode.SUCCESS) {
                            dialog?.cancel()
                            startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                            EventBus.getDefault().post(FinishEvent(1))
                            finish()
                        }
                    }

                    override fun onError(response: Response<WSResult<String>>) {

                    }

                })
    }

    @Subscribe
    fun onMessageEvent(event: UploadEvent) {
        // do something
        if (event.type == 0) {
            //上传图片
            val path = event.path
            val file: File = File(path)
            val absolutePath = file.getAbsolutePath()
            when (event.flag) {
                UploadEvent.FLAG_CARD1 -> {
                    auth_idcard1_image.setImageURI(Uri.parse("file://" + absolutePath))
                    imagePath1 = path
                }
                UploadEvent.FLAG_CARD2 -> {
                    auth_idcard2_image.setImageURI(Uri.parse("file://" + absolutePath))
                    imagePath2 = path
                }
                UploadEvent.FLAG_CARD3 -> {
                    auth_idcard3_image3.setImageURI(Uri.parse("file://" + absolutePath))
                    imagePath3 = path
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
