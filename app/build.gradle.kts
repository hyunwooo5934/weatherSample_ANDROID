plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
//    id ("com.google.dagger.hilt.android") version ("2.44") apply true
//    id ("kotlin-kapt")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.example.weatheralarm"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.weatheralarm"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }


    buildFeatures {
        dataBinding = true
        viewBinding = true
    }


    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }



}

dependencies {

    val roomVersion = "2.4.1"

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.5.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")


    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation ("androidx.activity:activity-ktx:1.4.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0") // retrofit2
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Gson 변환기
    implementation ("com.squareup.okhttp3:logging-interceptor:4.5.0")

    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    implementation ("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")


    implementation ("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")


}

kapt {
    correctErrorTypes = true
}