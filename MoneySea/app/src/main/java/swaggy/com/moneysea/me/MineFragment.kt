package swaggy.com.moneysea.me


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
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
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.EventBus
import swaggy.com.moneysea.CreditActivity
import swaggy.com.moneysea.R
import swaggy.com.moneysea.about.AboutUsActivity
import swaggy.com.moneysea.callback.JsonCallback
import swaggy.com.moneysea.callback.webref.WSResult
import swaggy.com.moneysea.config.BorrowStatus
import swaggy.com.moneysea.config.ErrorCode
import swaggy.com.moneysea.config.HttpContants
import swaggy.com.moneysea.login.AuthActivity
import swaggy.com.moneysea.login.ChangePwdActivity
import swaggy.com.moneysea.model.LogoutEvent
import swaggy.com.moneysea.model.UserInfo
import swaggy.com.moneysea.utils.SharedPreUtils
import java.util.*





class MineFragment : Fragment(), View.OnClickListener {

    private var isVisiable: Boolean = false

    private var phone :String = ""

    private var complete:Int = 0

    private var status:Int = 0


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater!!.inflate(R.layout.fragment_mine, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        initData()
    }

    private fun initData() {

        phone = SharedPreUtils.getString(activity, "userName", "")
        val stringMap = HashMap<String, String>()
        stringMap.put("mobile",phone)
        OkGo.post<WSResult<UserInfo>>(HttpContants.USER_INFO)
                .cacheMode(CacheMode.NO_CACHE)
                .params(stringMap)
                .execute(object : JsonCallback<WSResult<UserInfo>>() {
                    @SuppressLint("SetTextI18n", "ShowToast")
                    override fun onSuccess(response: Response<WSResult<UserInfo>>?) {
                        when (response!!.body().code) {
                            ErrorCode.SUCCESS ->{
                                var result = response.body().result
                                mine_account?.text = result.mobile
                                status = result.status
                                if (result.status>=2) {
                                    //审核通过
                                    if (isAdded()) {
                                        val drawable = resources.getDrawable(R.mipmap.pass)
                                        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                                        mine_shenhe.setCompoundDrawables(null, drawable, null, null)
                                        mine_shenhe.text  = "已通过"
                                    }

                                }else {
                                    //审核未通过
                                    if(isAdded()){
                                        val drawable = resources.getDrawable(R.mipmap.pass_no)
                                        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                                        mine_shenhe.setCompoundDrawables(null, drawable, null, null)
                                        mine_shenhe.text  = "未通过"
                                    }

                                }

                                complete = result.complete


                            }
                        }

                    }

                })
    }

    private fun initView() {
        mine_modify_pwd.setOnClickListener(this)
        mine_perfect_info.setOnClickListener(this)
        mine_borrow_progress.setOnClickListener(this)
        mine_about_us.setOnClickListener(this)
        mine_can_use.setOnClickListener(this)
        mine_logout.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("是否退出登录")
                    .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, id ->
                        EventBus.getDefault().post(LogoutEvent(1))
                        dialog.dismiss()
                    })
                    .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
            builder.show()
        }

    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mine_modify_pwd -> {
                startActivity(Intent(activity, ChangePwdActivity::class.java))
            }
            R.id.mine_perfect_info -> {
//                startActivity(Intent(activity, PerfectInfoActivity::class.java))
                if (complete==1) {
                    Toast.makeText(activity,"信息已完善", Toast.LENGTH_SHORT).show()
                }else{
                    startActivity(Intent(activity, AuthActivity::class.java))

                }
            }
            R.id.mine_borrow_progress -> {
                if (status==0) {
                    Toast.makeText(activity,"您还未申请",Toast.LENGTH_SHORT).show()
                }else{
                    if (isVisiable) {
                        mine_borrow_progress.setImageDrawable(resources.getDrawable(R.mipmap.down))
                        mine_progress.visibility = View.GONE
                        isVisiable = false
                    } else {
                        mine_borrow_progress.setImageDrawable(resources.getDrawable(R.mipmap.up))
                        mine_progress.visibility = View.VISIBLE
                        isVisiable = true
                    }
                }



            }
            R.id.mine_about_us -> {
                startActivity(Intent(activity, AboutUsActivity::class.java))
            }
            R.id.mine_can_use -> {
                if (status>BorrowStatus.VERIFY){
                    startActivity(Intent(activity,CreditActivity::class.java))
                }else{
                    Toast.makeText(activity,"审核未通过",Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

}
