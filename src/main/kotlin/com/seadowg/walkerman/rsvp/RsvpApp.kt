package com.seadowg.walkerman.rsvp

import com.seadowg.walkerman.database.Database
import com.seadowg.walkerman.http.NiceRequest
import spark.Request
import spark.Response
import spark.Spark.get
import spark.Spark.post

class RsvpApp(val database: Database) {
    fun load() {
        get("/events/:eventLink/rsvps/new", { req, res ->
            controller(req, res, database).new()
        })

        post("/events/:eventLink/rsvps", { req, res ->
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
                controller(req, res, database).create(email, name, extraGuests)
            }

            res.redirect("/rsvps/success")
        })

        get("/events/:eventLink/rsvps/success", { req, res ->
            controller(req, res, database).createSuccess()
        })

        get("/events/:eventLink/rsvps/email_exists_error", { req, res ->
            controller(req, res, database).emailExistsError()
        })

        get("/events/:eventLink/rsvps.csv", { req, res ->
            controller(req, res, database).csvIndex()
        })

        get("/events/:eventLink/rsvps", { req, res ->
            controller(req, res, database).index()
        })
    }

    private fun controller(request: Request, response: Response, database: Database): RsvpController {
        return RsvpController(request.params("eventLink"), response, database)
    }
}
