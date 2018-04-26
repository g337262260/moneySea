package swaggy.com.moneysea.me


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_mine.*
import swaggy.com.moneysea.R
import swaggy.com.moneysea.about.AboutUsActivity
import swaggy.com.moneysea.info.PerfectInfoActivity
import swaggy.com.moneysea.login.ChangePwdActivity
import swaggy.com.moneysea.utils.PhotoUtil

class MineFragment : Fragment(), View.OnClickListener {

    private var isVisiable: Boolean = false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater!!.inflate(R.layout.fragment_mine, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        mine_modify_pwd.setOnClickListener(this)
        mine_perfect_info.setOnClickListener(this)
        mine_borrow_progress.setOnClickListener(this)
        mine_about_us.setOnClickListener(this)
        mine_icon.setOnClickListener { pickImage() }
    }

    private fun pickImage() {

        PhotoUtil.pickPhoto(activity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mine_modify_pwd -> {
                startActivity(Intent(activity, ChangePwdActivity::class.java))
            }
            R.id.mine_perfect_info -> {
                startActivity(Intent(activity, PerfectInfoActivity::class.java))
            }
            R.id.mine_borrow_progress -> {
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
            R.id.mine_about_us -> {
                startActivity(Intent(activity, AboutUsActivity::class.java))
            }

        }
    }

}
