package vip.cdms.arkreader.gradle.utils

import java.io.File

fun ByteArray.saveTo(output: File) =
    output.apply { mkdirs() }.outputStream().use { it.write(this) }
