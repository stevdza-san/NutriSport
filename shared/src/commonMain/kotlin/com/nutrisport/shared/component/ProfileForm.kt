package com.nutrisport.shared.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nutrisport.shared.component.dialog.CountryPickerDialog
import com.nutrisport.shared.domain.Country

@Composable
fun ProfileForm(
    modifier: Modifier = Modifier,
    country: Country,
    onCountrySelect: (Country) -> Unit,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    city: String?,
    onCityChange: (String) -> Unit,
    postalCode: Int?,
    onPostalCodeChange: (Int?) -> Unit,
    address: String?,
    onAddressChange: (String) -> Unit,
    phoneNumber: String?,
    onPhoneNumberChange: (String) -> Unit,
) {
    var showCountryDialog by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = showCountryDialog
    ) {
        CountryPickerDialog(
            country = country,
            onDismiss = { showCountryDialog = false },
            onConfirmClick = { selectedCountry ->
                showCountryDialog = false
                onCountrySelect(selectedCountry)
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
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
            value = city ?: "",
            onValueChange = onCityChange,
            placeholder = "City",
            error = city?.length !in 3..50
        )
        CustomTextField(
            value = "${postalCode ?: ""}",
            onValueChange = { onPostalCodeChange(it.toIntOrNull()) },
            placeholder = "Postal Code",
            error = postalCode == null || postalCode.toString().length !in 3..8,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        CustomTextField(
            value = address ?: "",
            onValueChange = onAddressChange,
            placeholder = "Address",
            error = address?.length !in 3..50
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlertTextField(
                text = "+${country.dialCode}",
                icon = country.flag,
                onClick = { showCountryDialog = true }
            )
            Spacer(modifier = Modifier.width(12.dp))
            CustomTextField(
                value = phoneNumber ?: "",
                onValueChange = onPhoneNumberChange,
                placeholder = "Phone Number",
                error = phoneNumber.toString().length !in 5..30,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}