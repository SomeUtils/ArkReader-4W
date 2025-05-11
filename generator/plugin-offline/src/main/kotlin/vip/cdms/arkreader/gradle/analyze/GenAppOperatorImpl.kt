package vip.cdms.arkreader.gradle.analyze

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import vip.cdms.arkreader.gradle.GeneratedPackage
import vip.cdms.arkreader.gradle.utils.*
import vip.cdms.arkreader.resource.*
import vip.cdms.arkreader.resource.network.implement.AppOperatorImpl
import vip.cdms.arkreader.resource.story.StoryContent
import javax.lang.model.element.Modifier

class GenAppOperatorImpl(private val context: StaticContext) {
    private val appOperatorImplClass: ClassName = ClassName.get(GeneratedPackage, "AppOperatorImpl")
    private val sortedOperators = AppOperatorImpl.INSTANCE.sortedOperators

    fun generate() {
        val getSortedOperators = AppOperator::getSortedOperators.overrideMethod()
            .addCode(generateGetSortedOperatorsCode())
            .build()

        val getProfessionIcon = AppOperator::getProfessionIcon.overrideMethod()
            .addCode(generateGetProfessionIconCode())
            .build()

        val appOperatorImpl = TypeSpec.classBuilder(appOperatorImplClass)
            .addSuperinterface(AppOperator::class.java)
            .addModifiers(Modifier.PUBLIC)
            .addField(appOperatorImplClass.createSingletonField())
            .addMethod(getSortedOperators)
            .addMethod(getProfessionIcon)
            .build()

        val javaFile = JavaFile.builder(appOperatorImplClass.packageName(), appOperatorImpl).build()
        javaFile.writeTo(context.srcPath)
    }

    private fun generateGetSortedOperatorsCode(): CodeBlock = with(CodeBlock.builder()) {
        add("return new \$T[] {\n", StaticContext.OperatorImplClass)
        indent()
        sortedOperators.forEach { operator ->
            add(
                "new \$T(\$S, \$S, \$S, \$S, \$T.\$L, (byte) \$L) {\n",
                StaticContext.OperatorImplClass,
                context.getAssets(operator.avatarImage),
                operator.name,
                operator.appellation,
                operator.displayNumber,
                OperatorProfession::class.java, operator.profession.name,
                operator.rarity
            )
            indent()

            generateOperatorGetArchivesCode(operator)
            generateOperatorGetModulesCode(operator)
            generateOperatorGetRecordsCode(operator)

            unindent()
            add("},\n")
        }
        unindent()
        add("};\n")
        build()
    }

    private fun CodeBlock.Builder.generateOperatorGetArchivesCode(operator: Operator) {
        add("@Override\n")
        add("public \$T[] getArchives() {\n", OperatorArchive::class.java)
        indent()
        add("return new \$T[] {\n", OperatorArchive::class.java)
        indent()
        operator.archives.forEach { archive ->
            add("new \$T(\n", OperatorArchive::class.java)
            indent()
            add("\$S,\n", archive.title)
            add("new String[] {\n")
            indent()
            archive.stories.forEach { story ->
                add("\$S,\n", story)
            }
            unindent()
            add("}\n")
            unindent()
            add("),\n")
        }
        unindent()
        add("};\n")
        unindent()
        add("}\n")
    }

    private fun CodeBlock.Builder.generateOperatorGetModulesCode(operator: Operator) {
        add("@Override\n")
        add("public \$T[] getModules() {\n", StaticContext.OperatorModuleImplClass)
        indent()
        if (operator.modules == null || operator.modules!!.isEmpty())
            add("return null;\n")
        else {
            add("return new \$T[] {\n", StaticContext.OperatorModuleImplClass)
            indent()
            operator.modules!!.forEach { module ->
                add(
                    "new \$T(\$S, \$S, \$S, \$S, \$S),\n",
                    StaticContext.OperatorModuleImplClass,
                    module.name,
                    module.description,
                    module.tagText,
                    module.tagColor,
                    context.getAssets(module.equipIcon),
                )
            }
            unindent()
            add("};\n")
        }
        unindent()
        add("}\n")
    }

    private fun CodeBlock.Builder.generateOperatorGetRecordsCode(operator: Operator) {
        add("@Override\n")
        add("public \$T[] getRecords() {\n", OperatorRecord::class.java)
        indent()
        if (operator.records == null || operator.records!!.isEmpty())
            add("return null;\n")
        else {
            add("return new \$T[] {\n", OperatorRecord::class.java)
            indent()
            operator.records!!.forEach { record ->
                add("new \$T(\n", OperatorRecord::class.java)
                indent()
                add("\$S,\n", record.storySetName)
                add("new \$T[] {\n", StaticContext.OperatorRecordAvgImplClass)
                indent()
                record.avgList.forEach { avg ->
                    add(
                        "new \$T(\$S) {\n",
                        StaticContext.OperatorRecordAvgImplClass, avg.intro
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
                add("}\n")
                unindent()
                add("),\n")
            }
            unindent()
            add("};\n")
        }
        unindent()
        add("}\n")
    }

    private fun generateGetProfessionIconCode(): CodeBlock = with(CodeBlock.builder()) {
        add("return \$T.readAssets(\n", StaticContext.OfflineResourceHelperClass)
        indent()
        add("switch (arg0) {\n")
        indent()
        OperatorProfession.values().forEach {
            val icon = AppOperatorImpl.INSTANCE.getProfessionIcon(it)
            add("case ${it.name} -> \$S;\n", context.getAssets(icon))
        }
        unindent()
        add("}\n")
        unindent()
        add(");\n")
        build()
    }
}
