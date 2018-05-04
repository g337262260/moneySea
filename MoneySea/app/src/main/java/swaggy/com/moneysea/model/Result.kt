package swaggy.com.moneysea.model

/**
 * Writer：GuoWei_aoj on 2018/5/3 0003 09:05
 * description:
 */


data class Result<T>(
		val code: String,
		val msg: String,
		val result: T

)
