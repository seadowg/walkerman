package com.seadowg.walkerman

import com.seadowg.walkerman.database.UserInfo
import org.json.JSONObject
import java.net.URI

class Environment(val systemEnv: Map<String, String>) {
    fun port(): Int {
        return Integer.parseInt(System.getenv().get("PORT") ?: "9000")
    }

    fun jdbcUri(): String {
        val dbUri = URI.create(elephantSqlUriString(System.getenv()) ?: "postgres://localhost:5432/walkerman")
        return "jdbc:postgresql://${dbUri.getHost()}:${dbUri.getPort()}${dbUri.getPath()}"
    }

    fun jdbcUserInfo(): UserInfo? {
        val dbUri = URI.create(elephantSqlUriString(System.getenv()) ?: "postgres://postgres:mysecretpassword@localhost:5432/walkerman")
        val userAndPassword = dbUri.userInfo?.split(":")

        if (userAndPassword != null) {
            return UserInfo(userAndPassword[0], userAndPassword[1])
        } else {
            return null
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
