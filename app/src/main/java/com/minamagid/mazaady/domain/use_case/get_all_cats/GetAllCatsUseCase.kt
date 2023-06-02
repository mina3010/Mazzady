package com.minamagid.mazaady.domain.use_case.get_all_cats

import com.minamagid.mazaady.common.Resource
import com.minamagid.mazaady.domain.model.category.CategoryResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllCatsUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<Resource<GeneralResponse>> = flow {
        try {
            val movies = repository.getAllCats()
            emit(Resource.Success<GeneralResponse>(movies))
        } catch (e: HttpException) {
            emit(Resource.Error<GeneralResponse>(e.localizedMessage ?: "an Error Occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<GeneralResponse>("No Internet Connection, Check your Internet"))
        }
    }
}