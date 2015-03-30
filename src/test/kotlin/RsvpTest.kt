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
        var email = "michael@fassbender.com"
        var name = "Michael Fassbender"
        var guests = "5"

        goTo("http://localhost:9000")
        click("#create_rsvp")
        fill("#rsvp_email").with(email)
        fill("#rsvp_name").with(name)
        fill("#rsvp_guests").with(guests)
        submit("#rsvp_submit");

        goTo("http://localhost:9000/rsvps.csv")
        val csv = pageSource()
        assertThat(csv).contains(email)
        assertThat(csv).contains(name)
        assertThat(csv).contains(guests)
    }

    override fun getDefaultDriver(): WebDriver? {
        return HtmlUnitDriver()
    }
}
