package vip.cdms.arkreader.gradle.utils

import org.gradle.api.Project
import org.gradle.api.file.Directory
import java.io.ByteArrayOutputStream

fun Project.runCommand(command: String, trim: Boolean = true): String {
    val output = ByteArrayOutputStream()
    exec {
        commandLine = command.split(" ")
        standardOutput = output
        isIgnoreExitValue = true
    }
    return output.toString().run { if (trim) trim() else this }
}

fun Directory.isEmpty() = with(asFile) {
    if (!exists()) true
    else list()?.isEmpty() ?: true
}
