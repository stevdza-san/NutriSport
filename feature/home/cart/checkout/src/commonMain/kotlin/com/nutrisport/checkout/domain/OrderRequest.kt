package com.nutrisport.checkout.domain

import com.nutrisport.shared.Constants.CANCEL_URL
import com.nutrisport.shared.Constants.RETURN_URL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val intent: String = "CAPTURE",
    @SerialName("purchase_units")
    val purchaseUnits: List<PurchaseUnit>,
    @SerialName("payment_source")
    val paymentSource: PaymentSource = PaymentSource(),
)

@Serializable
data class PurchaseUnit(
    @SerialName("reference_id")
    val referenceId: String,
    val amount: Amount,
    val shipping: Shipping? = null,
)

@Serializable
data class Amount(
    @SerialName("currency_code")
    val currencyCode: String,
    val value: String,
)

@Serializable
data class Shipping(
    val name: Name,
    val address: ShippingAddress,
)

@Serializable
data class Name(
    @SerialName("full_name") val fullName: String,
)

@Serializable
data class ShippingAddress(
    @SerialName("address_line_1") val addressLine1: String,
    @SerialName("address_line_2") val addressLine2: String? = null,
    @SerialName("admin_area_2") val city: String,
    @SerialName("admin_area_1") val state: String,
    @SerialName("postal_code") val postalCode: String,
    @SerialName("country_code") val countryCode: String,
)

@Serializable
data class PaymentSource(
    val paypal: PayPal = PayPal()
)

@Serializable
data class PayPal(
    @SerialName("experience_context")
    val experienceContext: ExperienceContext = ExperienceContext(),
)

@Serializable
data class ExperienceContext(
    @SerialName("payment_method_preference")
    val paymentMethodPreference: String = "IMMEDIATE_PAYMENT_REQUIRED",
    @SerialName("brand_name")
    val brandName: String = "NutriSport",
    val locale: String = "en-US",
    @SerialName("landing_page")
    val landingPage: String = "LOGIN",
    @SerialName("shipping_preference")
    val shippingPreference: String = "SET_PROVIDED_ADDRESS",
//    @SerialName("shipping_preference")
//    val shippingPreference: String = "NO_SHIPPING",
    @SerialName("user_action")
    val userAction: String = "PAY_NOW",
    @SerialName("return_url")
    val returnUrl: String = RETURN_URL,
    @SerialName("cancel_url")
    val cancelUrl: String = CANCEL_URL
)

@Serializable
data class OrderResponse(
    val id: String,
    val status: String,
    val links: List<Link>,
)

@Serializable
data class Link(
    val href: String,
    val rel: String,
    val method: String,
)