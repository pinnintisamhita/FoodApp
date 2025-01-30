package com.example.foodapp.activities

import  android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName: String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMVVM:MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealMVVM = ViewModelProvider(this)[MealViewModel::class.java]
        getMealInformationFromIntent()

        setInformationInViews()
        mealMVVM.getMealDetail(mealId)
        observerMealDetailsLiveData()
    }
    private fun observerMealDetailsLiveData()
    {
        mealMVVM.observerMealDetailsLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {

                val meal = value
                binding.tvCategory.text = "Category : ${meal.strCategory}"
                binding.tvArea.text = "Area :${meal.strArea}"
                binding.tvInstructionsSteps.text = meal.strInstructions
            }



        })
    }
    private fun setInformationInViews()
    {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }
    private fun getMealInformationFromIntent(){
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
}