package com.seadowg.walkerman.router

import spark.Spark.get
import spark.Spark.post
import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter
import com.seadowg.walkerman.mustache.renderTemplate

fun loadRoutes() {
    get("/", { req, res ->  renderTemplate("home") })

    get("/rsvps/new", { req, res -> renderTemplate("rsvps_new") })
    post("/rsvps", { req, res -> res.redirect("/rsvps/success"); })
    get("/rsvps/success", { req, res -> renderTemplate("rsvps_create") })
}
