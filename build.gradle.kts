// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.5.21")
        classpath("io.realm:realm-gradle-plugin:10.15.1")
    }
}
plugins {
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}