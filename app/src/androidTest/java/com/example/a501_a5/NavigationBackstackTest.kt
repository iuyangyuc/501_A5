package com.example.a501_a5

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationBackstackTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bottomNavigationBackStack_behavesAsSingleTop() {
        composeRule.onNodeWithContentDescription("Tasks").performClick()
        composeRule.onNodeWithText("New task").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Calendar").performClick()
        composeRule.onNodeWithText("Tap a day to set a reminder anchor.").assertIsDisplayed()

        composeRule.activityRule.scenario.onActivity {
            it.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.onNodeWithText("Add a note").assertIsDisplayed()
    }
}
