package com.seadowg.walkerman.test.page

import org.fluentlenium.core.FluentPage

class RsvpsCsv : FluentPage() {
    public override fun getUrl(): String {
        return "http://localhost:9000/rsvps.csv"
    }
}