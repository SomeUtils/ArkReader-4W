plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:${libs.versions.android.gradle.plugin.get()}")
}

gradlePlugin {
    plugins.create("generatorGradlePlugin") {
        id = libs.plugins.generator.get().pluginId
        implementationClass = "vip.cdms.arkreader.gradle.GeneratorPlugin"
    }
}
