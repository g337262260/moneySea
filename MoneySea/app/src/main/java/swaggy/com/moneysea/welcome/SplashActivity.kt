package swaggy.com.moneysea.welcome

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import swaggy.com.moneysea.MainActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.login.LoginActivity
import swaggy.com.moneysea.utils.SharedPreUtils
import swaggy.com.moneysea.utils.StatusBarHeightUtil

class SplashActivity : AppCompatActivity(),Handler.Callback{


    // 将常量放入这里
    companion object {

        // 正常跳转到登录界面 常量 防止以后增加业务逻辑
        val MSG_LAUNCH : Int = 0

        // 延时时间
        val SLEEP_TIME:Long = 2000
    }

    private var mHandler :Handler = Handler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_welcome)
        init()

    }

    private fun init() {
        mHandler.sendEmptyMessageDelayed(MSG_LAUNCH, SLEEP_TIME)


    }
    override fun handleMessage(msg: Message?): Boolean {

        when (msg?.what) {
            MSG_LAUNCH -> {
                var isLogin = SharedPreUtils.getBoolean(this, "isLogin", false)
                if (isLogin) {
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    startActivity(Intent(this,LoginActivity::class.java))
                }
                finish()
            }

        }

        return false
    }


}
