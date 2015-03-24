package com.seadowg.walkerman.application

import spark.Spark.*
import spark.SparkBase.port
import spark.SparkBase.staticFileLocation;
import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter
import com.seadowg.walkerman.server
import com.seadowg.walkerman.database

public fun main(args: Array<String>) {
  database.configure()

  server.configure()
  server.loadRoutes()
}


