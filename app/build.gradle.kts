import vip.cdms.arkreader.gradle.utils.runCommand
import java.time.LocalDate

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.generator)
}

android {
    namespace = "vip.cdms.arkreader"
    compileSdk = 35

    defaultConfig {
        applicationId = "vip.cdms.arkreader"
        minSdk = 21
        targetSdk = 35
        versionCode = libs.versions.arkreader.core.code.get().toInt()
        versionName = libs.versions.arkreader.core.version.get().attachToCIVersion()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        all {
            buildConfigField("int", "CORE_VERSION_CODE", libs.versions.arkreader.core.code.get())
            buildConfigField("String", "CORE_VERSION_NAME", "\"${libs.versions.arkreader.core.version.get()}\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
            versionName = "%d.%02d.%02d".format(today.year, today.monthValue, today.dayOfMonth).attachToCIVersion()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    lint {
        disable += "OldTargetApi"
    }
}

fun String.attachToCIVersion() = if (System.getenv("CI") != "true") this else run {
    val branch = System.getenv("GITHUB_REF_NAME")
        ?: runCommand("git rev-parse --abbrev-ref HEAD")
    val hash = runCommand("git rev-parse --short HEAD")
    "$this ($branch-$hash)"
}

dependencies {
    compileOnly(libs.projectlombok.lombok)
    annotationProcessor(libs.projectlombok.lombok)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.android.material)
}
