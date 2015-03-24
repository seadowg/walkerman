package com.seadowg.walkerman.database

import org.postgresql.ds.PGPoolingDataSource
import org.json.JSONObject
import javax.sql.DataSource
import java.net.URI
import org.flywaydb.core.Flyway

val dataSource = PGPoolingDataSource()

fun configure(): Unit {
    connect()
    migrate()
}

private fun connect(): Unit {
    val dbUri = URI.create(elephantSqlUriString(System.getenv()) ?: "postgres://localhost:5432/walkerman")
    dataSource.setUrl("jdbc:postgresql://${dbUri.getHost()}:${dbUri.getPort()}${dbUri.getPath()}")

    if (dbUri.getUserInfo() != null) {
        dataSource.setUser(dbUri.getUserInfo().split(":").get(0))
        dataSource.setPassword(dbUri.getUserInfo().split(":").get(1))
    }
}

private fun migrate(): Unit {
    val flyway = Flyway();
    flyway.setDataSource(dataSource)
    flyway.migrate()
}

private fun elephantSqlUriString(env: Map<String, String>): String? {
    if (env.containsKey("VCAP_SERVICES")) {
        val vcapServices = JSONObject(env.get("VCAP_SERVICES"))
        val postgres = vcapServices.getJSONArray("elephantsql")?.getJSONObject(0)
        return postgres?.getJSONObject("credentials")?.getString("uri")
    } else {
        return null
    }
}

