//buildscript {
//    ext {
//        compose_version = '1.2.0'
//    }
//}// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    id 'com.android.application' version '7.4.2' apply false
//    id 'com.android.library' version '7.4.2' apply false
//    id 'org.jetbrains.kotlin.android' version '1.7.0' apply false
//}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

plugins {
    // Define the versions of external plugins that may be used by subprojects.

    // Specify that a module is an application.
    // https://developer.android.com/studio/releases/gradle-plugin
    id ("com.android.application") version "7.4.2" apply false

    // Kotlin Android plugin.
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.android
    id ("org.jetbrains.kotlin.android") version "1.8.10" apply false

    // Kotlin serialization plugin
    //https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.serialization
    kotlin("plugin.serialization") version "1.8.10" apply false

    // Kotlin parcelize plugin
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.parcelize
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.8.10" apply false
}

tasks {
    create("clean", Delete::class.java)
    {
        group = "build"
        delete(rootProject.buildDir)
    }
}