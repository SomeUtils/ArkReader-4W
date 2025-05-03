package vip.cdms.arkreader.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import vip.cdms.arkreader.gradle.analyze.GenAppScoreImpl
import vip.cdms.arkreader.gradle.analyze.StaticContext

abstract class GenerateTask : DefaultTask() {
    @get:OutputDirectory
    abstract val srcDir: DirectoryProperty

    @get:OutputDirectory
    abstract val assetsDir: DirectoryProperty

    @get:OutputDirectory
    abstract val networkCacheDir: DirectoryProperty

    @TaskAction
    fun generate() = StaticContext(
        srcPath = srcDir.get().asFile.toPath(),
        assetsDir = assetsDir.get().asFile,
        networkCacheDir = networkCacheDir.get().asFile,
    ).let { context ->
        GenAppScoreImpl(context).generate()
    }
}
