package swaggy.com.moneysea.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_setting_pwd.*
import swaggy.com.moneysea.MainActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.utils.StatusBarHeightUtil

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
                 startActivity(Intent(this,MainActivity::class.java))
            }
            R.id.setting_password_button -> {
                //TODO：调用修改接口，跳转到主页
                changePwd()
            }
        }
    }

    private fun changePwd() {

    }
}
