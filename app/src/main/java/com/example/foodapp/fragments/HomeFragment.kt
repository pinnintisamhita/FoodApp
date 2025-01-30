package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import com.example.foodapp.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
   private lateinit var binding:FragmentHomeBinding
   private lateinit var homeMvvm:HomeViewModel
   private lateinit var randomMeal: Meal

   companion object{
       const val MEAL_ID = "com.example.foodapp.fragments.idMeal"
       const val MEAL_NAME = "com.example.foodapp.fragments.nameMeal"
       const val MEAL_THUMB = "com.example.foodapp.fragments.thumbMeal"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
               homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
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

           homeMvvm.getRandomMeal()
           observerRandomMeal()
           onRandomMealClick()
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
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,{meal ->

                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.imgRandomMeal)
       this.randomMeal = meal
        })
    }

}