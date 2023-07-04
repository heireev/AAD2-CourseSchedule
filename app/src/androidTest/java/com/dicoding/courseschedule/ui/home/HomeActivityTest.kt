package com.dicoding.courseschedule.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.courseschedule.R
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun tapAddCourseMenu_opensAddCourseActivity() {
        onView(withId(R.id.action_add)).perform(click())

        onView(withId(R.id.ib_start_time)).check(matches(isDisplayed()))
        onView(withId(R.id.ed_course_name)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner_day)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_end_time)).check(matches(isDisplayed()))
        onView(withId(R.id.ed_lecturer)).check(matches(isDisplayed()))
        onView(withId(R.id.ed_note)).check(matches(isDisplayed()))
    }
}