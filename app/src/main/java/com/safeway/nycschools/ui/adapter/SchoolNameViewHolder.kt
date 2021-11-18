package com.safeway.nycschools.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.safeway.nycschools.data.model.School
import com.safeway.nycschools.databinding.ItemSchoolNameBinding

class SchoolNameViewHolder(
    val binding: ItemSchoolNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(school: School?) {
        binding.schoolName.text = school?.schoolName.orEmpty()
    }
}
