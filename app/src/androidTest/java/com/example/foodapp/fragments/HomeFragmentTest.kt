package com.example.foodapp.fragments

import android.provider.ContactsContract
import android.provider.ContactsContract.Intents
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.foodapp.R
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.adapters.CategoriesAdapter
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class HomeFragmentTest{
      @get:Rule
      val activitySecnarioRule = ActivityScenarioRule(MainActivity::class.java)
   @Test
   fun clickRandomMealCard_startMealActivity()
   {
       androidx.test.espresso.intent.Intents.init()
       onView(withId(R.id.random_meal_card)).perform(click())
       intended(hasComponent(MealActivity::class.java.name))
       androidx.test.espresso.intent.Intents.release()

   }
  fun categoryClick_startCategoryMealsActivity()
  {
        androidx.test.espresso.intent.Intents.init()
        onView(withId(R.id.rec_view_categories)).perform(RecyclerViewActions.actionOnItemAtPosition<CategoriesAdapter.CategoryViewHolder>(0, click()))
        intended(hasComponent(CategoryMealsActivity::class.java.name))
        androidx.test.espresso.intent.Intents.release()

  }


}