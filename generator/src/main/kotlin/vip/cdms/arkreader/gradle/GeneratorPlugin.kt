package vip.cdms.arkreader.gradle

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import vip.cdms.arkreader.gradle.tasks.GenTestJava

class GeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val generatedJavaDir = GeneratedDirectory.dir("java")

        val generateTestJava by tasks.registering(GenTestJava::class) {
            group = TaskGroup
            srcDir.set(generatedJavaDir)
        }
        val generateTask by tasks.registering {
            group = TaskGroup
            dependsOn(generateTestJava)
        }

        plugins.withId("com.android.application") {
            extensions.configure<AppExtension>("android") {
                sourceSets.getByName("main").java.srcDir(generatedJavaDir)
            }
        }

        afterEvaluate {
            tasks.named("preBuild").configure {
                dependsOn(generateTask)
            }
        }
    }
}
