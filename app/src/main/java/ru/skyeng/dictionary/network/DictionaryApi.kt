package ru.skyeng.dictionary.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.skyeng.dictionary.data.model.SearchData

private const val PAGE_SIZE = 20

interface DictionaryApi {

    @GET("/api/public/v1/words/search")
    suspend fun search(
        @Query("search") search: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = PAGE_SIZE,
    ): Response<List<SearchData>>
}
