package swaggy.com.moneysea.info

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_perfect_info1.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import swaggy.com.moneysea.R
import swaggy.com.moneysea.model.FinishEvent
import swaggy.com.moneysea.utils.MobileUtil
import swaggy.com.moneysea.utils.StatusBarHeightUtil
import java.util.*

class PerfectInfo1Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_perfect_info1)
        EventBus.getDefault().register(this)
        initView()
    }

    private fun initView() {
        info1_back.setOnClickListener { finish() }
        info1_perfect_button.setOnClickListener {
            commit()
        }
        val builder = AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog)
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("选择与你的关系")
        //    指定下拉列表的显示数据

        val banks = arrayOf("父母", "兄弟", "同事", "同学", "子女", "其他")
        //    设置一个下拉的列表选择项


        info1_contant_reletion2.setOnClickListener {
            builder.setItems(banks, DialogInterface.OnClickListener { dialog, which ->
                info1_contant_reletion2.text = banks[which]
            })

            builder.show()
        }
        info1_contant_reletion1.setOnClickListener {
            builder.setItems(banks, DialogInterface.OnClickListener { dialog, which ->
                info1_contant_reletion1.text = banks[which]
            })

            builder.show()
        }

    }

    private fun commit() {
        var mobileUtil = MobileUtil()

        val company = info1_work_place!!.text.toString()
        val titles = info1_work_job!!.text.toString()
        val address = info1_work_address!!.text.toString()
        val phone = info1_work_tel!!.text.toString()
        val name1 = info1_contant_name!!.text.toString()
        val phone1 = info1_contant_tel!!.text.toString()
        val name2 = info1_contant_name2!!.text.toString()
        val phone2 = info1_contant_tel2!!.text.toString()
        val reletion1 = info1_contant_reletion1!!.text.toString()
        val reletion2 = info1_contant_reletion2!!.text.toString()

        if (company.isEmpty()) {
            Toast.makeText(this,"请填写工作单位",Toast.LENGTH_SHORT).show()
            return
        }
        if (titles.isEmpty()) {
            Toast.makeText(this,"请填写职位",Toast.LENGTH_SHORT).show()
            return
        }
        if (address.isEmpty()) {
            Toast.makeText(this,"请填写单位地址",Toast.LENGTH_SHORT).show()
            return
        }
        if (phone.isEmpty()) {
            Toast.makeText(this,"请填写公司座机号",Toast.LENGTH_SHORT).show()
            return
        }

        if (name1.isEmpty()) {
            Toast.makeText(this,"请填写联系人姓名",Toast.LENGTH_SHORT).show()
            return
        }
        if (phone1.isEmpty()) {
            Toast.makeText(this,"请填写联系人电话",Toast.LENGTH_SHORT).show()
            return
        }
        if (reletion1.isEmpty()) {
            Toast.makeText(this,"请选择联系人关系",Toast.LENGTH_SHORT).show()
            return
        }
        if (name2.isEmpty()) {
            Toast.makeText(this,"请填写联系人姓名",Toast.LENGTH_SHORT).show()
            return
        }
        if (phone2.isEmpty()) {
            Toast.makeText(this,"请填写联系人电话",Toast.LENGTH_SHORT).show()
            return
        }
        if (reletion2.isEmpty()) {
            Toast.makeText(this,"请选择联系人关系",Toast.LENGTH_SHORT).show()
            return
        }
        val stringMap = HashMap<String, String>()
        stringMap.put("company", company)
        stringMap.put("titles", titles)
        stringMap.put("address", address)
        stringMap.put("phone", phone)
        stringMap.put("name1", name1)
        stringMap.put("relation1", reletion1)
        stringMap.put("phone1", phone1)
        stringMap.put("name2", name2)
        stringMap.put("phone2", phone2)
        stringMap.put("relation2", reletion2)
        Log.e("params", stringMap.toString())

        var intent = Intent(this, PerfectInfoActivity::class.java)
        intent.putExtra("params", stringMap)
        startActivity(intent)
    }

    @Subscribe
    fun onMessageEvent(event: FinishEvent) {
        if (event.event == 2) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
