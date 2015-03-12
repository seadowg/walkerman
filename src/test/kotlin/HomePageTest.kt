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

class HomePageTest : FluentTest() {
    Before fun setup() {
        com.seadowg.walkerman.main(array<String>())
    }

    After fun teardown() {
        SparkBase.stop();
    }

    Test fun showsTitle() {
        goTo("http://localhost:9000")
        assertThat(pageSource()).contains("Walkerman")
    }

    override fun getDefaultDriver(): WebDriver? {
        return HtmlUnitDriver()
    }
}
