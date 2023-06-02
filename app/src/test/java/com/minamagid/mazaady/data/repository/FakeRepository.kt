package com.minamagid.mazaady.data.repository

import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.category.Statistics
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse2
import com.minamagid.mazaady.domain.repository.Repository

class FakeRepository : Repository {
    private val data = Data(emptyList(), emptyList(),"1","1","1","1", Statistics(1,1,1),"",1,true,"mina",
        emptyList(),"",1,"mina magid","","",false)
    private val response = "Success"
    private val cats = GeneralResponse(code = 1, data = data ,msg= response)
    private val subCats = GeneralResponse2(code = 1, data = listOf(data) ,msg= response)


    override suspend fun getAllCats(): GeneralResponse {
        return cats
    }

    override suspend fun getSubCategoryById(cat_id: Int): GeneralResponse2? {
        return subCats
    }

    override suspend fun getOptionById(option_id: Int): GeneralResponse2? {
        return subCats
    }



}