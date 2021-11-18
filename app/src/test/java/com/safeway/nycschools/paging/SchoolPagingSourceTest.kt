package com.safeway.nycschools.paging

import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSource.LoadResult.Page
import com.safeway.nycschools.data.model.School
import com.safeway.nycschools.data.remote.NYCSchoolsService
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import retrofit2.Response
import kotlin.test.assertEquals

class SchoolPagingSourceTest {

    @Before
    fun setUp() {
//        MockServer.server.start()
//        MockServer.server.dispatcher = MockServer.dispatcher()
    }

    @Test
    fun `Load returns page when on successful load of ItemKeyedData`() = runBlockingTest {

        val apiService = mock<NYCSchoolsService>()
        val pagingSource = SchoolPagingSource(apiService)

        val mockSchoolList = listOf(
            School(dbn = "21K728", schoolName = "Liberation Diploma Plus High School"),
            School(dbn = "02M260", schoolName = "Clinton School Writers & Artists, M.S. 260")
        )

        apiService.stub {
            onBlocking { getSchools("s3k6-pzi2") } doReturn Response.success(mockSchoolList)
        }

        assertEquals(
            expected = Page(
                data = listOf(mockSchoolList[0], mockSchoolList[1]),
                prevKey = null,
                nextKey = 1
            ),
            actual = pagingSource.load(
                Refresh(
                    key = null,
                    loadSize = 50,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun getRefreshKey() {
    }

    @After
    fun tearDown() {
//        MockServer.server.shutdown()
    }

}