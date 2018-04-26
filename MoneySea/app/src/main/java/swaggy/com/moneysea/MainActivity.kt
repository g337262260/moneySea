package swaggy.com.moneysea

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import swaggy.com.moneysea.borrow.BorrowFragment
import swaggy.com.moneysea.me.MineFragment
import swaggy.com.moneysea.utils.StatusBarHeightUtil

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var borrowFragment : BorrowFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_main)
        initView()
    }



    fun initView(){
        borrowFragment = BorrowFragment()
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
                var mineFragment = MineFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, mineFragment).commitAllowingStateLoss()

            }
        }

    }

}
