package com.task.ui.component.news.adapter

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.task.R
import com.task.data.dto.news.NewsItem
import com.task.databinding.RecipeItemBinding
import com.task.ui.base.listeners.RecyclerItemListener
import com.task.utils.loadImage

/**
 * Created by Eldho
 */

class NewsViewHolder(private val itemBinding: RecipeItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(newsItem: NewsItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvCaption.text = newsItem.description
        itemBinding.tvName.text = newsItem.title
        itemBinding.ivRecipeItemImage.loadImage(newsItem.urlToImage.toString())
//        Picasso.get().load(newsItem.urlToImage).placeholder(R.drawable.ic_healthy_food).error(R.drawable.ic_healthy_food).into(itemBinding.ivRecipeItemImage)
        itemBinding.rlRecipeItem.setOnClickListener { recyclerItemListener.onItemSelected(newsItem) }
    }
}

