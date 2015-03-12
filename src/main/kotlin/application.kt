package com.seadowg.walkerman

import spark.Spark.*
import spark.SparkBase.port

public fun main(args: Array<String>) {
  val portString = System.getenv().get("PORT") ?: "9000";
  port(Integer.parseInt(portString))

  get("/", { req, res -> "Hello Walkerman!" })
}


