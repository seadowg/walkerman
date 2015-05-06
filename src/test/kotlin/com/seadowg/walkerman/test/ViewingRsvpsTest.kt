package com.seadowg.walkerman.test

import com.seadowg.walkerman.rsvp.Rsvp
import com.seadowg.walkerman.test.page.NewRsvpPage
import com.seadowg.walkerman.test.page.RsvpsPage
import com.seadowg.walkerman.test.support.bootWalkermanApp
import com.seadowg.walkerman.test.support.clearDatabase
import com.seadowg.walkerman.test.support.shutdownWalkermanApp
import org.fluentlenium.adapter.FluentTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.*;
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class ViewRsvpsTest : FluentTest() {
    Before fun setup() {
        bootWalkermanApp()
        clearDatabase()
    }

    After fun teardown() {
        shutdownWalkermanApp()
    }

    Test fun showsRsvps() {
        val stephen = createRsvp("stephen@curry.com", "Stephen Curry", 1)
        val james = createRsvp("james@harden.com", "James Harden", 3)

        val rsvps = onTheRsvpsPage().pageSource()
        assertThat(rsvps).contains(stephen.email, stephen.name, stephen.guest.toString())
        assertThat(rsvps).contains(james.email, james.name, james.guest.toString())
    }

    private fun onTheRsvpsPage(): RsvpsPage {
        val page = createPage(javaClass<RsvpsPage>())
        page.go()
        return page
    }

    private fun createRsvp(email: String, name: String, totalGuests: Int): Rsvp {
        val newRsvpPage = createPage(javaClass<NewRsvpPage>())
        newRsvpPage.go();

        newRsvpPage.fillInNameAndEmail(email, name)

        if (totalGuests > 1) {
            newRsvpPage.addExtraGuests(totalGuests - 1)
        } else {
            newRsvpPage.removeExtraGuests()
        }

        newRsvpPage.submit();

        return Rsvp(email, name, totalGuests)
    }

    override fun getDefaultDriver(): WebDriver? {
        val htmlUnitDriver = HtmlUnitDriver()
        htmlUnitDriver.setJavascriptEnabled(true)
        return htmlUnitDriver
    }
}
