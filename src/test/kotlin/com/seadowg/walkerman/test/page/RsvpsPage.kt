package com.seadowg.walkerman.test.page

import org.fluentlenium.core.FluentPage

public class RsvpsPage : FluentPage() {
    public override fun getUrl(): String {
        return "http://localhost:9000/rsvps"
    }
}
