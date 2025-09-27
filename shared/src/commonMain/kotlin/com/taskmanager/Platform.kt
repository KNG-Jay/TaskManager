package com.taskmanager

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform