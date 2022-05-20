package com.project.githubuser.ui.main

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.project.githubuser.R
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchView() {
        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.search_src_text)).perform(typeText("labib"))
        onView(withId(R.id.search)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
    }
}