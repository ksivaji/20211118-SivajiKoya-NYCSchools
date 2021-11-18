package com.safeway.nycschools.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import com.safeway.nycschools.data.model.School
import com.safeway.nycschools.databinding.ItemSchoolNameBinding
import com.safeway.nycschools.ui.SchoolsListFragmentDirections

class SchoolsListAdapter : PagingDataAdapter<School, SchoolNameViewHolder>(SchoolDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolNameViewHolder {
        val holder = SchoolNameViewHolder(
            ItemSchoolNameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        holder.binding.root.setOnClickListener { view ->
            getItem(holder.absoluteAdapterPosition)?.let { school ->
                view.findNavController().navigate(
                    SchoolsListFragmentDirections.actonGoToSchoolDetail(
                        schoolDbnId = school.dbn.orEmpty(),
                        toolbarTitle = school.schoolName.orEmpty()
                    )
                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: SchoolNameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}