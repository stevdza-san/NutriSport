package com.nutrisport.di

import com.nutrisport.auth.AuthViewModel
import com.nutrisport.data.AdminRepositoryImpl
import com.nutrisport.home.HomeGraphViewModel
import com.nutrisport.profile.ProfileViewModel
import com.nutrisport.admin_panel.AdminPanelViewModel
import com.nutrisport.manage_product.ManageProductViewModel
import com.nutrisport.data.CustomerRepositoryImpl
import com.nutrisport.data.domain.AdminRepository
import com.nutrisport.data.domain.CustomerRepository
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::AdminPanelViewModel)
}

expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, targetModule)
    }
}