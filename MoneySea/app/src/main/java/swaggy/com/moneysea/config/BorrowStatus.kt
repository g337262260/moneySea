package swaggy.com.moneysea.config

/**
 * Writer：GuoWei_aoj on 2018/5/8 0008 14:14
 * description:
 */

class BorrowStatus {


    companion object {
        val CAN_BORROW: Int = 0
        val VERIFY: Int = 1
        val VERIFY_END: Int = 2
        val LOAN: Int = 3
        val LOAN_END: Int = 4
        val TRADE_FINISH :Int = 5
    }
}