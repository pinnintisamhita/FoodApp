package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.adapters.CategoriesAdapter
import com.example.foodapp.adapters.MostPopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.fragments.bottomsheet.MealBottomSheetFragment
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel



class HomeFragment : Fragment() {
   private lateinit var binding:FragmentHomeBinding
   private lateinit var viewModel: HomeViewModel
   private lateinit var randomMeal: Meal
   private lateinit var popularItemsAdapter:MostPopularAdapter
   private lateinit var categoriesAdapter:CategoriesAdapter
   companion object{
       const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
       const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
       const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
       const val CATEGORY_NAME = "com.example.foodapp.fragments.categoryName"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
            popularItemsAdapter = MostPopularAdapter()
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

          preparePopularItemsRecyclerView()
           viewModel.getRandomMeal()
           observerRandomMeal()
           onRandomMealClick()

           viewModel.getPopularItems()
           observePopularItemsLiveData()
           onPopularItemClick()

           prepareCategoriesRecyclerView()
           viewModel.getCategories()
           observeCategoriesLiveData()
           onCategoryClick()

        onPopularItemLongClick()
        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick ={meal->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick ={category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)

        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
           adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {categories->

                 categoriesAdapter.setCategoryList( categories  )


        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {
            meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply{
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }
//we will get the data from the api wheneevr the data changes and put those changes in the data source
    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,
         {
           mealList ->
             popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)


        })
    }

    private fun onRandomMealClick()
    {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun observerRandomMeal()
    {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,{meal ->

                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgRandomMeal)
       this.randomMeal = meal
        })
    }

}