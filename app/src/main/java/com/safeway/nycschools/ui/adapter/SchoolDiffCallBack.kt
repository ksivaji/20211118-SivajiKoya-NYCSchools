package com.safeway.nycschools.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.safeway.nycschools.data.model.School

class SchoolDiffCallBack : DiffUtil.ItemCallback<School>() {
    override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
        return oldItem.dbn == newItem.dbn
    }

    override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
        return oldItem == newItem
    }

}
