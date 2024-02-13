import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.org.martall"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.org.martall"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MOCK_USER_URL", project.properties["MOCK_USER_URL"].toString())
        buildConfigField("String", "MOCK_MART_URL", properties["MOCK_MART_URL"].toString())
        buildConfigField("String", "MOCK_ITEM_URL", properties["MOCK_ITEM_URL"].toString())
        buildConfigField("String", "MOCK_CART_URL", properties["MOCK_CART_URL"].toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
//
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.1.1"
//    }

    kotlinOptions {
        jvmTarget = "1.8"
    }


//    viewBinding {
//        enable = true
//    }

}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.core:core-splashscreen:1.1.0-alpha02")  // Splash Screen
    implementation("de.hdodenhof:circleimageview:3.1.0") // Circle Image를 위한 라이브러리
    implementation ("com.google.android.material:material:1.2.0-alpha01")
    implementation("androidx.datastore:datastore-preferences:1.0.0") // DataStore
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit
    implementation("com.google.code.gson:gson:2.8.8") // Gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0") // Kotlin Serialization

    implementation ("com.github.bumptech.glide:glide:4.12.0") // 현재 Glide의 최신 버전인 경우에 해당

    implementation ("androidx.fragment:fragment-ktx:1.3.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
}

