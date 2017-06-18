package com.seadowg.walkerman.test

import com.seadowg.walkerman.test.page.NewRsvpPage
import com.seadowg.walkerman.test.page.RsvpsCsv
import com.seadowg.walkerman.test.support.bootWalkermanApp
import com.seadowg.walkerman.test.support.clearDatabase
import com.seadowg.walkerman.test.support.shutdownWalkermanApp
import org.assertj.core.api.Assertions.assertThat
import org.fluentlenium.adapter.FluentTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class CreatingRsvpsTest : FluentTest() {

    @Before
    fun setup() {
        bootWalkermanApp()
        clearDatabase()
    }

    @After
    fun teardown() {
        shutdownWalkermanApp()
    }

    @Test
    fun canRsvp() {
        val email = "michael@fassbender.com"
        val name = "Michael Fassbender"

        val newRsvpPage = onTheNewRsvpPage()
        newRsvpPage.fillInNameAndEmail(email, name)
        newRsvpPage.submit()

        val csv = onThRsvpsCsv().pageSource();
        assertThat(csv).contains(email, name, "1")
    }

    @Test
    fun canRsvp_withGuests() {
        val email = "gatecrasher@fassbender.com"
        val name = "Gate Crasher"
        val guests = 4

        val newRsvpPage = onTheNewRsvpPage()
        newRsvpPage.fillInNameAndEmail(email, name)
        newRsvpPage.addExtraGuests(guests)
        newRsvpPage.submit()

        val csv = onThRsvpsCsv().pageSource()
        assertThat(csv).contains(email, name, (guests + 1).toString())
    }

    @Test
    fun whenChoosingNotToBringGuests_itDoesNotAddPreviouslyAddedGuests() {
        val email = "billynomates@fassbender.com"
        val name = "Billy No-Mates"
        val guests = 7

        val newRsvpPage = onTheNewRsvpPage()
        newRsvpPage.fillInNameAndEmail(email, name)
        newRsvpPage.addExtraGuests(guests)
        newRsvpPage.removeExtraGuests()
        newRsvpPage.submit()

        val csv = onThRsvpsCsv().pageSource();
        assertThat(csv).contains(email, name, "1")
    }

    @Test
    fun whenEmailAlreadyUsed_itShowsTheUserAnerror() {
        val email = "repeatoffender@forgetful.com"
        val name = "James Forgetful"

        val firstNewRsvpPage = onTheNewRsvpPage()
        firstNewRsvpPage.fillInNameAndEmail(email, name)
        firstNewRsvpPage.submit()

        val secondNewRsvpPage = onTheNewRsvpPage()
        secondNewRsvpPage.fillInNameAndEmail(email, name)
        secondNewRsvpPage.submit()

        assertThat(secondNewRsvpPage.pageSource()).contains("already RSVP'd with that email")
    }

    private fun onTheNewRsvpPage(): NewRsvpPage {
        val page = createPage(NewRsvpPage::class.java)
        page.go()
        return page
    }

    private fun onThRsvpsCsv(): RsvpsCsv {
        val page = createPage(RsvpsCsv::class.java)
        page.go()
        return page
    }

    override fun getDefaultDriver(): WebDriver? {
        val htmlUnitDriver = HtmlUnitDriver()
        htmlUnitDriver.isJavascriptEnabled = true
        return htmlUnitDriver
    }
}