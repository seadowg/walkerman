package com.seadowg.walkerman.database

import org.postgresql.ds.PGPoolingDataSource
import org.json.JSONObject
import javax.sql.DataSource
import java.net.URI
import org.flywaydb.core.Flyway
import com.seadowg.walkerman.database.UserInfo

val dataSource = PGPoolingDataSource()

class Database(val uri: String, val userInfo: UserInfo?) {
    init {
        dataSource.setUrl(uri)

        if (userInfo != null) {
            dataSource.setUser(userInfo.user)
            dataSource.setPassword(userInfo.password)
        }
    }

    fun migrate(): Unit {
        val flyway = Flyway();
        flyway.setDataSource(dataSource)
        flyway.migrate()
    }
}

