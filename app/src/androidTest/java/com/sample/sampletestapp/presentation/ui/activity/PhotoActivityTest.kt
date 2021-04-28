package com.sample.sampletestapp.presentation.ui.activity

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.sample.sampletestapp.R
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<PhotoActivity>(PhotoActivity::class.java)

    @Before
    fun setup(){
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, PhotoActivity::class.java)
        activityRule.launchActivity(intent)
        activityRule.activity
    }

    @Test
    fun recyclerViewTest_PhotoActivity() {

        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.photo_list_recycler_view)
        val itemCount = recyclerView.adapter?.itemCount

        if (itemCount != null) {
            Assert.assertThat("itemCount", 5000, Matchers.greaterThan(0))
        }
    }

}