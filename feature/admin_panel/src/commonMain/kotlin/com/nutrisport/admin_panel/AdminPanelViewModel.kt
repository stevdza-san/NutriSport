package com.nutrisport.admin_panel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrisport.data.domain.AdminRepository
import com.nutrisport.shared.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AdminPanelViewModel(
    private val adminRepository: AdminRepository,
) : ViewModel() {
    val products = adminRepository.readLastTenProducts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )
}