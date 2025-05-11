plugins {
    java
}

val moduleGroup = libs.arkreader.resource.structure.get().group as Any
group = moduleGroup

dependencies {
    compileOnly(libs.projectlombok.lombok)
    annotationProcessor(libs.projectlombok.lombok)
    compileOnly(libs.androidx.annotation)
}
