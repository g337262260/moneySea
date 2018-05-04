package swaggy.com.moneysea

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import swaggy.com.moneysea.borrow.BorrowFragment
import swaggy.com.moneysea.login.LoginActivity
import swaggy.com.moneysea.me.MineFragment
import swaggy.com.moneysea.model.LogoutEvent
import swaggy.com.moneysea.utils.SharedPreUtils
import swaggy.com.moneysea.utils.StatusBarHeightUtil

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var borrowFragment : BorrowFragment? = null
    var mineFragment : MineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        initView()
    }

    fun initView(){
        borrowFragment = BorrowFragment()
        mineFragment = MineFragment()

        supportFragmentManager.beginTransaction().replace(R.id.main_fragment, borrowFragment).commitAllowingStateLoss()
        main_borrow.setOnClickListener(this)
        main_mine.setOnClickListener(this)
        main_borrow.setChecked(true)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.main_borrow -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, borrowFragment).commitAllowingStateLoss()
            }
            R.id.main_mine -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, mineFragment).commitAllowingStateLoss()

            }
        }

    }

    @Subscribe
    fun onMessageEvent(event: LogoutEvent) {
        if (event.event==1) {
            SharedPreUtils.putBoolean(this,"isLogin",false)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
