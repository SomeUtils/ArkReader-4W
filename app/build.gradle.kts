import java.time.LocalDate

plugins {
    alias(libs.plugins.generator)
    alias(libs.plugins.android.application)
}

android {
    namespace = "vip.cdms.arkreader"
    compileSdk = 35

    defaultConfig {
        applicationId = "vip.cdms.arkreader"
        minSdk = 21
        targetSdk = 35
        versionCode = libs.versions.arkreader.core.code.get().toInt()
        versionName = libs.versions.arkreader.core.version.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        all {
            buildConfigField("String", "CORE_VERSION", "\"${libs.versions.arkreader.core.version.get()}\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions += "gamedata"
    productFlavors {
        create("online") {
            applicationIdSuffix = ".online"
        }
        create("offline") {
            applicationIdSuffix = ".offline"
            val today = LocalDate.now()
            versionCode = today.year * 10000 + today.monthValue * 100 + today.dayOfMonth
            versionName = "%d.%02d.%02d".format(today.year, today.monthValue, today.dayOfMonth)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    compileOnly(libs.projectlombok.lombok)
    annotationProcessor(libs.projectlombok.lombok)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.android.material)
}
