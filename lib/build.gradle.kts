plugins {
    kotlin("jvm")
    alias(libs.plugins.realm)
    application
}

group = "de.takedown"
version = "1.0.0"

application {
    // Entry Point
    mainClass.set("de.takedown.ApplicationKt")
}

dependencies {
    implementation(libs.realm.base)
    implementation(libs.realm.sync)
    implementation(libs.html.unit)

    implementation(project(":composeApp"))
}

