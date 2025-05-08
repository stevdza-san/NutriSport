package com.nutrisport.checkout.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaypalTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
)
