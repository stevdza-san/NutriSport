package com.nutrisport.di

import com.nutrisport.auth.AuthViewModel
import com.nutrisport.home.HomeGraphViewModel
import com.nutrisport.profile.ProfileViewModel
import com.nutrisport.data.CustomerRepositoryImpl
import com.nutrisport.data.domain.CustomerRepository
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}