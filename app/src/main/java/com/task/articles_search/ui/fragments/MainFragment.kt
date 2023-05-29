package com.task.articles_search.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.task.articles_search.R
import com.task.articles_search.data.ArticlesRepository.Companion.MOST_EMAILED_ARTICLE
import com.task.articles_search.data.ArticlesRepository.Companion.MOST_SHARED_ARTICLE
import com.task.articles_search.data.ArticlesRepository.Companion.MOST_VIEWED_ARTICLE
import com.task.articles_search.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        state: Bundle?
    ): View {

        var binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.searchBtn.content.setOnClickListener {
            findNavController().navigate(R.id.action_to_SearchFragment)
        }
        binding.viewedBtn.text.text = resources.getString(R.string.most_viewed)
        binding.viewedBtn.content.setOnClickListener {
            findNavController().navigate(
                R.id.action_to_ArticleFragment,
                bundleOf("type" to MOST_VIEWED_ARTICLE)
            )
        }
        binding.sharedBtn.text.text = resources.getString(R.string.most_shared)
        binding.sharedBtn.content.setOnClickListener {
            findNavController().navigate(
                R.id.action_to_ArticleFragment,
                bundleOf("type" to MOST_SHARED_ARTICLE)
            )
        }
        binding.emailedBtn.text.text = resources.getString(R.string.most_emailed)
        binding.emailedBtn.content.setOnClickListener {
            findNavController().navigate(
                R.id.action_to_ArticleFragment,
                bundleOf("type" to MOST_EMAILED_ARTICLE)
            )
        }

        return binding.root
    }
}