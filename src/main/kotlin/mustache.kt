package com.seadowg.walkerman.mustache

import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter

fun renderTemplate(name: String): String {
    val mustacheFactory = DefaultMustacheFactory()
    val mustache = mustacheFactory.compile("${name}.mustache")
    val stringWriter = StringWriter()

    mustache.execute(stringWriter, null).close();
    return stringWriter.toString()
}
