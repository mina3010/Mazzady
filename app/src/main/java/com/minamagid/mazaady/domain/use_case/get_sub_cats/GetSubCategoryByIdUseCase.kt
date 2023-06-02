package com.minamagid.mazaady.domain.use_case.get_sub_cats

import com.minamagid.mazaady.common.Resource
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse2
import com.minamagid.mazaady.domain.model.subCategory.SubCategoryResponse
import com.minamagid.mazaady.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSubCategoryByIdUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(id:Int):
            Flow<Resource<GeneralResponse2>> = flow {
        try {
            val details = repository.getSubCategoryById(id)
            emit(Resource.Success<GeneralResponse2>(details))
        } catch (e: retrofit2.HttpException) {
            emit(Resource.Error<GeneralResponse2>(e.localizedMessage ?: "an Error Occurred"))
        } catch (e: java.io.IOException) {
            emit(Resource.Error<GeneralResponse2>("No Internet Connection, Check your Internet"))
        }

    }
}