package swaggy.com.moneysea.login

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import cn.smssdk.SMSSDK
import kotlinx.android.synthetic.main.activity_change_pwd.*
import kotlinx.android.synthetic.main.activity_login_by_phone.*
import swaggy.com.moneysea.R
import swaggy.com.moneysea.utils.StatusBarHeightUtil

class ChangePwdActivity : Activity(), View.OnClickListener,Handler.Callback {


    private val mHandler = Handler(this)

    //接收验证码倒计时60s
    private var second = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_change_pwd)
        initView()
    }

    private fun initView() {
        change_pwd_back.setOnClickListener(this)
        change_pwd_button.setOnClickListener(this)
        change_pwd_checkCode.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.change_pwd_back -> {
                finish()
            }
            R.id.change_pwd_checkCode -> {
                var phone = change_pwd_phone.text.toString()
                if (phone.length in 1..11) {
                    SMSSDK.getVerificationCode("86", phone)
                    mHandler.sendEmptyMessage(102)
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
                var new_pwd = change_pwd_new.text.toString()
                var again_pwd = change_pwd_again.text.toString()
                savePwd()

            }
            104 -> Toast.makeText(this, "短信发送失败", Toast.LENGTH_SHORT).show()
            105 -> Toast.makeText(this, "短信验证失败", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    /**
     * 调用接口,保存密码
     */
    private fun savePwd() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null);
    }

}
