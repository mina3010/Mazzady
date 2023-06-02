package com.minamagid.mazaady.domin.use_case
import com.minamagid.mazaady.common.Resource
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.category.Statistics
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.repository.Repository
import com.minamagid.mazaady.domain.use_case.get_all_cats.GetAllCatsUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import retrofit2.HttpException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import retrofit2.Response
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.IOException

class GetAllCatsUseCaseTest {

    @Mock
    private lateinit var mockRepository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun `invoke should return success when repository call succeeds`() = runBlockingTest {
        // Initialize mocks
        MockitoAnnotations.openMocks(this)
        val data = Data(emptyList(), emptyList(),"1","1","1","1", Statistics(1,1,1),"",1,true,"mina",
            emptyList(),"",1,"mina magid","","",false)
        val response = "Success"
        val cats = GeneralResponse(code = 1, data = data ,msg= response)
        // Mock repository response
        `when`(mockRepository.getAllCats()).thenReturn(cats)

        // Create an instance of the use case
        val useCase = GetAllCatsUseCase(mockRepository)

        // Invoke the use case
        val resultFlow: Flow<Resource<GeneralResponse>> = useCase()

        // Collect the flow and verify the result
        resultFlow.collect { result ->
            assertEquals(Resource.Success(cats), result)
        }
    }


    private fun mockHttpException(): HttpException {
        val errorResponseBody = ResponseBody.create("application/json".toMediaTypeOrNull(), "Error response")
        val errorResponse = Response.error<Any>(404, errorResponseBody)
        return HttpException(errorResponse)
    }

}
