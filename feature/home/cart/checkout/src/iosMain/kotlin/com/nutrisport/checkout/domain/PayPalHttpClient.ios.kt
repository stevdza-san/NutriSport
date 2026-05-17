package com.nutrisport.checkout.domain

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

internal actual fun createPayPalHttpClient(): HttpClient =
    HttpClient(Darwin) {
        configurePayPalHttpClient()
    }
