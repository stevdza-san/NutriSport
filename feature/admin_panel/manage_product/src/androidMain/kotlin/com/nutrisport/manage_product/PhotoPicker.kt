package com.nutrisport.manage_product

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.gitlive.firebase.storage.File

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PhotoPicker {
    private var openPhotoPicker = mutableStateOf(false)

    actual fun open() {
        openPhotoPicker.value = true
    }

    @Composable
    actual fun InitializePhotoPicker(
        onImageSelect: (File?) -> Unit,
    ) {
        val openPhotoPickerState by remember { openPhotoPicker }
        val pickMedia = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let { onImageSelect(File(it)) } ?: onImageSelect(null)
            openPhotoPicker.value = false
        }

        LaunchedEffect(openPhotoPickerState) {
            if (openPhotoPickerState) {
                pickMedia.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        }
    }
}