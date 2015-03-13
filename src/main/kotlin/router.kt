package com.seadowg.walkerman.router

import spark.Spark.get
import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter
import com.seadowg.walkerman.mustache.renderTemplate

fun loadRoutes() {
    get("/", { req, res ->  renderTemplate("home") })
}
