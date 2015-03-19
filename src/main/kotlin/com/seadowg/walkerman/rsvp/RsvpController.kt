package com.seadowg.walkerman.rsvp

import spark.Response
import com.seadowg.walkerman.mustache

class RsvpController(val response: Response) {
    fun csvIndex(): String {
        val rsvps = RsvpRepository().fetchAll().map { email -> mapOf("email" to email) }

        response.type("text/csv")
        return mustache.renderTemplate("rsvps", mapOf("rsvps" to rsvps))
    }

    fun new(): String {
        return mustache.renderTemplate("rsvps_new")
    }

    fun create(email: String): Unit {
        RsvpRepository().create(email)
        response.redirect("/rsvps/success")
    }

    fun createSuccess(): String {
        return mustache.renderTemplate("rsvps_create")
    }
}
