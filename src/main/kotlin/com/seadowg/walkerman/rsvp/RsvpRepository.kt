package com.seadowg.walkerman.rsvp

private val rsvpEmails = arrayListOf<String>()

class RsvpRepository() {
    fun create(email: String) {
        rsvpEmails.add(email)
    }

    fun fetchAll(): List<String> {
        return rsvpEmails.toList();
    }
}
