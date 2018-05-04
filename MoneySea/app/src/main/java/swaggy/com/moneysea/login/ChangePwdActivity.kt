package swaggy.com.moneysea.login

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_change_pwd.*
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.utils.StatusBarHeightUtil
import java.util.*

class ChangePwdActivity : Activity(), View.OnClickListener,Handler.Callback {


    private val mHandler = Handler(this)
    private var eventHandler: EventHandler? = null


    //接收验证码倒计时60s
    private var second = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_change_pwd)
        initEvent()
        initView()
    }

    private fun initView() {
        change_pwd_back.setOnClickListener(this)
        change_pwd_button.setOnClickListener(this)
        change_pwd_check.setOnClickListener(this)
    }
    private fun initEvent() {
        // 创建EventHandler对象
        eventHandler = object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //发送成功
                    mHandler.sendEmptyMessage(111)
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //验证成功
                    mHandler.sendEmptyMessage(113)
                } else if (event == SMSSDK.RESULT_ERROR) {
                    //短信发送失败
                    mHandler.sendEmptyMessage(114)
                } else {
                    //短信验证失败
                    mHandler.sendEmptyMessage(115)
                }

            }
        }

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.change_pwd_back -> {
                finish()
            }
            R.id.change_pwd_check -> {
                Log.e("phone","sssss"+v?.id)
                var phone = change_pwd_phone.text.toString()
                if (phone.length in 1..11) {
                    SMSSDK.getVerificationCode("86", phone)
                    mHandler.sendEmptyMessage(112)
                } else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show()

                }
            }
            R.id.change_pwd_button ->{
                //验证验证码-----登录
                var code = change_pwd_code.text.toString()
                if (code.length in 1..11) {
                    SMSSDK.submitVerificationCode("86", change_pwd_phone.text.toString(), code)
                } else {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun handleMessage(msg: Message?): Boolean {
        when (msg?.what) {
            111 -> Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show()
            112 -> {
                //获取验证码后开始倒计时
                change_pwd_check.setText("剩余" + second.toString() + "s")
                second -= 1
                mHandler.sendEmptyMessageDelayed(112, 1000)
                if (second == 0) {
                    mHandler.removeMessages(112)
                    change_pwd_check.setText("重新获取")
                    second = 60
                }
            }
            113 -> {
                Toast.makeText(this, "验证成功", Toast.LENGTH_SHORT).show()
                savePwd()

            }
            114 -> Toast.makeText(this, "短信发送失败", Toast.LENGTH_SHORT).show()
            115 -> Toast.makeText(this, "短信验证失败", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    /**
     * 调用接口,保存密码
     */
    private fun savePwd() {
        val phone = change_pwd_new!!.text.toString()
        val pwd = change_pwd_again!!.text.toString()
        if (!phone.equals(pwd)) {
            Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT).show()
            return
        }
        val stringMap = HashMap<String, String>()
        stringMap.put("mobile",change_pwd_phone.text.toString())
        stringMap.put("password",pwd)
        OkGo.post<WSResult<String>>(HttpContants.CHANGE_PWD)
                .cacheMode(CacheMode.NO_CACHE)
                .params(stringMap)
                .execute(object : JsonCallback<WSResult<String>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<String>>?) {
                        when (response!!.body().code) {
                            ErrorCode.SUCCESS -> {
                                mHandler.removeCallbacksAndMessages(null);
                                finish()
                            }

                        }

                    }

                })
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null);
    }

}
