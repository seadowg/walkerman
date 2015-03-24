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
        val dataSource = PGPoolingDataSource()
        dataSource.setUrl("jdbc:postgresql://localhost:5432/walkerman")

        DBI(dataSource).open().use { db ->
            db.createStatement("delete from rsvps").execute()
        }

        com.seadowg.walkerman.application.main(array<String>())
    }

    After fun teardown() {
        SparkBase.stop();
    }

    Test fun canRsvp() {
        goTo("http://localhost:9000")
        click("#create_rsvp")
        fill("#rsvp_email").with("michael@fassbender.com")
        submit("#rsvp_submit");
        assertThat(pageSource()).contains("Thank you")

        goTo("http://localhost:9000/rsvps.csv")
        assertThat(pageSource()).contains("michael@fassbender.com")
    }

    override fun getDefaultDriver(): WebDriver? {
        return HtmlUnitDriver()
    }
}
