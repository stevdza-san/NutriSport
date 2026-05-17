rootProject.name = "NutriSport"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}
include(":composeApp")
include(":navigation")
include(":data")
include(":di")
include(":shared")
include(":feature:admin_panel")
include(":feature:admin_panel:manage_product")
include(":feature:details")
include(":feature:home:cart")
include(":feature:home:cart:checkout")
include(":feature:home:categories")
include(":feature:home:categories:category_search")
include(":feature:home:products_overview")
include(":feature:payment_completed")
include(":feature:home")
include(":feature:profile")
include(":feature:auth")
