package swaggy.com.moneysea.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_login.*
import swaggy.com.moneysea.MainActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.utils.SharedPreUtils
import swaggy.com.moneysea.utils.StatusBarHeightUtil
import java.util.*

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
                val intent = Intent(this, ChangePwdActivity::class.java)
                intent.putExtra("flag",1)
                startActivity(intent)
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

        val phone = login_edit_phone!!.text.toString()
        val pwd = login_edit_password!!.text.toString()

        val stringMap = HashMap<String, String>()
        stringMap.put("mobile",phone)
        stringMap.put("password",pwd)
        OkGo.post<WSResult<String>>(HttpContants.LOGIN)
                .cacheMode(CacheMode.NO_CACHE)
                .params(stringMap)
                .execute(object : JsonCallback<WSResult<String>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<String>>?) {
                        when (response!!.body().code) {
                            ErrorCode.SUCCESS -> {
                                success()
                            }
                            ErrorCode.PASSWORD_ERROR -> {
                                Toast.makeText(this@LoginActivity,"密码错误",Toast.LENGTH_SHORT).show()
                            }
                            ErrorCode.NO_REGISTER -> {
                                Toast.makeText(this@LoginActivity,"用户未注册",Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                })

    }

    private fun success(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        SharedPreUtils.putBoolean(this,"isLogin",true)
        SharedPreUtils.putString(this,"userName",login_edit_phone!!.text.toString())
    }
}
