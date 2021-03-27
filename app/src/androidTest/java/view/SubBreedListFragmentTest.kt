package view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.view.subbreed.SubBreedFragment
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SubBreedListFragmentTest {

    @Test
    fun testNavigationToSubBreedListFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_graph)

        val titleScenario = launchFragmentInContainer<SubBreedFragment>()

        titleScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.subBreedList)).perform(ViewActions.click())
        Assert.assertEquals(navController.currentDestination?.id, R.id.breedDetailFragment)
    }
}