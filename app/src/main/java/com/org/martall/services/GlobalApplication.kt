package com.org.martall.services

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.org.martall.BuildConfig

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_KEY)
        // userInfoManager = MartAllUserInfoManager(applicationContext)
    }
}