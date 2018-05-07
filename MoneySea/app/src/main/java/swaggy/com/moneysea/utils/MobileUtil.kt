package swaggy.com.moneysea.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Writer：GuoWei_aoj on 2018/5/7 0007 16:17
 * description:
 */
class MobileUtil{
    /**
     * 手机号验证
     * @author ：shijing
     * 2016年12月5日下午4:34:46
     * @param  str
     * @return 验证通过返回true
     */
    fun isMobile(str: String): Boolean {
        var p: Pattern? = null
        var m: Matcher? = null
        var b = false
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$") // 验证手机号
        m = p!!.matcher(str)
        b = m!!.matches()
        return b
    }

    /**
     * 电话号码验证
     * @author ：shijing
     * 2016年12月5日下午4:34:21
     * @param  str
     * @return 验证通过返回true
     */
    fun isPhone(str: String): Boolean {
        var p1: Pattern? = null
        var p2: Pattern? = null
        var m: Matcher? = null
        var b = false
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$")  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$")         // 验证没有区号的
        if (str.length > 9) {
            m = p1!!.matcher(str)
            b = m!!.matches()
        } else {
            m = p2!!.matcher(str)
            b = m!!.matches()
        }
        return b
    }
}