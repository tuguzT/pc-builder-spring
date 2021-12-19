package io.github.tuguzt.pcbuilder.backend.spring.retrofit

import io.github.tuguzt.pcbuilder.backend.spring.model.octopart.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OctopartAPI {
    @GET(
        "v4/rest/parts/search" +
                "?include[]=specs" +
                "&include[]=short_description" +
                "&include[]=imagesets"
    )
    fun searchQuery(
        @Query("q") query: String,
        @Query("apikey") apiKey: String,
        @Query("start") start: Int,
        @Query("limit") limit: Int,
    ): Call<SearchResponse>
}
