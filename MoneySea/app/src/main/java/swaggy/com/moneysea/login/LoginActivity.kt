package swaggy.com.moneysea.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import swaggy.com.moneysea.MainActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.utils.SharedPreUtils
import swaggy.com.moneysea.utils.StatusBarHeightUtil

class LoginActivity : AppCompatActivity(), View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        login_button.setOnClickListener(this)
        login_forget.setOnClickListener(this)
        login_phone.setOnClickListener(this)
        login_edit_password.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length >= 6) {
                login_button.setBackground(resources.getDrawable(R.mipmap.button_success))
            }
        }

        override fun afterTextChanged(s: Editable) {

        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_button -> {
                login()
            }
            R.id.login_forget -> {
                startActivity(Intent(this, ChangePwdActivity::class.java))
            }
            R.id.login_phone -> {
                startActivity(Intent(this,LoginByPhoneActivity::class.java))
            }

        }
    }

    /**
     * 调用登录接口
     */
    private fun login() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        SharedPreUtils.putBoolean(this,"isLogin",true)
    }
}
