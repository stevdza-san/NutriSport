package com.nutrisport.data.domain

import com.nutrisport.shared.domain.Product
import com.nutrisport.shared.util.RequestState
import dev.gitlive.firebase.storage.File
import kotlinx.coroutines.flow.Flow

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
    fun readLastTenProducts(): Flow<RequestState<List<Product>>>
}