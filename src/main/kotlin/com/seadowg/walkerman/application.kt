package com.seadowg.walkerman.application

import spark.Spark.*
import spark.SparkBase.port
import spark.SparkBase.staticFileLocation;
import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter
import com.seadowg.walkerman.routing.loadRoutes

public fun main(args: Array<String>) {
  port(Integer.parseInt(System.getenv().get("PORT") ?: "9000"))
  staticFileLocation("public")

  loadRoutes()
}


