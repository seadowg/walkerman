package com.seadowg.walkerman.rsvp

import spark.Response
import com.seadowg.walkerman.mustache
import com.seadowg.walkerman.database.dataSource

class RsvpController(val response: Response) {
    fun csvIndex(): String {
        val rsvps = RsvpRepository(dataSource).fetchAll().map { email -> mapOf("email" to email) }

        response.type("text/csv")
        return mustache.renderTemplate("rsvps", mapOf("rsvps" to rsvps))
    }

    fun new(): String {
        return mustache.renderTemplate("rsvps_new")
    }

    fun create(email: String): Unit {
        RsvpRepository(dataSource).create(email)
        response.redirect("/rsvps/success")
    }

    fun createSuccess(): String {
        return mustache.renderTemplate("rsvps_create")
    }
}
