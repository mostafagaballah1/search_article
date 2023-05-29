package com.task.articles_search.ui.fragments.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.task.articles_search.Injection
import com.task.articles_search.data.ArticlesRepository.Companion.MOST_EMAILED_ARTICLE
import com.task.articles_search.data.ArticlesRepository.Companion.MOST_SHARED_ARTICLE
import com.task.articles_search.data.ArticlesRepository.Companion.MOST_VIEWED_ARTICLE
import com.task.articles_search.databinding.FragmentArticlesBinding
import com.task.articles_search.model.Article
import com.task.articles_search.ui.fragments.articles.adapters.ArticlesAdapter
import com.task.articles_search.ui.fragments.articles.adapters.LoadStateAdapter
import com.task.articles_search.ui.fragments.articles.adapters.SearchAdapter
import com.task.articles_search.ui.fragments.articles.listeners.ArticleViewListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticlesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        state: Bundle?
    ): View {

        val binding = FragmentArticlesBinding.inflate(inflater)

        val type = when (arguments?.getInt("type", 0)) {
            MOST_VIEWED_ARTICLE -> ArticlesType.MostViewed
            MOST_SHARED_ARTICLE -> ArticlesType.MostShared
            MOST_EMAILED_ARTICLE -> ArticlesType.MostEmailed
            else -> ArticlesType.Search(arguments?.getString("query", "") ?: "")
        }

        // get the view model
        val viewModel = ViewModelProvider(
            this, Injection.provideArticlesViewModelFactory(
                context = requireContext(),
                owner = this,
                type = type
            )
        )[ArticlesViewModel::class.java]

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        // bind the state
        binding.bindState(
            pagingData = viewModel.getPagingDataFlow(),
            data = { viewModel.getDataFlow() },
        )
        return binding.root
    }

    private fun FragmentArticlesBinding.bindState(
        pagingData: Flow<PagingData<Article>>? = null,
        data: suspend () -> Flow<List<Article>>,
    ) {
        val views = listOf(emptyList, list, progressBar, retryButton)
        pagingData?.let {
            val searchAdapter = SearchAdapter()
            val listener = ArticleViewListener(searchAdapter)
            val header = LoadStateAdapter(listener)
            list.adapter = searchAdapter.withLoadStateHeaderAndFooter(
                header = header,
                footer = LoadStateAdapter(listener)
            )
            bindPaging(
                header = header,
                searchAdapter = searchAdapter,
                listener = listener,
                pagingData = pagingData,
                views = views
            )
        } ?: kotlin.run {
            showView(views, progressBar)
            lifecycleScope.launch {
                data.invoke().catch {
                    showView(views, retryButton)
                }.collect {
                    if (it.isEmpty()) {
                        showView(views, emptyList)
                    } else {
                        showView(views, list)
                        list.adapter = ArticlesAdapter(it)
                    }
                }
            }
        }
    }

    private fun FragmentArticlesBinding.bindPaging(
        header: LoadStateAdapter,
        searchAdapter: SearchAdapter,
        listener: ArticleViewListener,
        pagingData: Flow<PagingData<Article>>,
        views: List<View>
    ) {
        retryButton.setOnClickListener { listener.retry() }

        lifecycleScope.launch {
            pagingData.collectLatest(searchAdapter::submitData)
        }

        lifecycleScope.launch {
            searchAdapter.loadStateFlow.collect { loadState ->

                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && searchAdapter.itemCount > 0 }
                    ?: loadState.prepend

                when {
                    loadState.refresh is LoadState.NotLoading && searchAdapter.itemCount == 0 ->
                        showView(views, emptyList)
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading ->
                        showView(views, list)
                    loadState.mediator?.refresh is LoadState.Loading ->
                        showView(views, progressBar)
                    loadState.mediator?.refresh is LoadState.Error && searchAdapter.itemCount == 0 ->
                        showView(views, retryButton)
                }


                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error

                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showView(views: List<View>, view: View) {
        views.forEach {
            it.isVisible = it == view
        }
    }
}