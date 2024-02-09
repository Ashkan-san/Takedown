// Project Gradle File
// Hier werden die Plugins resolved, aber NICHT applied (apply false), damit diese Modulspezifisch applied werden können

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.jetbrainsCompose) apply false

    id("org.jetbrains.kotlin.android") version "1.9.20" apply false

}

buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}