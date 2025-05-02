plugins {
    java
}

val moduleGroup = libs.arkreader.resource.network.get().group as Any
group = moduleGroup

dependencies {
    compileOnly(libs.projectlombok.lombok)
    annotationProcessor(libs.projectlombok.lombok)

    implementation(project(":resource-structure"))

    implementation(libs.eclipsesource.minimal.json)
}
