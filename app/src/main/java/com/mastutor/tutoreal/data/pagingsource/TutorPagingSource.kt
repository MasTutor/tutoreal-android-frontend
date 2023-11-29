package com.mastutor.tutoreal.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mastutor.tutoreal.data.remote.TutorItem
import com.mastutor.tutoreal.data.remote.TutorealApiService

class TutorPagingSource(
    private val tutorealApiService: TutorealApiService,
    private val specialization: String,
    private val category: String? = null,
    ): PagingSource<Int, TutorItem>()
{
        private companion object{
            const val INITIAL_PAGE_INDEX = 1
        }

    override fun getRefreshKey(state: PagingState<Int, TutorItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1) ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TutorItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = tutorealApiService.searchTutor(
                page = position,
                size = params.loadSize,
                specialization, category
            )
            LoadResult.Page(
                data = responseData.tutors.items,
                prevKey = if(position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if(responseData.tutors.items.isEmpty()) null else position + 1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}