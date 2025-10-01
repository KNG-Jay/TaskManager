import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.jvm

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSpring)
    alias(libs.plugins.kotlinx.serialization)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "com.taskmanager.server"
version = "0.0.1-SNAPSHOT"
description = "DB: MongoDB, BACKEND: SPRINGBOOT KMP, FRONTEND: REACT TYPESCRIPT, CONTAINERS: DOCKER KUBERNETES"

kotlin {
    jvm()

    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
        val commonTest by getting {
            dependencies {

            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.spring.boot.starter.data.mongodb)
                implementation(libs.spring.boot.starter.security)
                implementation(libs.spring.boot.starter.web)
                implementation(libs.mongodb.driver.bom)
                implementation(libs.mongodb.driver.kotlin.coroutine)
                implementation(libs.bson.kotlinx)
                implementation(libs.coil.compose)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines)
                //implementation(libs.jackson.module.kotlin)

                implementation(libs.kotlin.reflect)
                implementation(libs.springdoc.openapi.ui)
                implementation(libs.springdoc.openapi.kotlin)

                implementation(libs.spring.boot.devtools)

                implementation(libs.spring.boot.starter.test)
                implementation(libs.spring.security.test)
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.testJunit)
                //implementation(libs.junit)
                runtimeOnly(libs.junit.platform.launcher)
            }
        }
    }
}

tasks.withType<Test> {
	useJUnitPlatform()
}
