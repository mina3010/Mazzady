package com.minamagid.mazaady.domin.use_case

import com.minamagid.mazaady.common.Resource
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.category.Statistics
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse2
import com.minamagid.mazaady.domain.repository.Repository
import com.minamagid.mazaady.domain.use_case.get_options.GetOptionsByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class GetOptionsByIdUseCaseTest {
    // Mock the Repository dependency
    @Mock
    private lateinit var mockRepository: Repository

    // The instance of the use case to be tested
    private lateinit var useCase: GetOptionsByIdUseCase

    @Before
    fun setup() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this)

        // Create an instance of the use case with the mocked repository
        useCase = GetOptionsByIdUseCase(mockRepository)
    }

    @Test
    fun `invoke should return success when repository call is successful`() = runBlockingTest {
        val data = Data(emptyList(), emptyList(),"1","1","1","1", Statistics(1,1,1),"",1,true,"mina",
            emptyList(),"",1,"mina magid","","",false)
        val response = "Success"
        val  list = ArrayList<Data>()
        list.add(data)
        val mockResponse = GeneralResponse2(code = 1, data = list ,msg= response)
        `when`(mockRepository.getOptionById(1)).thenReturn(mockResponse)

        // Invoke the use case with the given ID
        val result = useCase(1)

        // Collect the flow and assert the emitted result
        result.collect { resource ->
            // Assert that the result is a success and contains the expected response
            assertEquals(Resource.Success(mockResponse), resource)
        }
    }

    @Test
    fun `invoke should return error when repository call throws HttpException`() = runBlockingTest {
        // Mock the repository's getOptionById function to throw an HttpException
        val mockException = mockHttpException()
        `when`(mockRepository.getOptionById(1)).thenThrow(mockException)

        // Invoke the use case with the given ID
        val result = useCase(1)

        // Collect the flow and assert the emitted result
        result.collect { resource ->
            // Assert that the result is an error and contains the expected exception message
            val expectedError = Resource.Error<GeneralResponse>(mockException.localizedMessage ?: "an Error Occurred")
            assertEquals(expectedError, result)
        }
    }

    @Test
    fun `invoke should return error when repository call throws IOException`() = runBlockingTest {
        // Mock the repository's getOptionById function to throw an IOException
        val mockException = IOException("No Internet Connection, Check your Internet")
        `when`(mockRepository.getOptionById(1)).thenThrow(mockException)

        // Invoke the use case with the given ID
        val result = useCase(1)

        // Collect the flow and assert the emitted result
        result.collect { resource ->
            // Assert that the result is an error and contains the expected exception message
            val expectedError = Resource.Error<GeneralResponse>("No Internet Connection, Check your Internet")
            assertEquals(expectedError, resource)
           // assertEquals(Resource.Error("No Internet Connection, Check your Internet"), resource)

        }
    }

    private fun mockHttpException(): HttpException {
        val errorResponseBody = ResponseBody.create("application/json".toMediaTypeOrNull(), "Error response")
        val errorResponse = Response.error<Any>(404, errorResponseBody)
        return HttpException(errorResponse)
    }
}
