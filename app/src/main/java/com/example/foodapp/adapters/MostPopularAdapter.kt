package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemsBinding
import com.example.foodapp.pojo.MealsByCategory

import kotlin.collections.ArrayList

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    var onLongItemClick:((MealsByCategory)->Unit)?=null
    private var mealsList = ArrayList<MealsByCategory>()

    fun setMeals(mealsList :ArrayList<MealsByCategory>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgPopularMealItem)
        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }
        holder.itemView.setOnLongClickListener{
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }
    class PopularMealViewHolder(val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)
}