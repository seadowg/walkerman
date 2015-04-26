package com.seadowg.walkerman.rsvp

import spark.Response
import com.seadowg.walkerman.mustache
import com.seadowg.walkerman.database.dataSource

class RsvpController(val response: Response) {
    fun csvIndex(): String {
        val rsvps = RsvpRepository(dataSource).fetchAll().map { rsvp ->
            mapOf("email" to rsvp.email, "name" to rsvp.name, "guests" to rsvp.guest)
        }

        response.type("text/csv")
        return mustache.renderTemplate("rsvps", mapOf("rsvps" to rsvps))
    }

    fun new(): String {
        return mustache.renderTemplate("rsvps_new")
    }

    fun create(email: String, name: String, extraGuests: Int): Unit {
        val rsvpRepository = RsvpRepository(dataSource)

        if (rsvpRepository.exists(email)) {
            response.redirect("/rsvps/email_exists_error")
        } else {
            rsvpRepository.create(email, name, extraGuests)
            response.redirect("/rsvps/success")
        }
    }

    fun createSuccess(): String {
        return mustache.renderTemplate("rsvps_create")
    }

    fun emailExistsError(): String {
        return mustache.renderTemplate("rsvps_email_exists_error")
    }
}
