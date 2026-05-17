package com.stevdza_san.nutrisport

import androidx.compose.ui.window.ComposeUIViewController
import com.nutrisport.di.initializeKoin
import com.nutrisport.shared.util.PreferencesRepository

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }

fun savePayPalData(
    isSuccess: Boolean?,
    error: String?,
    token: String?,
) {
    PreferencesRepository.savePayPalData(
        isSuccess = isSuccess,
        error = error,
        token = token,
    )
}
