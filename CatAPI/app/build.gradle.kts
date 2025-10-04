plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.mapb.catapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mapb.catapi"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
	implementation("com.squareup.retrofit2:retrofit:2.9.0") // Libreria Retrofit (para REST)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Libreria para convertir JSON a objetos Java
    implementation("com.github.bumptech.glide:glide:4.13.0") // Libreria para asignar imagenes/gifs de una URL a componentes ImageView
}