package com.nutrisport.checkout.domain

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

internal actual fun createPayPalHttpClient(): HttpClient =
    HttpClient(Android) {
        configurePayPalHttpClient()
    }
