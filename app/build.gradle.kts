plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.sourav.verthome"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sourav.verthome"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.2"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

        implementation (libs.appcompat)
        implementation (libs.firebase.auth)
        implementation (libs.firebase.database)
        implementation (libs.material)
    implementation ("androidx.work:work-runtime:2.7.1")

    implementation ("androidx.activity:activity-ktx:1.10.0")
        implementation (libs.androidx.constraintlayout.v221)

        implementation (libs.firebase.bom.v33100)

        implementation (libs.gms.play.services.auth)
        implementation (libs.play.services.maps)
        implementation (libs.gms.play.services.location)
        implementation (libs.lottie)
        implementation ("de.hdodenhof:circleimageview:3.1.0")
        implementation ("androidx.cardview:cardview:1.0.0")
        implementation ("androidx.credentials:credentials:1.3.0")
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.firebase:firebase-messaging-directboot:24.1.0")

        implementation (libs.glide)
        implementation (libs.glide.transformations)
    implementation(libs.activity)
    implementation(libs.firebase.messaging)
    annotationProcessor (libs.compiler)
        testImplementation ("junit:junit:4.13.2")
        androidTestImplementation ("androidx.test.ext:junit:1.2.1")
        androidTestImplementation (libs.espresso.core)
}
    
