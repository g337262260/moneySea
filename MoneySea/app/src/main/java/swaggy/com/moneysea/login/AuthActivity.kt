package swaggy.com.moneysea.login

import android.app.Activity
import android.os.Bundle
import swaggy.com.moneysea.R
import swaggy.com.moneysea.utils.StatusBarHeightUtil

class AuthActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarHeightUtil.transparencyBar(this)
        setContentView(R.layout.activity_auth)
    }
}
