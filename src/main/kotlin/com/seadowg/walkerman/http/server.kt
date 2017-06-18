package com.seadowg.walkerman.server

import spark.Spark.get
import spark.Spark.post
import com.github.mustachejava.DefaultMustacheFactory
import java.io.StringWriter
import com.seadowg.walkerman.mustache.renderTemplate
import spark.Response
import com.seadowg.walkerman.rsvp.RsvpController
import java.net.URLDecoder.decode;
import spark.Request
import com.seadowg.walkerman.http.NiceRequest
import spark.SparkBase.port
import spark.SparkBase.staticFileLocation

class Server(val port: Int) {
    init {
        port(port)
        staticFileLocation("public")
    }

    fun loadRoutes() {
        get("/", { req, res ->
            res.redirect("/events/2017")
        })

        get("/events/:eventLink", { req, res ->
            renderTemplate("home", mapOf("eventLink" to req.params("eventLink")))
        })

        get("/info", { req, res ->
            renderTemplate("info")
        })

        get("/lineup", { req, res ->
            renderTemplate("lineup")
        })

        get("/hospitality", { req, res ->
            renderTemplate("hospitality")
        })

        get("/general", { req, res ->
            renderTemplate("general")
        })
    }
}
