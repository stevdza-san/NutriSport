package com.nutrisport.shared.util

import com.nutrisport.shared.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IntentHandler {
    private val _navigateTo = MutableStateFlow<Screen?>(null)
    val navigateTo: StateFlow<Screen?> = _navigateTo.asStateFlow()

    fun navigateToPaymentCompleted(
        isSuccess: Boolean?,
        error: String?,
    ) {
        _navigateTo.value = Screen.PaymentCompleted(
            isSuccess = isSuccess,
            error = error
        )
    }

    fun resetNavigation() {
        _navigateTo.value = null
    }
}