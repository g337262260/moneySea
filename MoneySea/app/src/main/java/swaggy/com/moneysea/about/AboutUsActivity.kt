package swaggy.com.moneysea.about

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about_us.*
import swaggy.com.moneysea.R
import swaggy.com.moneysea.utils.StatusBarHeightUtil

class AboutUsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_about_us)


        initView()
    }

    private fun initView() {

        about_us_back.setOnClickListener { finish() }

    }
}
