import org.junit.Before
import org.junit.After
import org.junit.Test

import org.fluentlenium.adapter.FluentTest
import kotlin.test.assertTrue
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.assertj.core.api.Assertions.*

public class HomePageTest : FluentTest() {
    Before fun setup() {
        goTo("http://localhost:9000")
    }

    Test fun showsTitle() {
        assertThat(pageSource()).contains("Walkerman")
    }

    override fun getDefaultDriver(): WebDriver? {
        return HtmlUnitDriver()
    }
}
