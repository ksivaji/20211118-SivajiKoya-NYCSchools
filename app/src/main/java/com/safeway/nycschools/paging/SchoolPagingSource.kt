package com.safeway.nycschools.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.safeway.nycschools.data.model.School
import com.safeway.nycschools.data.remote.NYCSchoolsService
import com.safeway.nycschools.repository.SchoolRepository.Companion.NETWORK_PAGE_SIZE

class SchoolPagingSource(
    private val nycSchoolsService: NYCSchoolsService
) : PagingSource<Int, School>() {

    companion object {
        private const val STARTING_OFF_SET = 0
        private const val SCHOOL_LIST_DATA_SET_ID = "s3k6-pzi2"
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, School> {
        val position = params.key ?: STARTING_OFF_SET
        return try {
            val response = nycSchoolsService.getSchools(SCHOOL_LIST_DATA_SET_ID) //TODO: check this
            val schoolList = response.body() ?: listOf()
            val nextKey = if (schoolList.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = schoolList,
                prevKey = if (position == STARTING_OFF_SET) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, School>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}