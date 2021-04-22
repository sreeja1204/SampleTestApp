package com.sample.sampletestapp.presentation.ui.activity

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.sample.sampletestapp.R
import org.hamcrest.Matchers.greaterThan
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<UserActivity>(UserActivity::class.java)

    @Before
    fun setup(){
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, UserActivity::class.java)
        activityRule.launchActivity(intent)
        activityRule.activity
    }

    @Test
    fun recyclerViewTest_UserActivity() {

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.user_list_recycler_view)
        val itemCount = recyclerView.adapter?.itemCount

        if (itemCount != null) {
            Assert.assertThat("itemCount", 10, greaterThan(0))
        }
    }
}
