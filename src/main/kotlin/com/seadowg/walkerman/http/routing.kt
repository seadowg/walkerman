package com.seadowg.walkerman.routing

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

fun loadRoutes() {
    get("/", { req, res ->
        renderTemplate("home")
    })

    get("/rsvps/new", { req, res ->
        RsvpController(res).new()
    })

    post("/rsvps", { req, res ->
        val request = NiceRequest(req)
        val email = request.params().get("rsvp_email")

        if (email != null) {
            RsvpController(res).create(email)
        }

        res.redirect("/rsvps/success")
    })

    get("/rsvps/success", { req, res ->
        RsvpController(res).createSuccess()
    })

    get("/rsvps.csv", { req, res ->
        RsvpController(res).csvIndex()
    })
}
