package com.example.foodapp.retrofit

import com.example.foodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    //tells the retrofit that need to get data from the Api
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}