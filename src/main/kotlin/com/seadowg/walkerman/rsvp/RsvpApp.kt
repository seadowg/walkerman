package com.seadowg.walkerman.rsvp

import com.seadowg.walkerman.http.NiceRequest
import spark.Spark.get
import spark.Spark.post

public class RsvpApp() {
    fun load() {
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

        get("/rsvps", { req, res ->
            RsvpController(res).index()
        })
    }
}
