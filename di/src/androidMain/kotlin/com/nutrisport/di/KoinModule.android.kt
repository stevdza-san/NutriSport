package com.nutrisport.di

import com.nutrisport.manage_product.PhotoPicker
import org.koin.dsl.module

actual val targetModule = module {
    single<PhotoPicker> { PhotoPicker() }
}