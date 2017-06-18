package com.seadowg.walkerman.database

import org.postgresql.ds.PGPoolingDataSource
import org.json.JSONObject
import javax.sql.DataSource
import java.net.URI
import org.flywaydb.core.Flyway
import com.seadowg.walkerman.database.UserInfo
import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.Handle

class Database(uri: String, userInfo: UserInfo?) {

    val dataSource = PGPoolingDataSource()

    init {
        if (userInfo != null) {
            dataSource.user = userInfo.user
            dataSource.password = userInfo.password
        }

        dataSource.url = uri
    }

    fun migrate(): Unit {
        val flyway = Flyway()
        flyway.dataSource = dataSource
        flyway.migrate()
    }

    fun connection(): Handle {
        return DBI(dataSource).open()
    }
}

