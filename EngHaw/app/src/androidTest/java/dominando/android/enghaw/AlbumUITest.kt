package dominando.android.enghaw

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import dominando.android.IdleResource
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumUITest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.activity.deleteDatabase("engHawDb")
    }
    @Test
    fun test_add_favorite() {
        val tagRecyclerWeb = "web"
        val tagRecyclerFavorite = "fav"
        val albumTitleToBeClicked = "Minuano"
        IdlingRegistry.getInstance().register(IdleResource.instance)
        Espresso.onView(ViewMatchers.withTagValue(Matchers.`is`(tagRecyclerWeb as Any)))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(ViewMatchers.hasDescendant(ViewMatchers.withText(albumTitleToBeClicked))))
        Espresso.onView(ViewMatchers.withTagValue(Matchers.`is`(tagRecyclerWeb as Any)))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(ViewMatchers.hasDescendant(ViewMatchers.withText(albumTitleToBeClicked)), ViewActions.click()))
        Espresso.onView(ViewMatchers.withId(R.id.txtTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(albumTitleToBeClicked)))
        Espresso.onView(ViewMatchers.withId(R.id.fabFavorite))
            .perform(ViewActions.click())
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.viewPager))
            .perform(ViewActions.swipeLeft())
        Espresso.onView(ViewMatchers.withTagValue(Matchers.`is`(tagRecyclerFavorite as Any)))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(albumTitleToBeClicked))))
    }
}