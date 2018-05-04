package swaggy.com.moneysea.config

/**
 * Writer：GuoWei_aoj on 2018/5/3 0003 10:07
 * description:
 */
class ErrorCode{
    companion object {
        val SUCCESS :Int = 200

        val PASSWORD_ERROR = 403

        val NO_REGISTER = 404

        val BORROW_SUCCESS = 1

        val BORROW_NO = 0
    }
}