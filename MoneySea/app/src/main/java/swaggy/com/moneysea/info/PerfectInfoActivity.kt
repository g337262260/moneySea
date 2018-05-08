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
import swaggy.com.moneysea.MainActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.model.FinishEvent
import swaggy.com.moneysea.utils.SharedPreUtils
import swaggy.com.moneysea.utils.StatusBarHeightUtil


class PerfectInfoActivity : Activity() {


    private var params: HashMap<String, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_perfect_info)
        EventBus.getDefault().register(this)
        ininData()
        initView()
    }

    private fun ininData() {
        var intent = intent
        params = intent.getSerializableExtra("params") as HashMap<String, String>
        Log.e("params",params.toString())

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

        val name_real = info_bank_user!!.text.toString()
        val idCard = info_bank_id!!.text.toString()
        val bankName = info_bank_name!!.text.toString()
        val bankNumber = info_bank_card!!.text.toString()
        val alipay = info_other_ali!!.text.toString()
        val wechat = info_other_wechat!!.text.toString()
        val QQ = info_other_qq!!.text.toString()
        val taobao = info_other_taobao!!.text.toString()
        val jd = info_other_jingdong!!.text.toString()


        if (name_real.isEmpty()) {
            Toast.makeText(this,"请填写姓名",Toast.LENGTH_SHORT).show()
            return
        }
        if (idCard.isEmpty()) {
            Toast.makeText(this,"请填写姓名",Toast.LENGTH_SHORT).show()
            return
        }
        if (idCard.length!=18) {
            Toast.makeText(this,"请填写正确的身份证号",Toast.LENGTH_SHORT).show()
            return
        }
        if (bankNumber.isEmpty()) {
            Toast.makeText(this,"请填写银行卡号",Toast.LENGTH_SHORT).show()
            return
        }
        if (bankNumber.length!=19) {
            Toast.makeText(this,"请填写正确的银行卡号",Toast.LENGTH_SHORT).show()
            return
        }
        if (alipay.isEmpty()) {
            Toast.makeText(this,"请填写支付宝账号",Toast.LENGTH_SHORT).show()
            return
        }

        params!!.put("name_real", name_real)
        params!!.put("idCard", idCard)
        params!!.put("bankName", bankName)
        params!!.put("bankNumber", bankNumber)
        params!!.put("alipay", alipay)
        params!!.put("wechat", wechat)
        params!!.put("QQ", QQ)
        params!!.put("taobao", taobao)
        params!!.put("jd", jd)

        Log.e("params", params.toString())
        val gson = Gson()
        var data = gson.toJson(params).toString()
        Log.e("data", data)

        var mobile = SharedPreUtils.getString(this, "userName", "")

        val params1 = HashMap<String, String>()
        params1.put("mobile", mobile)
        params1.put("data", data)
        OkGo.post<WSResult<String>>(HttpContants.COMPLETE)
                .cacheMode(CacheMode.NO_CACHE)
                .params(params1)
                .execute(object : JsonCallback<WSResult<String>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<String>>?) {
                        var message = response!!.body().msg
                        when (response!!.body().code) {
                            ErrorCode.SUCCESS -> {
                                //完善成功,跳转到首页
                                startActivity(Intent(this@PerfectInfoActivity, MainActivity::class.java))
                                EventBus.getDefault().post(FinishEvent(2))
                                finish()
                            }
                        }
                        Toast.makeText(this@PerfectInfoActivity, message, Toast.LENGTH_SHORT).show()

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
