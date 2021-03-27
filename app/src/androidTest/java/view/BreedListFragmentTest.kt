package view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yashwant.doggo_api_ui.R
import com.yashwant.doggo_api_ui.view.breedlist.BreedListFragment
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BreedListFragmentTest {


    @Test
    fun testNavigationToSubBreedListFragment() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        navController.setGraph(R.navigation.nav_graph)

        val titleScenario = launchFragmentInContainer<BreedListFragment>()

        titleScenario.onFragment { fragment ->
            fragment.breedListViewModel.bind()
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        Thread.sleep(1000)
        onView(ViewMatchers.withId(R.id.breedList)).perform(ViewActions.click())
        Assert.assertEquals(navController.currentDestination?.id, R.id.subBreedFragment)
    }
}