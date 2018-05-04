package swaggy.com.moneysea.borrow


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lzy.okgo.OkGo
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.Response
import kotlinx.android.synthetic.main.fragment_borrow.*
import swaggy.com.moneysea.R
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.login.AuthActivity
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class BorrowFragment : Fragment() {


    private var isPerfect :Int = 0

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
        params.put("mobile", "13261561970")
        OkGo.post<WSResult<String>>(HttpContants.BORROW_STATUS)
                .cacheMode(CacheMode.NO_CACHE)
                .params(params)
                .execute(object : JsonCallback<WSResult<String>>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(response: Response<WSResult<String>>?) {
                        when (response!!.body().code) {
                            ErrorCode.BORROW_SUCCESS -> {
                                borrow_button!!.text = "审核中"
                                borrow_button!!.isClickable = false
                            }
                            ErrorCode.BORROW_NO -> {
                                borrow_button!!.text = "一键借贷"
                                borrow_button!!.isClickable = true
                            }

                        }

                    }

                })
    }

    private fun initView() {
        borrow_button.setOnClickListener {
            //判断状态，如果未完善，则跳转到完善信息页面
            if (isPerfect==0) {
                commit()

            }else{
                startActivity(Intent(activity,AuthActivity::class.java))
            }
        }
    }

    private fun commit() {
        val params = HashMap<String, String>()
        params.put("mobile", "13261561970")
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
                                borrow_button.text = "审核中"
                                borrow_button.isClickable = false
                            }

                        }

                    }

                })
    }

}
