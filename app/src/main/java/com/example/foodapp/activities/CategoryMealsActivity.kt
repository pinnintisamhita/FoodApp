package com.example.foodapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapters.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.viewModel.CategoryMealsViewModel
import com.example.foodapp.viewModel.MealViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryMealsAdapter:CategoryMealsAdapter
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

         categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
         categoryMealsViewModel.observeMealsLiveData().observe(this,Observer{mealsList->
             binding.tvCategoryCount.text = mealsList.size.toString()
              categoryMealsAdapter.setMealsList(mealsList)


         })

    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}