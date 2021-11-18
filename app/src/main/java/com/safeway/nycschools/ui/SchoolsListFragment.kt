package com.safeway.nycschools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.safeway.nycschools.databinding.SchoolsListFragmentBinding
import com.safeway.nycschools.ui.adapter.SchoolLoadStateAdapter
import com.safeway.nycschools.ui.adapter.SchoolsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SchoolsListFragment : Fragment() {

    private lateinit var binding: SchoolsListFragmentBinding
    private val schoolsListViewModel: SchoolsListViewModel by viewModels()
    private var schoolListAdapter: SchoolsListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SchoolsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectUiState()
    }

    private fun initView() {
        val appBarConfig = AppBarConfiguration(findNavController().graph)
        binding.toolbar.setupWithNavController(findNavController(), appBarConfig)

        schoolListAdapter = SchoolsListAdapter()
        binding.rvSchools.apply {
            adapter = schoolListAdapter?.withLoadStateHeaderAndFooter(
                header = SchoolLoadStateAdapter { schoolListAdapter?.retry() },
                footer = SchoolLoadStateAdapter { schoolListAdapter?.retry() }
            )
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        schoolListAdapter?.addLoadStateListener { loadState -> renderUi(loadState) }
        binding.btnSchoolsRetry.setOnClickListener { schoolListAdapter?.retry() }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            schoolsListViewModel.getSchoolsList().collectLatest { schoolList ->
                schoolListAdapter?.submitData(schoolList)
            }
        }
    }

    private fun renderUi(loadState: CombinedLoadStates) {
        val isListEmpty =
            loadState.refresh is LoadState.NotLoading && schoolListAdapter?.itemCount == 0

        binding.rvSchools.isVisible = !isListEmpty
        binding.tvSchoolsEmpty.isVisible = isListEmpty

        // Only shows the list if refresh succeeds.
        binding.rvSchools.isVisible = loadState.source.refresh is LoadState.NotLoading
        // Show loading spinner during initial load or refresh.
        binding.progressBarSchools.isVisible = loadState.source.refresh is LoadState.Loading
        // Show the retry state if initial load or refresh fails.
        binding.btnSchoolsRetry.isVisible = loadState.source.refresh is LoadState.Error
    }

}