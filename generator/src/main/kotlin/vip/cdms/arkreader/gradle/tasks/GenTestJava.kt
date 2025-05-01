package vip.cdms.arkreader.gradle.tasks

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import vip.cdms.arkreader.gradle.GeneratedPackage
import vip.cdms.arkreader.gradle.utils.IGenerateTask
import javax.lang.model.element.Modifier

abstract class GenTestJava : IGenerateTask() {
    override fun generate() {
        val greet = MethodSpec.methodBuilder("greet")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(String::class.java)
            .addStatement("return \$S", "Hello World!")
            .build()

        val greeter = TypeSpec.classBuilder("Greeter")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addMethod(greet)
            .build()

        val javaFile = JavaFile.builder("$GeneratedPackage.test", greeter)
            .build()

        javaFile.writeTo(srcPath)
    }
}
