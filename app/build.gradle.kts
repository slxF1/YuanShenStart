plugins {
    id("com.android.application")
}

android {
    namespace = "com.xxxy.yjw.yuanshenstart"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.xxxy.yjw.yuanshenstart"
        minSdk = 22
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation ("com.google.code.gson:gson:2.8.5")
    implementation ("com.squareup.okhttp3:okhttp:3.14.9")
    implementation ("com.squareup.okio:okio:3.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")

    implementation("mysql:mysql-connector-java:5.1.49")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}