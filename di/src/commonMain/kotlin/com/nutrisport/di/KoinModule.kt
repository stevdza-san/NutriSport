package com.nutrisport.di

import com.nutrisport.auth.AuthViewModel
import com.nutrisport.data.AdminRepositoryImpl
import com.nutrisport.home.HomeGraphViewModel
import com.nutrisport.profile.ProfileViewModel
import com.nutrisport.admin_panel.AdminPanelViewModel
import com.nutrisport.details.DetailsViewModel
import com.nutrisport.category_search.CategorySearchViewModel
import com.nutrisport.checkout.CheckoutViewModel
import com.nutrisport.cart.CartViewModel
import com.nutrisport.checkout.domain.PaypalApi
import com.nutrisport.manage_product.ManageProductViewModel
import com.nutrisport.products_overview.ProductsOverviewViewModel
import com.nutrisport.data.CustomerRepositoryImpl
import com.nutrisport.data.OrderRepositoryImpl
import com.nutrisport.data.ProductRepositoryImpl
import com.nutrisport.data.domain.AdminRepository
import com.nutrisport.data.domain.CustomerRepository
import com.nutrisport.data.domain.OrderRepository
import com.nutrisport.data.domain.ProductRepository
import com.nutrisport.shared.util.IntentHandler
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<AdminRepository> { AdminRepositoryImpl() }
    single<ProductRepository> { ProductRepositoryImpl() }
    single<OrderRepository> { OrderRepositoryImpl(get()) }
    single<IntentHandler> { IntentHandler() }
    single<PaypalApi> { PaypalApi() }
    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeGraphViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ManageProductViewModel)
    viewModelOf(::AdminPanelViewModel)
    viewModelOf(::ProductsOverviewViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::CategorySearchViewModel)
    viewModelOf(::CheckoutViewModel)
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