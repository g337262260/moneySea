package swaggy.com.moneysea.info

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_perfect_info.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.login.AuthActivity
import swaggy.com.moneysea.model.FinishEvent
import swaggy.com.moneysea.utils.SharedPreUtils
import swaggy.com.moneysea.utils.StatusBarHeightUtil
import java.util.*


class PerfectInfoActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_perfect_info)
        EventBus.getDefault().register(this)
        initView()
    }

    private fun initView() {
        info_back.setOnClickListener { finish() }
        perfect_button.setOnClickListener { commit() }
        info_bank_name.setOnClickListener {
            val builder = AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog)
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("选择一个银行")
            //    指定下拉列表的显示数据

            val banks = arrayOf("中国建设银行", "中国银行", "中国农业银行", "中国工商银行", "交通银行", "中国光大银行", "华夏银行", "其他银行")
            //    设置一个下拉的列表选择项
            builder.setItems(banks, DialogInterface.OnClickListener { dialog, which ->
                info_bank_name.text = banks[which]
            })

            builder.show()
        }
    }

    private fun commit() {
        val company = info_work_place!!.text.toString()
        val titles = info_work_job!!.text.toString()
        val address = info_work_address!!.text.toString()
        val phone = info_work_tel!!.text.toString()
        val name1 = info_contant_name!!.text.toString()
        val phone1 = info_contant_tel!!.text.toString()
        val name2 = info_contant_name2!!.text.toString()
        val phone2 = info_contant_tel2!!.text.toString()
        val name_real = info_bank_user!!.text.toString()
        val idCard = info_bank_id!!.text.toString()
        val bankName = info_bank_name!!.text.toString()
        val bankNumber = info_bank_card!!.text.toString()
        val alipay = info_other_ali!!.text.toString()
        val wechat = info_other_wechat!!.text.toString()
        val QQ = info_other_qq!!.text.toString()
        val taobao = info_other_taobao!!.text.toString()
        val jd = info_other_jingdong!!.text.toString()

        val stringMap = HashMap<String, String>()
        stringMap.put("company", company)
        stringMap.put("titles", titles)
        stringMap.put("address", address)
        stringMap.put("phone", phone)
        stringMap.put("name1", name1)
        stringMap.put("phone1", phone1)
        stringMap.put("name2", name2)
        stringMap.put("phone2", phone2)
        stringMap.put("name_real", name_real)
        stringMap.put("idCard", idCard)
        stringMap.put("bankName", bankName)
        stringMap.put("bankNumber", bankNumber)
        stringMap.put("alipay", alipay)
        stringMap.put("wechat", wechat)
        stringMap.put("QQ", QQ)
        stringMap.put("taobao", taobao)
        stringMap.put("jd", jd)

        val gson = Gson()
        var data = gson.toJson(stringMap).toString()
        Log.e("data", data)

        var mobile = SharedPreUtils.getString(this, "userName", "")

        val params = HashMap<String, String>()
        params.put("mobile", mobile)
        params.put("data", data)
        OkGo.post<WSResult<String>>(HttpContants.COMPLETE)
                .cacheMode(CacheMode.NO_CACHE)
                .params(params)
                .execute(object : JsonCallback<WSResult<String>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<String>>?) {
                        var message = response!!.body().msg
                        when (response!!.body().code) {
                            ErrorCode.SUCCESS -> {
                                //注册成功,跳转到验证
                                startActivity(Intent(this@PerfectInfoActivity, AuthActivity::class.java))
                                finish()
                            }
                        }
                        Toast.makeText(this@PerfectInfoActivity,message,Toast.LENGTH_SHORT).show()

                    }

                })
    }

    @Subscribe
    fun onMessageEvent(event: FinishEvent) {
        if (event.event == 1) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
