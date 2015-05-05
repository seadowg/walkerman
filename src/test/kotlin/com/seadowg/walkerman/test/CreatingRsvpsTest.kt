package com.seadowg.walkerman.test

import com.seadowg.walkerman.application.main
import com.seadowg.walkerman.test.page.NewRsvpPage
import com.seadowg.walkerman.test.page.RsvpsCsv
import org.fluentlenium.adapter.FluentTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.postgresql.ds.PGPoolingDataSource
import org.skife.jdbi.v2.DBI
import spark.SparkBase
import org.assertj.core.api.Assertions.*;

class CreatingRsvpsTest : FluentTest() {
    Before fun setup() {
        bootWalkermanApp()
        clearDatabase()
    }

    After fun teardown() {
        SparkBase.stop();
    }

    Test fun canRsvp() {
        val email = "michael@fassbender.com"
        val name = "Michael Fassbender"

        val newRsvpPage = onTheNewRsvpPage()
        newRsvpPage.fillInNameAndEmail(email, name)
        newRsvpPage.submit();

        val csv = onThRsvpsCsv().pageSource();
        assertThat(csv).contains(email, name, "1")
    }

    Test fun canRsvp_withGuests() {
        val email = "gatecrasher@fassbender.com"
        val name = "Gate Crasher"
        val guests = 4

        val newRsvpPage = onTheNewRsvpPage()
        newRsvpPage.fillInNameAndEmail(email, name)
        newRsvpPage.addExtraGuests(guests)
        newRsvpPage.submit()

        val csv = onThRsvpsCsv().pageSource();
        assertThat(csv).contains(email, name, (guests + 1).toString())
    }

    Test fun whenChoosingNotToBringGuests_itDoesNotAddPreviouslyAddedGuests() {
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

    Test fun whenEmailAlreadyUsed_itShowsTheUserAnerror() {
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
        val page = createPage(javaClass<NewRsvpPage>())
        page.go()
        return page
    }

    private fun onThRsvpsCsv(): RsvpsCsv {
        val page = createPage(javaClass<RsvpsCsv>())
        page.go()
        return page
    }

    private fun clearDatabase() {
        val dataSource = PGPoolingDataSource()
        dataSource.setUrl("jdbc:postgresql://localhost:5432/walkerman")

        DBI(dataSource).open().use { db ->
            db.createStatement("delete from rsvps").execute()
        }
    }

    private fun bootWalkermanApp() {
        main(array<String>())
    }

    override fun getDefaultDriver(): WebDriver? {
        val htmlUnitDriver = HtmlUnitDriver()
        htmlUnitDriver.setJavascriptEnabled(true)
        return htmlUnitDriver
    }
}