package swaggy.com.moneysea.borrow


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.fragment_borrow.*
import swaggy.com.moneysea.CreditActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.BorrowStatus
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.login.AuthActivity
import swaggy.com.moneysea.model.Status
import swaggy.com.moneysea.utils.SharedPreUtils
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class BorrowFragment : Fragment() {


    private var approve: Int = 0

    private var borrow: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_borrow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getstatus()
        initView()
    }

    private fun getstatus() {
        val params = HashMap<String, String>()
        var phone = SharedPreUtils.getString(activity, "userName", "")
        params.put("mobile", phone)
        OkGo.post<WSResult<Status>>(HttpContants.BORROW_STATUS)
                .cacheMode(CacheMode.NO_CACHE)
                .params(params)
                .execute(object : JsonCallback<WSResult<Status>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<Status>>?) {
                        when (response!!.body().code) {
                            ErrorCode.SUCCESS -> {
                                Log.e("borrowFragmeng", "success")
                                //请求成功
                                var result = response.body().result
                                borrow = result.borrow
                                Log.e("borrowFragmeng", "success" + borrow)
                                //放款进度
                                if (borrow == BorrowStatus.CAN_BORROW) {
                                    borrow_button?.isClickable = true
                                    borrow_button?.text = "一键贷款"
                                } else if (borrow == BorrowStatus.VERIFY) {
                                    borrow_button?.isClickable = false
                                    borrow_button?.text = "审核中"
                                    borrow_button?.setBackgroundDrawable(resources.getDrawable(R.drawable.button_login_shape))
                                    borrow_hint?.text  = "可用额度（元）"
                                    borrow_edu?.visibility = View.VISIBLE
                                    borrow_interest_container?.visibility = View.GONE
                                    borrow_edu?.text = result.lines.toString()
                                } else if (borrow == BorrowStatus.VERIFY_END) {
                                    borrow_button?.isClickable = true
                                    borrow_button?.text = "审核通过"
                                    borrow_button?.setBackgroundDrawable(resources.getDrawable(R.mipmap.button_success))
                                    borrow_hint?.text  = "可用额度（元）"
                                    borrow_edu?.visibility = View.VISIBLE
                                    borrow_interest_container?.visibility = View.GONE
                                    borrow_edu?.text = result.lines.toString()
                                } else if (borrow == BorrowStatus.LOAN) {
                                    borrow_button?.isClickable = false
                                    borrow_button?.text = "放款中"
                                    borrow_hint?.text  = "已贷款额度（元）"
                                    borrow_edu?.visibility = View.VISIBLE
                                    borrow_interest_container?.visibility = View.GONE
                                    borrow_edu?.text = result.lines.toString()
                                } else if (borrow == BorrowStatus.LOAN_END) {
                                    borrow_button?.isClickable = false
                                    borrow_button?.text = "放款结束"
                                    borrow_hint?.text  = "可用额度（元）"
                                    borrow_edu?.visibility = View.VISIBLE
                                    borrow_interest_container?.visibility = View.GONE
                                    borrow_edu?.text = result.lines.toString()
                                } else if (borrow == BorrowStatus.LOAN_END) {
                                    borrow_button?.isClickable = false
                                    borrow_button?.text = "贷款已还清"
                                }
                                //认证信息
                                approve = result.approve
                                //设置利息
                                borrow_interest?.text = result.interest + "%"
                            }


                        }

                    }

                })
    }

    private fun initView() {
        borrow_button.setOnClickListener {
            //判断状态，如果未完善，则跳转到完善信息页面
            if (approve == 0) {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("是否前往实名认证")
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, id ->
                            startActivity(Intent(activity, AuthActivity::class.java))
                            dialog.dismiss()
                        })
                        .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, id ->
                            dialog.cancel()
                        })
                builder.create()
                builder.show()
            } else {
                when (borrow) {
                    BorrowStatus.VERIFY_END -> {
                        startActivity(Intent(activity,CreditActivity::class.java))
                    }
                    BorrowStatus.CAN_BORROW -> {
                        commit()
                    }
                }

            }


        }
    }

    private fun commit() {
        val params = HashMap<String, String>()
        var phone = SharedPreUtils.getString(activity, "userName", "")
        params.put("mobile", phone)
        OkGo.post<WSResult<String>>(HttpContants.BORROW)
                .cacheMode(CacheMode.NO_CACHE)
                .params(params)
                .execute(object : JsonCallback<WSResult<String>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<String>>?) {
                        when (response!!.body().code) {
                            ErrorCode.SUCCESS -> {
                                //注册成功,跳转到验证
                                Toast.makeText(activity, "提交成功", Toast.LENGTH_SHORT).show()
                                borrow_button?.text = "审核中"
                                borrow_button?.isClickable = false
                            }

                        }

                    }

                })
    }

}
