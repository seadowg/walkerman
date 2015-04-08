import org.junit.Before
import org.junit.After
import org.junit.Test

import org.fluentlenium.adapter.FluentTest
import kotlin.test.assertTrue
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.assertj.core.api.Assertions.*
import spark.SparkBase
import org.junit.BeforeClass
import org.junit.AfterClass
import java.sql.DriverManager
import org.postgresql.ds.PGPoolingDataSource
import org.skife.jdbi.v2.DBI

class RsvpTest : FluentTest() {
    Before fun setup() {
        com.seadowg.walkerman.application.main(array<String>())

        val dataSource = PGPoolingDataSource()
        dataSource.setUrl("jdbc:postgresql://localhost:5432/walkerman")

        DBI(dataSource).open().use { db ->
            db.createStatement("delete from rsvps").execute()
        }
    }

    After fun teardown() {
        SparkBase.stop();
    }

    Test fun canRsvp() {
        val email = "michael@fassbender.com"
        val name = "Michael Fassbender"

        goTo("http://localhost:9000")
        click("#create_rsvp")
        fill("#rsvp_email").with(email)
        fill("#rsvp_name").with(name)
        submit("#rsvp_submit");

        goTo("http://localhost:9000/rsvps.csv")
        val csv = pageSource()
        assertThat(csv).contains(email)
        assertThat(csv).contains(name)
        assertThat(csv).contains("1")
    }

    Test fun canRsvp_withGuests() {
        val email = "gatecrasher@fassbender.com"
        val name = "Gate Crasher"
        val guests = 4

        goTo("http://localhost:9000")
        click("#create_rsvp")
        fill("#rsvp_email").with(email)
        fill("#rsvp_name").with(name)

        find("#rsvp_guests_yes").click()
        fill("#rsvp_guests").with(guests.toString())

        submit("#rsvp_submit");

        goTo("http://localhost:9000/rsvps.csv")
        val csv = pageSource()
        assertThat(csv).contains(email)
        assertThat(csv).contains(name)
        assertThat(csv).contains((guests + 1).toString())
    }

    Test fun whenChoosingNotToBringGuests_itDoesNotAddPreviouslyAddedGuests() {
        val email = "billynomates@fassbender.com"
        val name = "Billy No-Mates"
        val guests = 7

        goTo("http://localhost:9000")
        click("#create_rsvp")
        fill("#rsvp_email").with(email)
        fill("#rsvp_name").with(name)

        find("#rsvp_guests_yes").click()
        fill("#rsvp_guests").with(guests.toString())

        find("#rsvp_guests_no").click()

        submit("#rsvp_submit");

        goTo("http://localhost:9000/rsvps.csv")
        val csv = pageSource()
        assertThat(csv).contains(email)
        assertThat(csv).contains(name)
        assertThat(csv).contains("1")
    }

    override fun getDefaultDriver(): WebDriver? {
        val htmlUnitDriver = HtmlUnitDriver()
        htmlUnitDriver.setJavascriptEnabled(true)
        return htmlUnitDriver
    }
}
