package vip.cdms.arkreader.gradle

import org.gradle.api.Project

const val TaskGroup = "arkreader"

val Project.GeneratedDirectory
    get() = layout.buildDirectory.dir("generated/arkreader").get()

const val GeneratedPackage = "vip.cdms.arkreader.generated"
