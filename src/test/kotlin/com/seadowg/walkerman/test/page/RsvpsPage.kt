package com.seadowg.walkerman.test.page

import org.fluentlenium.core.FluentPage

class RsvpsPage : FluentPage() {
    override fun getUrl(): String {
        return "http://localhost:9000/events/2017/rsvps"
    }
}
