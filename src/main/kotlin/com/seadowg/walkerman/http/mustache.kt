package com.seadowg.walkerman.mustache

import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter

fun renderTemplate(name: String, scope: Map<String, Any> = mapOf()): String {
    val mustacheFactory = DefaultMustacheFactory()
    val mustache = mustacheFactory.compile("${name}.mustache")
    val stringWriter = StringWriter()

    mustache.execute(stringWriter, scope).close();
    return stringWriter.toString()
}
