package com.safeway.nycschools.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.safeway.nycschools.R
import com.safeway.nycschools.databinding.ItemLoadStateFooterBinding

class SchoolLoadStateViewHolder(
    private val binding: ItemLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvErrorDescription.text = loadState.error.localizedMessage
        }
        binding.progressLoadMore.isVisible = loadState is LoadState.Loading
        binding.btnRetry.isVisible = loadState is LoadState.Error
        binding.tvErrorDescription.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): SchoolLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load_state_footer, parent, false)
            val binding = ItemLoadStateFooterBinding.bind(view)
            return SchoolLoadStateViewHolder(binding, retry)
        }
    }
}