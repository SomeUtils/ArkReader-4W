package vip.cdms.arkreader.gradle.analyze

import com.squareup.javapoet.*
import vip.cdms.arkreader.gradle.GeneratedPackage
import vip.cdms.arkreader.gradle.utils.createSingletonField
import vip.cdms.arkreader.gradle.utils.overrideMethod
import vip.cdms.arkreader.resource.AppScore
import vip.cdms.arkreader.resource.EventType
import vip.cdms.arkreader.resource.StoryInfo
import vip.cdms.arkreader.resource.network.implement.AppScoreImpl
import vip.cdms.arkreader.resource.story.StoryContent
import javax.lang.model.element.Modifier

class GenAppScoreImpl(private val context: StaticContext) {
    private val appScoreImplClass: ClassName = ClassName.get(GeneratedPackage, "AppScoreImpl")
    private val sortedEvents = AppScoreImpl.INSTANCE.sortedEvents

    fun generate() {
        val getSortedEvents = AppScore::getSortedEvents.overrideMethod()
            .addCode(generateGetSortedEventsCode())
            .build()

        val getStoriesMethods = sortedEvents.mapIndexed { i, event ->
            MethodSpec.methodBuilder("getStories$i")
                .addModifiers(Modifier.PRIVATE)
                .returns(ArrayTypeName.of(StaticContext.StoryInfoImplClass))
                .addCode(generateGetStoriesCode(event.stories))
                .build()
        }

        val appScoreImpl = TypeSpec.classBuilder(appScoreImplClass)
            .addSuperinterface(AppScore::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addField(appScoreImplClass.createSingletonField())
            .addMethod(getSortedEvents)
            .addMethods(getStoriesMethods)
            .build()

        val javaFile = JavaFile.builder(appScoreImplClass.packageName(), appScoreImpl).build()
        javaFile.writeTo(context.srcPath)
    }

    private fun generateGetSortedEventsCode(): CodeBlock = with(CodeBlock.builder()) {
        add("return new \$T[] {\n", StaticContext.EventImplClass)
        indent()
        sortedEvents.withIndex().forEach { (i, event) ->
            add(
                "new \$T(\$S, \$S, \$T.\$L, \$S, \$L, \$L) {\n", StaticContext.EventImplClass,
                context.getAssets(event.coverImage), event.name,
                EventType::class.java, event.type.name,
                event.appCategory, event.startTime, event.wordcount
            )
            indent()
            add("@Override\n")
            add("public \$T[] getStories() {\n", StaticContext.StoryInfoImplClass)
            indent()
            add("return getStories$i();\n")
            unindent()
            add("}\n")
            unindent()
            add("},\n")
        }
        unindent()
        add("};\n")
        build()
    }

    private fun generateGetStoriesCode(stories: Array<StoryInfo>) = with(CodeBlock.builder()) {
        add("return new \$T[] {\n", StaticContext.StoryInfoImplClass)
        indent()
        stories.forEach { story ->
            add(
                "new \$T(\$S, \$S, \$S, \$L) {\n",
                StaticContext.StoryInfoImplClass, story.name, story.stageName, story.summary, story.wordcount
            )
            indent()
            add("@Override\n")
            add("public \$T getContent() {\n", StoryContent::class.java)
            indent()
            add("return null;\n")
            unindent()
            add("}\n")
            unindent()
            add("},\n")
        }
        unindent()
        add("};\n")
        build()
    }
}
