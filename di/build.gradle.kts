import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "di"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // implementation("io.kotzilla:kotzilla-sdk:1.2.0-Beta3")
            implementation("io.kotzilla:kotzilla-sdk-ktor3:1.2.0-Beta1")

            implementation(project(":feature:auth"))
            implementation(project(":feature:details"))
            implementation(project(":feature:home"))
            implementation(project(":feature:profile"))
            implementation(project(":feature:admin_panel"))
            implementation(project(":feature:admin_panel:manage_product"))
            implementation(project(":feature:home:products_overview"))
            implementation(project(":feature:home:cart"))
            implementation(project(":feature:home:cart:checkout"))
            implementation(project(":feature:home:categories:category_search"))
            implementation(project(":feature:payment_completed"))
            implementation(project(":data"))
            implementation(project(":shared"))
        }
    }
}

android {
    namespace = "com.nutrisport.di"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}