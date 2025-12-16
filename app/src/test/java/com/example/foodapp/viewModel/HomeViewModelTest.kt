package com.example.foodapp.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Dao
import com.example.foodapp.db.MealDao
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.MealApi
import com.example.foodapp.viewModel.livedata.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() //for Livedata


    private val api = mock(MealApi::class.java)

   @Before
   fun setUp()
   {
       Dispatchers.setMain(testDispatcher)
       MockitoAnnotations.openMocks(this)
      val fakeDatabase = mock(MealDatabase::class.java)
       val fakeDao = mock(MealDao::class.java)
       Mockito.`when`(fakeDatabase.mealDao()).thenReturn(fakeDao)
       viewModel = HomeViewModel(fakeDatabase)


   }
 @After
 fun tearDown() {
  Dispatchers.resetMain()
 }

 @Test
    fun testgetRandomMeal() = runTest{
     val expectedMeal = Meal(
     dateModified = null,
     idMeal = "123",
     strArea = "Indian",
     strCategory = "Main Course",
     strCreativeCommonsConfirmed = null,
     strDrinkAlternate = null,
     strImageSource = null,
     strIngredient1 = "Rice",
     strIngredient2 = "Chicken",
     strIngredient3 = null,
     strIngredient4 = null,
     strIngredient5 = null,
     strIngredient6 = null,
     strIngredient7 = null,
     strIngredient8 = null,
     strIngredient9 = null,
     strIngredient10 = null,
     strIngredient11 = null,
     strIngredient12 = null,
     strIngredient13 = null,
     strIngredient14 = null,
     strIngredient15 = null,
     strIngredient16 = null,
     strIngredient17 = null,
     strIngredient18 = null,
     strIngredient19 = null,
     strIngredient20 = null,
     strInstructions = "Cook rice with chicken and spices.",
     strMeal = "Biryani",
     strMealThumb = "https://example.com/biryani.jpg",
     strMeasure1 = "1 cup",
     strMeasure2 = "200g",
     strMeasure3 = null,
     strMeasure4 = null,
     strMeasure5 = null,
     strMeasure6 = null,
     strMeasure7 = null,
     strMeasure8 = null,
     strMeasure9 = null,
     strMeasure10 = null,
     strMeasure11 = null,
     strMeasure12 = null,
     strMeasure13 = null,
     strMeasure14 = null,
     strMeasure15 = null,
     strMeasure16 = null,
     strMeasure17 = null,
     strMeasure18 = null,
     strMeasure19 = null,
     strMeasure20 = null,
     strSource = "https://example.com",
     strTags = "Spicy,Indian",
     strYoutube = "https://youtube.com/biryani"
 )
     val fakeResponse = MealList(listOf(expectedMeal))

     Mockito.`when`(api.getRandomMeal()).thenReturn(fakeResponse)
     //act
     viewModel.getRandomMeal()
     advanceUntilIdle()
     //assert
     val actualMeal = viewModel.randomMealLiveData.getOrAwaitValue()
     assertEquals(expectedMeal, actualMeal)

    }




}