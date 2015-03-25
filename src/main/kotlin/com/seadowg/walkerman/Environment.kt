package com.seadowg.walkerman

import java.net.URI
import com.seadowg.walkerman.database.dataSource
import org.json.JSONObject
import com.seadowg.walkerman.database.UserInfo

class Environment(val systemEnv: Map<String, String>) {
    fun port(): Int {
        return Integer.parseInt(System.getenv().get("PORT") ?: "9000")
    }

    fun jdbcUri(): String {
        val dbUri = URI.create(elephantSqlUriString(System.getenv()) ?: "postgres://localhost:5432/walkerman")
        return "jdbc:postgresql://${dbUri.getHost()}:${dbUri.getPort()}${dbUri.getPath()}"
    }

    fun jdbcUserInfo(): UserInfo? {
        val dbUri = URI.create(elephantSqlUriString(System.getenv()) ?: "postgres://localhost:5432/walkerman")
        val userAndPassword = dbUri.getUserInfo()?.split(":")

        if (userAndPassword != null) {
            return UserInfo(userAndPassword.get(0), userAndPassword.get(1))
        } else {
            return null;
        }
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
}
