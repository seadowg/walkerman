package com.seadowg.walkerman.database

import org.postgresql.ds.PGPoolingDataSource
import org.json.JSONObject

val dataSource = PGPoolingDataSource()

fun configure(): Unit {
    val env = System.getenv()

    if (env.containsKey("VCAP_SERVICES")) {
        val vcapServices = JSONObject(env.get("VCAP_SERVICES"))
        val postgres = vcapServices.getJSONArray("elephantsql")?.getJSONObject(0)
        val uri = postgres?.getJSONObject("credentials")?.getString("uri")

        if (uri != null) dataSource.setUrl(uri)
    } else {
        dataSource.setUrl("jdbc:postgresql://localhost:5432/walkerman")
    }
}

