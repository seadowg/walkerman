package com.seadowg.walkerman.http;

import java.net.URLDecoder.decode;
import spark.Request

class NiceRequest(val request: Request) {
  public fun params(): Map<String, String> {
    val formBody = request.body()

    return formBody.split("&").map { paramString ->
        paramString.split("=")

    }.fold(hashMapOf<String, String>(), { map, param ->
        map.put(param[0], decode(param[1], "UTF-8"))
        map
    })
  }
}
