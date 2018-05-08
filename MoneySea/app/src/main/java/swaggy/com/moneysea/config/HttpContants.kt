package swaggy.com.moneysea.config

/**
 * Writer：GuoWei_aoj on 2018/5/3 0003 09:12
 * description:
 */

class HttpContants {

    companion object {
        val HOST :String = " http://45.32.29.157/api/"

        val ABOUT_US :String = HOST+"aboutUs"

        val LOGIN :String = HOST +"login"

        val REGISTER :String = HOST+"register"

        val PERFECT : String = HOST+"prefect"

        val CHANGE_PWD :String = HOST +"setPass"

        val BORROW : String = HOST + "borrow"

        val BORROW_STATUS : String = HOST + "borrowStatus"

        val USER_INFO :String = HOST +"userInfo"

        val COMPLETE :String = HOST +"complete"

        val LOAN :String  = HOST +"loan"

        val LOANING :String  = HOST +"loaning"
    }
}