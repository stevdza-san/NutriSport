package com.nutrisport.shared.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileForm(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    city: String,
    onCityChange: (String) -> Unit,
    postalCode: Int?,
    onPostalCodeChange: (Int?) -> Unit,
    address: String,
    onAddressChange: (String) -> Unit,
    phoneNumber: String?,
    onPhoneNumberChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            )
            .verticalScroll(state = rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            placeholder = "First Name",
            error = firstName.length !in 3..50
        )
        CustomTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            placeholder = "Last Name",
            error = lastName.length !in 3..50
        )
        CustomTextField(
            value = email,
            onValueChange = {},
            placeholder = "Email",
            enabled = false
        )
        CustomTextField(
            value = city,
            onValueChange = onCityChange,
            placeholder = "City",
            error = city.length !in 3..50
        )
        CustomTextField(
            value = "${postalCode ?: ""}",
            onValueChange = { onPostalCodeChange(it.toIntOrNull()) },
            placeholder = "Postal Code",
            error = postalCode.toString().length !in 3..8
        )
        CustomTextField(
            value = address,
            onValueChange = onAddressChange,
            placeholder = "Address",
            error = address.length !in 3..50
        )
        CustomTextField(
            value = phoneNumber ?: "",
            onValueChange = onPhoneNumberChange,
            placeholder = "Phone Number",
            error = phoneNumber.toString().length !in 5..30
        )
    }
}