package com.seadowg.walkerman.application

import spark.Spark.*
import spark.SparkBase.port
import spark.SparkBase.staticFileLocation;
import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter
import com.seadowg.walkerman.server
import com.seadowg.walkerman.database
import com.seadowg.walkerman.database.Database
import com.seadowg.walkerman.Environment
import com.seadowg.walkerman.server.Server
import com.seadowg.walkerman.database.UserInfo

class Application(val env: Environment) {
  fun run() {
    val database = Database(env.jdbcUri(), env.jdbcUserInfo())
    database.migrate()

    val server = Server(env.port())
    server.loadRoutes()
  }
}

public fun main(args: Array<String>) {
  val env = Environment(System.getenv())

  val app = Application(env)
  app.run()
}


