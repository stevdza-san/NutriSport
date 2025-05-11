package com.stevdza_san.nutrisport

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nutrisport.shared.util.IntentHandler
import com.nutrisport.shared.util.PreferencesRepository
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

//    private val intentHandler: IntentHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri = intent.data

        val isSuccess = uri?.getQueryParameter("success")
        val isCancelled = uri?.getQueryParameter("cancel")
        val token = uri?.getQueryParameter("token")

        PreferencesRepository.savePayPalData(
            isSuccess = isSuccess?.toBooleanStrictOrNull(),
            error = if (isCancelled == "null") null
            else "Payment has been canceled.",
            token = token
        )
//        intentHandler.navigateToPaymentCompleted(
//            isSuccess = isSuccess?.toBooleanStrictOrNull(),
//            error = if (isCancelled == "null") null
//            else "Payment has been canceled.",
//            token = token
//        )
    }
}