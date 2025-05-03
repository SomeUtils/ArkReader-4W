plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

dependencies {
    implementation(gradleApi())
    implementation("com.android.tools.build:gradle:${libs.versions.android.gradle.plugin.get()}")

    implementation(project(":resource-structure"))
    implementation(project(":resource-network"))
    implementation(libs.eclipsesource.minimal.json)

    implementation(libs.square.javapoet)
}

gradlePlugin {
    plugins.create("generatorGradlePlugin") {
        id = libs.plugins.generator.get().pluginId
        implementationClass = "vip.cdms.arkreader.gradle.GeneratorPlugin"
    }
}
