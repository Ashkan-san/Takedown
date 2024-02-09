plugins {
    kotlin("jvm")
    alias(libs.plugins.ktor)
}

group = "de.takedown"
version = "1.0.0"

application {
    // Entry Point
    mainClass.set("de.takedown.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback)

    implementation(libs.html.unit)
}