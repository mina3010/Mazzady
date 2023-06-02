package com.minamagid.mazaady.data.repository

import com.minamagid.mazaady.data.remote.Api
import com.minamagid.mazaady.domain.model.category.CategoryResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse2
import com.minamagid.mazaady.domain.model.options.OptionResponse
import com.minamagid.mazaady.domain.model.subCategory.SubCategoryResponse
import com.minamagid.mazaady.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api) : Repository {

    override suspend fun getAllCats(): GeneralResponse? {
        return api.getCategories()
    }

    override suspend fun getSubCategoryById(cat_id: Int): GeneralResponse2? {
        return api.getSubCategoryById(cat_id)
    }

    override suspend fun getOptionById(option_id: Int): GeneralResponse2? {
        return api.getOptionsChildById(option_id)
    }


}