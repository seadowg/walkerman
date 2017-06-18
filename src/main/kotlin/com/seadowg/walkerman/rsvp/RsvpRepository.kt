package com.seadowg.walkerman.rsvp

import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.Handle
import javax.sql.DataSource

class RsvpRepository(val eventLink: String, val dataSource: DataSource) {

    fun exists(email: String): Boolean {
        val eventID = getEventID(eventLink)

        return dbConnection().use { db ->
            db.createQuery("select * from rsvps where email=:email and event_id=:eventID")
                    .bind("email", email)
                    .bind("eventID", eventID)
                    .count() > 0
        }
    }

    fun create(email: String, name: String, extraGuests: Int): Unit {
        val eventID = getEventID(eventLink)

        dbConnection().use { db ->
            db.createStatement("insert into rsvps (email, name, guests, event_id) values (:email, :name, :guests, :eventID)")
                    .bind("email", email)
                    .bind("name", name)
                    .bind("guests", 1 + extraGuests)
                    .bind("eventID", eventID)
                    .execute()
        }
    }

    fun fetchAll(): List<Rsvp> {
        val eventID = getEventID(eventLink)

        return dbConnection().use { db ->
            db.createQuery("select * from rsvps where event_id=:eventID")
                    .bind("eventID", eventID)
                    .toList().map { rsvp ->
                Rsvp(
                    rsvp["email"] as String,
                    rsvp["name"] as String,
                    rsvp["guests"] as Int
                )
            }
        }
    }

    private fun getEventID(link: String): Long {
        val eventID = dbConnection().use { db ->
            db.createQuery("select * from events where link=:link")
                    .bind("link", link)
                    .toList().map { event -> event["id"] as Long }
        }.first()
        return eventID
    }

    private fun dbConnection(): Handle {
        return DBI(dataSource).open()
    }
}
