package vip.cdms.arkreader.gradle

import org.gradle.api.Project

const val TaskGroup = "arkreader"

const val GeneratedPackage = "vip.cdms.arkreader.generated"

val Project.generatedDirectory
    get() = layout.buildDirectory.dir("generated/arkreader").get()
