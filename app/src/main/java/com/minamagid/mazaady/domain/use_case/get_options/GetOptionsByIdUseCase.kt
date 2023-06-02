package com.minamagid.mazaady.domain.use_case.get_options

import com.minamagid.mazaady.common.Resource
import com.minamagid.mazaady.domain.model.general.GeneralResponse2
import com.minamagid.mazaady.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOptionsByIdUseCase @Inject constructor(val repository: Repository) {
    suspend operator fun invoke(id: Int): Flow<Resource<GeneralResponse2>> = flow {
        try {
            val details = repository.getOptionById(id)
            emit(Resource.Success<GeneralResponse2>(details))
        } catch (e: retrofit2.HttpException) {
            emit(Resource.Error<GeneralResponse2>(e.localizedMessage ?: "An error occurred"))
        } catch (e: java.io.IOException) {
            emit(Resource.Error<GeneralResponse2>("No Internet Connection, Check your Internet"))
        }
    }
}
