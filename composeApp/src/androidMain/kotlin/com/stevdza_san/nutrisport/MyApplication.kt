package com.stevdza_san.nutrisport

import android.app.Application
import com.nutrisport.di.initializeKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@MyApplication)
                analytics {
                    setApiKey("ktz-sdk-c1FNa8mI5OXXoHZKuzjFQB9L-I3UztK4-KREeCWwBwU") // Available in kotzilla.json
                    setVersion("1.0.1")
                }
            }
        )
        Firebase.initialize(context = this)
    }
}