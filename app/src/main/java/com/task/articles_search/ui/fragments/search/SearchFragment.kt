package com.task.articles_search.ui.fragments.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.task.articles_search.Injection
import com.task.articles_search.R
import com.task.articles_search.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        state: Bundle?
    ): View {

        val binding = FragmentSearchBinding.inflate(inflater, container, false)


        binding.bindSearch(viewModel.getCurrentQuery())

        return binding.root

    }

    private fun FragmentSearchBinding.bindSearch(
        lastQuery: String
    ) {
        searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                onSearch()
                true
            } else {
                false
            }
        }
        searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                onSearch()
                true
            } else {
                false
            }
        }

        searchBtn.setOnClickListener {
            onSearch()
        }

        searchRepo.setText(lastQuery)
    }

    private fun FragmentSearchBinding.onSearch() {
        searchRepo.text?.trim()?.let {
            if (it.isNotEmpty()) {
                findNavController().navigate(
                    R.id.action_to_ArticleFragment,
                    bundleOf("query" to it.toString())
                )
            }
        }
    }
}