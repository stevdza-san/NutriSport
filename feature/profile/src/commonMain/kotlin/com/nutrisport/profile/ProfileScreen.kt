package com.nutrisport.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nutrisport.shared.Surface
import com.nutrisport.shared.component.ProfileForm

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier
            .background(Surface)
            .systemBarsPadding()
    ) {
        ProfileForm(
            firstName = "Stefan",
            onFirstNameChange = {},
            lastName = "",
            onLastNameChange = {},
            email = "",
            city = "",
            onCityChange = {},
            postalCode = null,
            onPostalCodeChange = {},
            address = "",
            onAddressChange = {},
            phoneNumber = null,
            onPhoneNumberChange = {}
        )
    }
}