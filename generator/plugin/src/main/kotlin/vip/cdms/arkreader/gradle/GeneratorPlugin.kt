package vip.cdms.arkreader.gradle

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import vip.cdms.arkreader.gradle.tasks.GenTestJavaTask
import vip.cdms.arkreader.resource.network.Network
import vip.cdms.arkreader.resource.network.NetworkCache
import vip.cdms.arkreader.resource.network.implement.AppScoreImpl

class GeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        Network.cache = NetworkCache(generatedDirectory.dir("network_cache").asFile)

        val generatedJavaDir = generatedDirectory.dir("java")

        val generateTestJava by tasks.registering(GenTestJavaTask::class) {
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
            AppScoreImpl.INSTANCE.sortedEvents
                .withIndex().forEach { (index, event) ->
                    println("=>> $index")
                    println(event.name)
                    println(event.type.name)
                    println(event.appCategory)
                    println(event.wordcount)
                    for (info in event.stories) {
                        println("-->")
                        println("    " + info.name)
                        println("    " + info.stageName)
                        println("    " + info.summary)
                        println("    " + info.wordcount)
                    }
                    println()
                }
            tasks.named("preBuild").configure {
                dependsOn(generateTask)
            }
        }
    }
}
