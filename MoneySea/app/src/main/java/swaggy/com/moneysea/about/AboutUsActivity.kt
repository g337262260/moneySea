package swaggy.com.moneysea.about

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_about_us.*
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.model.AboutUs
import swaggy.com.moneysea.utils.StatusBarHeightUtil
import java.util.*

class AboutUsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_about_us)


        initView()
    }

    private fun initView() {

        about_us_back.setOnClickListener { finish() }

        initData()
    }

    private fun initData() {
        val stringMap = HashMap<String, String>()
        OkGo.post<WSResult<AboutUs>>(HttpContants.ABOUT_US)
                .cacheMode(CacheMode.NO_CACHE)
                .params(stringMap)
                .execute(object : JsonCallback<WSResult<AboutUs>>() {

                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<AboutUs>>?) {
                        val result = response!!.body().result
                        about_us_version.text = "版本号: ${result.app_ver}"
                        about_us_tel.text = "客服电话: ${result.app_phone}"
                        about_us_qq.text = "客服QQ: ${result.app_qq}"
                        about_us_wechat.text = "客服微信: ${result.app_wechat}"
                        about_us_address.text = "公司地址: ${result.address}"
                    }

                })
    }
}
