package vip.cdms.arkreader.gradle.utils

import com.squareup.javapoet.*
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KVisibility
import kotlin.reflect.jvm.javaMethod

inline fun <reified R> KFunction<R>.overrideMethod(): MethodSpec.Builder =
    MethodSpec.methodBuilder(name)
        .addAnnotation(Override::class.java)
        .addModifiers(
            when (visibility) {
                KVisibility.PUBLIC -> Modifier.PUBLIC
                KVisibility.PROTECTED -> Modifier.PROTECTED
                else -> error("Unexpected visibility: $visibility")
            }
        ).apply {
            javaMethod?.parameters?.withIndex()?.forEach { (i, param) ->
                addParameter(
                    ParameterSpec
                        .builder(TypeName.get(param.parameterizedType), "arg$i")
                        .build()
                )
            }
        }
//        .returns(returnType.javaType)
        .returns(R::class.java)

fun ClassName.createSingletonField(): FieldSpec = FieldSpec.builder(
    this, "INSTANCE",
    Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL
)
    .initializer("new \$T()", this)
    .build()
