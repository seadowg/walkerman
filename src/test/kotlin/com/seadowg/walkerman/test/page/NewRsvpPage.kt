package com.seadowg.walkerman.test.page

import org.fluentlenium.core.FluentPage

class NewRsvpPage : FluentPage() {
    override fun getUrl(): String {
        return "http://localhost:9000/events/2015/rsvps/new"
    }

    fun fillInNameAndEmail(email: String, name: String) {
        fill("#rsvp_email").with(email)
        fill("#rsvp_name").with(name)
    }

    fun submit() {
        submit("#rsvp_submit")
    }

    fun addExtraGuests(guests: Int) {
        find("#rsvp_guests_yes").click()
        fill("#rsvp_guests").with(guests.toString())
    }

    fun removeExtraGuests() {
        find("#rsvp_guests_no").click()
    }
}