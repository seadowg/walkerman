package com.seadowg.walkerman

import spark.Spark.*
import spark.SparkBase.port
import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter

public fun main(args: Array<String>) {
  val portString = System.getenv().get("PORT") ?: "9000";
  port(Integer.parseInt(portString))

  get("/", { req, res ->
    val mustacheFactory = DefaultMustacheFactory()
    val mustache = mustacheFactory.compile("index.mustache")
    val stringWriter = StringWriter()

    mustache.execute(stringWriter, null).close();
    stringWriter.toString()
  })
}


