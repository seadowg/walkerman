package com.seadowg.walkerman.rsvp

import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Connection
import org.skife.jdbi.v2.DBI
import org.postgresql.ds.PGPoolingDataSource
import org.skife.jdbi.v2.Handle
import javax.sql.DataSource

class RsvpRepository(val dataSource: DataSource) {
    fun create(email: String, name: String, extraGuests: Int): Unit {
        dbConnection().use { db ->
            db.createStatement("insert into rsvps (email, name, guests) values (:email, :name, :guests)")
                    .bind("email", email)
                    .bind("name", name)
                    .bind("guests", 1 + extraGuests)
                    .execute()
        }
    }

    fun fetchAll(): List<Rsvp> {
        return dbConnection().use { db ->
            db.createQuery("select * from rsvps").toList().map { rsvp ->
                Rsvp(
                    rsvp.get("email") as String,
                    rsvp.get("name") as String,
                    rsvp.get("guests") as Int
                )
            }
        }
    }

    private fun dbConnection(): Handle {
        return DBI(dataSource).open()
    }
}
