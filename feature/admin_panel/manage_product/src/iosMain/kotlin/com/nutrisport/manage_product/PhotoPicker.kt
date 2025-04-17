package com.nutrisport.manage_product

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.gitlive.firebase.storage.File
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerImageURL
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationController
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UITabBarController
import platform.UIKit.UIViewController
import platform.darwin.NSObject

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PhotoPicker {
    private var openPhotoPicker = mutableStateOf(false)

    actual fun open() {
        openPhotoPicker.value = true
    }

    @Composable
    actual fun InitializePhotoPicker(
        onImageSelect: (File?) -> Unit
    ) {
        val openPhotoPickerState by remember { openPhotoPicker }

        LaunchedEffect(openPhotoPickerState) {
            if (openPhotoPickerState) {
                val viewController = getCurrentViewController()
                val picker = UIImagePickerController().apply {
                    sourceType =
                        UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
                    mediaTypes = listOf("public.image", "public.heif")
                    delegate = PickerDelegate(
                        callback = { file ->
                            onImageSelect(file)
                            openPhotoPicker.value = false
                        }
                    )
                }

                viewController?.presentViewController(
                    picker, animated = true, completion = null
                )
            }
        }
    }

    private fun getCurrentViewController(): UIViewController? {
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        return findTopViewController(rootViewController)
    }

    private fun findTopViewController(viewController: UIViewController?): UIViewController? {
        return when (viewController) {
            is UINavigationController -> findTopViewController(viewController.visibleViewController)
            is UITabBarController -> findTopViewController(viewController.selectedViewController)
            is UIViewController -> {
                viewController.presentedViewController?.let { findTopViewController(it) }
                    ?: viewController
            }

            else -> viewController
        }
    }

    // Delegate class implementing the necessary protocols
    private class PickerDelegate(private val callback: (File?) -> Unit) : NSObject(),
        UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {

        // Override the method to handle the media picked by the user
        override fun imagePickerController(
            picker: UIImagePickerController,
            didFinishPickingMediaWithInfo: Map<Any?, *>
        ) {
            // Extract the URL of the picked media
            val url = didFinishPickingMediaWithInfo[UIImagePickerControllerImageURL] as? NSURL
            if (url != null) callback(File(url))
            else callback(null)
            picker.dismissViewControllerAnimated(true, completion = null)
        }

        // Override the method to handle the cancellation of the picker
        override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
            callback(null)
            picker.dismissViewControllerAnimated(true, completion = null)
        }
    }
}