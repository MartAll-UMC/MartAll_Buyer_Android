buildscript {
    val agp_version by extra("8.1.4")
    val agp_version1 by extra("8.1.0")
    val agp_version2 by extra("8.1.2")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

