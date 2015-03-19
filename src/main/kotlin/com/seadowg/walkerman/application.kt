package com.seadowg.walkerman.application

import spark.Spark.*
import spark.SparkBase.port
import spark.SparkBase.staticFileLocation;
import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter
import com.seadowg.walkerman.server

public fun main(args: Array<String>) {
  server.configure()
  server.loadRoutes()
}


