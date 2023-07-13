package com.task.ui.component.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.data.dto.news.NewsItem
import com.task.databinding.RecipeItemBinding
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.ui.component.news.NewsListViewModel

/**
 * Created by Eldho
 */

class NewsAdapter(private val newsListViewModel: NewsListViewModel, private val recipes: List<NewsItem>) : RecyclerView.Adapter<NewsViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(recipe: NewsItem) {
            newsListViewModel.openRecipeDetails(recipe)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemBinding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(recipes[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}

