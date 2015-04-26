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
            renderTemplate("home")
        })

        get("/rsvps/new", { req, res ->
            RsvpController(res).new()
        })

        post("/rsvps", { req, res ->
            val request = NiceRequest(req)

            val params = request.params()
            val email = params.get("rsvp_email")
            val name = params.get("rsvp_name")
            val areExtraGuests = params.get("rsvp_guests_yes") == "true"

            val extraGuests = if (areExtraGuests) {
                Integer.parseInt(params.get("rsvp_guests") ?: "0")
            } else {
                0
            }

            if (email != null && name != null) {
                RsvpController(res).create(email, name, extraGuests)
            }

            res.redirect("/rsvps/success")
        })

        get("/rsvps/success", { req, res ->
            RsvpController(res).createSuccess()
        })

        get("/rsvps/email_exists_error", { req, res ->
            RsvpController(res).emailExistsError()
        })

        get("/rsvps.csv", { req, res ->
            RsvpController(res).csvIndex()
        })
    }
}
