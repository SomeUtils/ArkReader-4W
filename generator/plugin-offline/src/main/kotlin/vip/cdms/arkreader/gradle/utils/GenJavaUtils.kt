package vip.cdms.arkreader.gradle.utils

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import javax.lang.model.element.Modifier
import kotlin.reflect.KCallable
import kotlin.reflect.KVisibility

inline fun <reified R> KCallable<R>.overrideMethod(): MethodSpec.Builder =
    MethodSpec.methodBuilder(name)
        .addAnnotation(Override::class.java)
        .addModifiers(
            when (visibility) {
                KVisibility.PUBLIC -> Modifier.PUBLIC
                KVisibility.PROTECTED -> Modifier.PROTECTED
                else -> error("Unexpected visibility: $visibility")
            }
        )  // ignore parameters... impl it when need
//        .returns(returnType.javaType)
        .returns(R::class.java)

fun ClassName.createSingletonField(): FieldSpec = FieldSpec.builder(
    this, "INSTANCE",
    Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
)
    .initializer("new \$T()", this)
    .build()
