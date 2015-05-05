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

        val newRsvpPage = onTheNewRsvpPage()
        newRsvpPage.fillInNameAndEmail(email, name)
        newRsvpPage.submit();

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

        val newRsvpPage = onTheNewRsvpPage()
        newRsvpPage.fillInNameAndEmail(email, name)
        newRsvpPage.addExtraGuests(guests)
        newRsvpPage.submit()

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

        val newRsvpPage = onTheNewRsvpPage()
        newRsvpPage.fillInNameAndEmail(email, name)
        newRsvpPage.addExtraGuests(guests)
        newRsvpPage.removeExtraGuests()
        newRsvpPage.submit()

        goTo("http://localhost:9000/rsvps.csv")
        val csv = pageSource()
        assertThat(csv).contains(email)
        assertThat(csv).contains(name)
        assertThat(csv).contains("1")
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

        assertThat(pageSource()).contains("already RSVP'd with that email")
    }

    private fun onTheNewRsvpPage(): NewRsvpPage {
        val newRsvpPage = createPage(javaClass<NewRsvpPage>())
        newRsvpPage.go()
        return newRsvpPage
    }

    override fun getDefaultDriver(): WebDriver? {
        val htmlUnitDriver = HtmlUnitDriver()
        htmlUnitDriver.setJavascriptEnabled(true)
        return htmlUnitDriver
    }
}
