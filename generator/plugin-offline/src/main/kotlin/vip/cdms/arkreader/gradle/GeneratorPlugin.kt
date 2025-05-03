package vip.cdms.arkreader.gradle

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import vip.cdms.arkreader.gradle.tasks.GenerateTask

class GeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val generatedJavaDir = generatedDirectory.dir("java")
        val generatedAssetsDir = generatedDirectory.dir("assets")

        val generateTask by tasks.registering(GenerateTask::class) {
            group = TaskGroup
            outputs.cacheIf { generatedJavaDir.asFile.exists() && generatedAssetsDir.asFile.exists() }
            srcDir.set(generatedJavaDir)
            assetsDir.set(generatedAssetsDir)
            networkCacheDir.set(generatedDirectory.dir("network_cache"))
        }

        afterEvaluate {
            plugins.withId("com.android.application") {
                extensions.configure<AppExtension>("android") {
                    sourceSets.getByName("offline").java.srcDir(generatedJavaDir)
                    sourceSets.getByName("offline").assets.srcDir(generatedAssetsDir)
                }
            }

            tasks.named("preBuild").configure {
                dependsOn(generateTask)
            }
        }
    }
}
