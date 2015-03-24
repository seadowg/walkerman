package com.seadowg.walkerman.rsvp

import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Connection
import org.skife.jdbi.v2.DBI
import org.postgresql.ds.PGPoolingDataSource
import org.skife.jdbi.v2.Handle
import javax.sql.DataSource

class RsvpRepository(val dataSource: DataSource) {
    fun create(email: String): Unit {
        dbConnection().use { db ->
            db.createStatement("insert into rsvps (email) values (:email)")
                    .bind("email", email)
                    .execute()
        }
    }

    fun fetchAll(): List<String> {
        return dbConnection().use { db ->
            db.createQuery("select * from rsvps").toList().map { rsvp ->
                rsvp.get("email") as String
            }
        }
    }

    private fun dbConnection(): Handle {
        return DBI(dataSource).open()
    }
}
