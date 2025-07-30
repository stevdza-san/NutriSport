package com.nutrisport.shared.util

actual fun threadSleep(millis: Long) {
    Thread.sleep(millis)
}