package swaggy.com.moneysea.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_setting_pwd.*
import swaggy.com.moneysea.MainActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.utils.SharedPreUtils
import swaggy.com.moneysea.utils.StatusBarHeightUtil
import java.util.*

class SettingPwdActivity : Activity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_setting_pwd)
        initView()
    }

    private fun initView() {
        setting_pwd_skip.setOnClickListener(this)
        setting_password_button.setOnClickListener(this)
        setting_password_again.addTextChangedListener(textWatcher)
    }
    private val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length >= 6) {
                setting_password_button.setBackground(resources.getDrawable(R.mipmap.button_success))
            }
        }
        override fun afterTextChanged(s: Editable) {

        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
             R.id.setting_pwd_skip-> {
                 startActivity(Intent(this,AuthActivity::class.java))
            }
            R.id.setting_password_button -> {
                //TODO：调用修改接口，跳转到主页
                changePwd()
            }
        }
    }

    private fun changePwd() {
        val pwd = setting_password!!.text.toString()
        val again = setting_password_again!!.text.toString()
        if (!pwd.equals(again)) {
            Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT)
        }
        var phone = SharedPreUtils.getString(this, "userName", "")
        val stringMap = HashMap<String, String>()
        stringMap.put("mobile",phone)
        stringMap.put("password",pwd)
        OkGo.post<WSResult<String>>(HttpContants.REGISTER)
                .cacheMode(CacheMode.NO_CACHE)
                .params(stringMap)
                .execute(object : JsonCallback<WSResult<String>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<String>>?) {
                        when (response!!.body().code) {
                            ErrorCode.SUCCESS -> {
                                //注册成功,跳转到验证
                                SharedPreUtils.putBoolean(this@SettingPwdActivity,"isLogin",true)
                                startActivity(Intent(this@SettingPwdActivity,MainActivity::class.java))
                                finish()
                            }

                        }

                    }

                })
    }
}
