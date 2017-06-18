package com.seadowg.walkerman.rsvp

import spark.Response
import com.seadowg.walkerman.mustache.renderTemplate
import com.seadowg.walkerman.database.dataSource

class RsvpController(val eventLink: String, val response: Response) {

    fun csvIndex(): String {
        val rsvps = RsvpRepository(eventLink, dataSource).fetchAll().map { rsvp ->
            mapOf("email" to rsvp.email, "name" to rsvp.name, "guests" to rsvp.guest)
        }

        response.type("text/csv")
        return renderTemplate("rsvps.csv", mapOf("rsvps" to rsvps))
    }

    fun new(): String {
        return renderTemplate("rsvps_new")
    }

    fun create(email: String, name: String, extraGuests: Int): Unit {
        val rsvpRepository = RsvpRepository(eventLink, dataSource)

        if (rsvpRepository.exists(email)) {
            response.redirect("/events/$eventLink/rsvps/email_exists_error")
        } else {
            rsvpRepository.create(email, name, extraGuests)
            response.redirect("/events/$eventLink/rsvps/success")
        }
    }

    fun index(): Any {
        val rsvps = RsvpRepository(eventLink, dataSource).fetchAll().map { rsvp ->
            mapOf("email" to rsvp.email, "name" to rsvp.name, "guests" to rsvp.guest)
        }

        return renderTemplate("rsvps", mapOf("rsvps" to rsvps))
    }

    fun createSuccess(): String {
        return renderTemplate("rsvps_create")
    }

    fun emailExistsError(): String {
        return renderTemplate("rsvps_email_exists_error")
    }
}
