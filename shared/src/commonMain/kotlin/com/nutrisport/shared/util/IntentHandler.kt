package com.nutrisport.shared.util

import com.nutrisport.shared.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IntentHandler {
    private val _navigateTo = MutableStateFlow<Screen?>(null)
    val navigateTo: StateFlow<Screen?> = _navigateTo.asStateFlow()

    fun navigateToPaymentCompleted(
        isSuccess: Boolean?,
        error: String?,
        token: String?,
    ) {
        _navigateTo.value = Screen.PaymentCompleted(
            isSuccess = isSuccess,
            error = error,
            token = token
        )
    }

    fun resetNavigation() {
        _navigateTo.value = null
    }
}

class IntentHandlerHelper : KoinComponent {
    private val intentHandler: IntentHandler by inject()

    fun navigateToPaymentCompleted(
        isSuccess: Boolean?,
        error: String?,
        token: String?,
    ) {
        intentHandler.navigateToPaymentCompleted(
            isSuccess = isSuccess,
            error = error,
            token = token
        )
    }
}