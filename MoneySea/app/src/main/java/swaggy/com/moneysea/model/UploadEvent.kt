package swaggy.com.moneysea.model

/**
 * Writer：GuoWei_aoj on 2018/5/3 0003 15:35
 * description:
 */
class UploadEvent(var type: Int, var flag: Int,var path:String){
    companion object {
        val TYPE_IMAGE :Int = 0
        val TYPE_ICON :Int = 1

        val FLAG_CARD1 :Int = 1
        val FLAG_CARD2 :Int = 2
        val FLAG_CARD3 :Int = 3
    }
}
