package com.taskmanager

class JvmPlatform: Platform {
    override val name: String = "JVM"
}

actual fun getPlatform(): Platform = JvmPlatform()