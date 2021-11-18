package com.safeway.nycschools.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class SchoolLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<SchoolLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: SchoolLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): SchoolLoadStateViewHolder {
        return SchoolLoadStateViewHolder.create(parent, retry)
    }
}