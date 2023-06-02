package com.minamagid.mazaady.data.remote

import com.minamagid.mazaady.domain.model.category.CategoryResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse2
import com.minamagid.mazaady.domain.model.options.OptionResponse
import com.minamagid.mazaady.domain.model.subCategory.SubCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {
    @GET("get_all_cats")
    suspend fun getCategories(): GeneralResponse?

    @GET("properties")
    suspend fun getSubCategoryById(
        @Query("cat") sub_category_id: Int
    ): GeneralResponse2?

    @GET("get-options-child/{id}")
    suspend fun getOptionsChildById(
        @Path("id") id: Int
    ): GeneralResponse2?

}