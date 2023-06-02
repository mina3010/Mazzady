package com.minamagid.mazaady.domain.repository

import com.minamagid.mazaady.domain.model.category.CategoryResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse2
import com.minamagid.mazaady.domain.model.options.OptionResponse
import com.minamagid.mazaady.domain.model.subCategory.SubCategoryResponse


interface Repository {
    suspend fun getAllCats(): GeneralResponse?

    suspend fun getSubCategoryById(cat_id: Int): GeneralResponse2?
    suspend fun getOptionById(option_id: Int): GeneralResponse2?


}