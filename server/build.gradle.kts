plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSpring)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.shadowJar)
}

group = "com.taskmanager.server"
version = "0.0.1-SNAPSHOT"
description = "DB: MongoDB-Atlas, BACKEND: SPRINGBOOT-Kotlin, FRONTEND: VITE REACT-Ts, CONTAINERS: DOCKER MINIKUBE"

kotlin {
    @Suppress("OPT_IN_USAGE")
    jvm().mainRun {
        mainClass = "com.taskmanager.server.ServerApplicationKt"
    }

    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {}
        }
        val commonTest by getting {
            dependencies {}
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlin.std)
                implementation(libs.spring.boot.starter.data.mongodb)
                implementation(libs.spring.boot.starter.security)
                implementation(libs.spring.boot.starter.web)
                implementation(libs.mongodb.driver.bom)
                implementation(libs.mongodb.driver.kotlin.sync)
                implementation(libs.bson.kotlinx)
                implementation(libs.coil.compose)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlin.reflect)
                implementation(libs.springdoc.openapi.ui)
                implementation(libs.springdoc.openapi.kotlin)
                implementation(libs.spring.boot.devtools)
                implementation(libs.spring.boot.starter.test)
                implementation(libs.spring.security.test)
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.testJunit)
                runtimeOnly(libs.junit.platform.launcher)
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("server-jvm")
    archiveVersion.set("1.0")
    manifest {
        attributes["Main-Class"] = "com.taskmanager.server.ServerApplicationKt"
    }
}
