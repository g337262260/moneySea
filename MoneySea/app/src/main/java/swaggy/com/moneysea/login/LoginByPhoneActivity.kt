package swaggy.com.moneysea.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import kotlinx.android.synthetic.main.activity_login_by_phone.*
import swaggy.com.moneysea.R
import swaggy.com.moneysea.utils.StatusBarHeightUtil

class LoginByPhoneActivity : Activity() ,Handler.Callback{



    private var eventHandler: EventHandler? = null

    private val mHandler = Handler(this)

    //接收验证码倒计时60s
    private var second = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_login_by_phone)
        initEvent()
        initView()
    }

    private fun initView() {
        //获取验证码
        login_phone_checkCode.setOnClickListener {
            var phone = login_phone_edit_num.text.toString()
            if (phone.length in 1..11) {
                SMSSDK.getVerificationCode("86", phone)
                mHandler.sendEmptyMessage(102)
            } else {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show()

            }
        }
        login_phone_button.setOnClickListener {
            //验证验证码-----登录
            var code = login_phone_code.text.toString()
            if (code.length in 1..11) {
                SMSSDK.submitVerificationCode("86", login_phone_edit_num.text.toString(), code)
            } else {
                Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show()
            }
        }
        phone_login_back.setOnClickListener { finish() }
    }

    private fun initEvent() {
        // 创建EventHandler对象
        eventHandler = object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //发送成功
                    mHandler.sendEmptyMessage(101)
                } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //验证成功
                    mHandler.sendEmptyMessage(103)
                } else if (event == SMSSDK.RESULT_ERROR) {
                    //短信发送失败
                    mHandler.sendEmptyMessage(104)
                } else {
                    //短信验证失败
                    mHandler.sendEmptyMessage(105)
                }

            }
        }

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler)
    }

    override fun handleMessage(msg: Message?): Boolean {
        when (msg?.what) {
            101 -> Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show()
            102 -> {
                //获取验证码后开始倒计时
                login_phone_checkCode.setText("剩余" + second.toString() + "s")
                second -= 1
                mHandler.sendEmptyMessageDelayed(102, 1000)
                if (second == 0) {
                    mHandler.removeMessages(102)
                    login_phone_checkCode.setText("重新获取")
                }
            }
            103 -> {
                Toast.makeText(this, "验证成功", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,SettingPwdActivity::class.java))
            }
            104 -> Toast.makeText(this, "短信发送失败", Toast.LENGTH_SHORT).show()
            105 -> Toast.makeText(this, "短信验证失败", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}
