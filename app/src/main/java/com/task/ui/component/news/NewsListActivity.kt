package com.task.ui.component.news

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.task.R
import com.task.NEWS_ITEM_KEY
import com.task.data.Resource
import com.task.data.dto.news.News
import com.task.data.dto.news.NewsItem
import com.task.data.error.SEARCH_ERROR
import com.task.databinding.HomeActivityBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.details.DetailsActivity
import com.task.ui.component.news.adapter.NewsAdapter
import com.task.utils.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Eldho
 */
@AndroidEntryPoint
class NewsListActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding

    private val newsListViewModel: NewsListViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.recipe)
        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipesList.layoutManager = layoutManager
        binding.rvRecipesList.setHasFixedSize(true)
        newsListViewModel.getRecipes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions, menu)
        // Associate searchable configuration with the SearchView
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by_name)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> newsListViewModel.getRecipes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleSearch(query: String) {
        if (query.isNotEmpty()) {
            binding.pbLoading.visibility = VISIBLE
            newsListViewModel.onSearchClick(query)
        }
    }


    private fun bindListData(news: News) {
        if (!(news.articles.isNullOrEmpty())) {
            newsAdapter = NewsAdapter(newsListViewModel, news.articles)
            binding.rvRecipesList.adapter = newsAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<NewsItem>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(NEWS_ITEM_KEY, it)
            }
            startActivity(nextScreenIntent)
        }
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        newsListViewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvRecipesList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvRecipesList.toGone()
    }


    private fun showSearchResult(newsItem: NewsItem) {
        newsListViewModel.openRecipeDetails(newsItem)
        binding.pbLoading.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.pbLoading.toGone()
    }

    private fun handleRecipesList(status: Resource<News>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(news = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { newsListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(newsListViewModel.newsLiveData, ::handleRecipesList)
        observe(newsListViewModel.recipeSearchFound, ::showSearchResult)
        observe(newsListViewModel.noSearchFound, ::noSearchResult)
        observeEvent(newsListViewModel.openRecipeDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(newsListViewModel.showSnackBar)
        observeToast(newsListViewModel.showToast)

    }
}
