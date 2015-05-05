package com.seadowg.walkerman.test.page

import org.fluentlenium.core.FluentPage

class NewRsvpPage : FluentPage() {
    public override fun getUrl(): String {
        return "http://localhost:9000/rsvps/new"
    }

    public fun fillInNameAndEmail(email: String, name: String) {
        fill("#rsvp_email").with(email)
        fill("#rsvp_name").with(name)
    }

    public fun submit() {
        submit("#rsvp_submit")
    }

    public fun addExtraGuests(guests: Int) {
        find("#rsvp_guests_yes").click()
        fill("#rsvp_guests").with(guests.toString())
    }

    public fun removeExtraGuests() {
        find("#rsvp_guests_no").click()
    }
}