package com.seadowg.walkerman.database

import org.postgresql.ds.PGPoolingDataSource
import org.json.JSONObject
import javax.sql.DataSource
import java.net.URI
import org.flywaydb.core.Flyway
import com.seadowg.walkerman.database.UserInfo

val dataSource = PGPoolingDataSource()
private var connected = false

class Database(uri: String, userInfo: UserInfo?) {

    init {
        if (!connected) {
            if (userInfo != null) {
                dataSource.user = userInfo.user
                dataSource.password = userInfo.password
            }

            dataSource.url = uri
            connected = true
        }
    }

    fun migrate(): Unit {
        val flyway = Flyway();
        flyway.dataSource = dataSource
        flyway.migrate()
    }
}

