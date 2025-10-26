plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "org.override.logistics"
    compileSdk = 36

    defaultConfig {
        applicationId = "org.override.logistics"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Implementaciones de Modulos

    // Core
    implementation(project(":core:common"))
    implementation(project(":core:ui"))

    // Features
    implementation(project(":features:haulier:presentation"))
    implementation(project(":features:wharehouse:presentation"))

    implementation(project(":features:login:domain"))
    implementation(project(":features:login:presentation"))

    // Data
    implementation(project(":data:auth:api"))
    implementation(project(":data:auth:impl"))

    implementation(project(":data:session:api"))
    implementation(project(":data:session:impl"))


    // Implementaciones principales
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.composeIcons.fontAwesome)
    implementation(libs.material.kolor)
    implementation(libs.androidx.core.splashscreen)

    // Inyección de dependencias
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.android)

    // utilidades de kotlinx
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.play.services)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // datastore
    implementation(libs.androidx.datastore.preferences.core)
    implementation(libs.androidx.datastore.preferences)

    // Pruebas unitarias
    testImplementation(libs.junit)

    // Pruebas instrumentadas
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Dependencias para depuración
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}