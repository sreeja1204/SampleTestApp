package com.sample.sampletestapp.presentation.ui.activity

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.sample.sampletestapp.R
import com.sample.sampletestapp.presentation.ui.activity.RecyclerViewAssertions.withRowContaining
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Test


class ActivityTest {


    @Test
    fun recyclerViewTest_UserActivity_Pass() {
            Espresso.onView(ViewMatchers.withId(R.id.user_list_recycler_view))
                .check(withRowContaining(withText("Leanne Graham")));
    }

    @Test
    fun recyclerViewTest_PhotoActivity_Pass() {
            Espresso.onView(ViewMatchers.withId(R.id.photo_list_recycler_view))
                .check(withRowContaining(withText("accusamus beatae ad facilis cum similique qui sunt")));
    }

    @Test
    fun recyclerViewTest_PhotoDetailActivity_Pass() {
            Espresso.onView(ViewMatchers.withId(R.id.photo_detail_list_recycler_view))
                .check(withRowContaining(withText("accusamus beatae ad facilis cum similique qui sunt")));
    }

}

object RecyclerViewAssertions {

    /**
     * Provides a RecyclerView assertion based on a view matcher. This allows you to
     * validate whether a RecyclerView contains a row in memory without scrolling the list.
     *
     * @param viewMatcher - an Espresso ViewMatcher for a descendant of any row in the recycler.
     * @return an Espresso ViewAssertion to bindData against a RecyclerView.
     */
    fun withRowContaining(viewMatcher: Matcher<View>): ViewAssertion {
        assertNotNull(viewMatcher)

        return object : ViewAssertion {

            override fun check(view: View, noViewException: NoMatchingViewException?) {
                if (noViewException != null) {
                    throw noViewException
                }

                assertTrue(view is RecyclerView)

                val recyclerView = view as RecyclerView
                val adapter = recyclerView.adapter
                for (position in 0 until adapter!!.itemCount) {
                    val itemType = adapter.getItemViewType(position)
                    val viewHolder = adapter.createViewHolder(recyclerView, itemType)
                    adapter.bindViewHolder(viewHolder, position)

                    if (viewHolderMatcher(hasDescendant(viewMatcher)).matches(viewHolder)) {
                        return  // Found a matching row
                    }
                }

                fail("No match found")
            }
        }
    }

    /**
     * Creates matcher for view holder with given item view matcher.
     *
     * @param itemViewMatcher a item view matcher which is used to match item.
     * @return a matcher which matches a view holder containing item matching itemViewMatcher.
     */
    private fun viewHolderMatcher(itemViewMatcher: Matcher<View>): Matcher<RecyclerView.ViewHolder> {
        return object : TypeSafeMatcher<RecyclerView.ViewHolder>() {

            override fun matchesSafely(viewHolder: RecyclerView.ViewHolder): Boolean {
                return itemViewMatcher.matches(viewHolder.itemView)
            }

            override fun describeTo(description: Description) {
                description.appendText("holder with view: ")
                itemViewMatcher.describeTo(description)
            }
        }
    }
}