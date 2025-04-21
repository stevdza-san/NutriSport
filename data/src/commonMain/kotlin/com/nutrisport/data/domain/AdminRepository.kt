package com.nutrisport.data.domain

import com.nutrisport.shared.domain.Product
import dev.gitlive.firebase.storage.File

interface AdminRepository {
    fun getCurrentUserId(): String?
    suspend fun createNewProduct(
        product: Product,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
    suspend fun uploadImageToStorage(file: File): String?
    suspend fun deleteImageFromStorage(
        downloadUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}