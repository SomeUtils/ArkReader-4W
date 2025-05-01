package vip.cdms.arkreader.gradle.utils

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.nio.file.Path

abstract class IGenerateTask : DefaultTask() {
    @get:OutputDirectory
    abstract val srcDir: DirectoryProperty

    @get:Internal
    protected val srcPath: Path
        get() = srcDir.get().asFile.toPath()

    @TaskAction
    abstract fun generate()
}
