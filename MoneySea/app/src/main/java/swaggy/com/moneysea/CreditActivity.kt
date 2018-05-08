package swaggy.com.moneysea

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.activity_credit.*
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.BorrowStatus
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.model.Loan
import swaggy.com.moneysea.utils.SharedPreUtils
import swaggy.com.moneysea.utils.StatusBarHeightUtil
import java.util.*

class CreditActivity : Activity(), View.OnClickListener {

    private var mobile :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_credit)
        mobile = SharedPreUtils.getString(this, "userName", "")
        initData()
        initView()
    }

    private fun initData() {
        //获取数据
        val stringMap = HashMap<String, String>()
        stringMap.put("mobile",mobile)
        OkGo.post<WSResult<Loan>>(HttpContants.LOAN)
                .cacheMode(CacheMode.NO_CACHE)
                .params(stringMap)
                .execute(object : JsonCallback<WSResult<Loan>>() {

                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<Loan>>?) {
                        val loan = response!!.body().result
                        credit_interest.text = loan.interest
                        credit_back_date.text = "${loan.month} 个月"
                        credit_payment.text = loan.type
                        credit_lines.text = "${loan.lines}"

                        when (loan.status) {
                            BorrowStatus.VERIFY_END -> {
                                credit_commit?.text = "确认借贷"
                                credit_commit?.isClickable = true
                            }
                            BorrowStatus.LOAN -> {
                                credit_commit?.text = "放款中"
                                credit_commit?.isClickable = false
                            }
                            BorrowStatus.LOAN_END -> {
                                credit_commit?.text = "放款结束"
                                credit_commit?.isClickable = false
                            }
                        }
                    }

                })
    }

    private fun initView() {
        credit_back.setOnClickListener(this)
        credit_commit.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.credit_back -> {
                finish()
            }
            R.id.credit_commit -> {
                commit()
            }
        }
    }

    private fun commit() {
        //TODO:调用确认借贷的接口
        credit_commit.isClickable = false
        val stringMap = HashMap<String, String>()
        stringMap.put("mobile",mobile)
        OkGo.post<WSResult<String>>(HttpContants.LOANING)
                .cacheMode(CacheMode.NO_CACHE)
                .params(stringMap)
                .execute(object : JsonCallback<WSResult<String>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<String>>?) {
                        if (response!!.body().code==ErrorCode.SUCCESS) {
                            credit_commit?.text = "放款中"
                            credit_commit?.isClickable = false
                        }
                        Toast.makeText(this@CreditActivity,response.body().msg,Toast.LENGTH_SHORT).show()
                    }

                })
    }
}
