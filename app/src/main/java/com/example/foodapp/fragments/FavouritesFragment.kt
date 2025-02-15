package com.example.foodapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapters.FavoritesMealsAdapter
import com.example.foodapp.databinding.FragmentFavouritesBinding
import com.example.foodapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavouritesFragment : Fragment() {
    private lateinit var binding:FragmentFavouritesBinding
    private lateinit var viewmodel:HomeViewModel
    private lateinit var favoritesAdapter:FavoritesMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentFavouritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewmodel.deleteMeal(favoritesAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction("Undo",
                    View.OnClickListener {
                        viewmodel.insertMeal(favoritesAdapter.differ.currentList[position])
                    }).show()
            }

        }
            ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)

    }

    private fun prepareRecyclerView() {
       favoritesAdapter = FavoritesMealsAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        viewmodel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer {
            meals ->
            favoritesAdapter.differ.submitList(meals)
        })
    }


}