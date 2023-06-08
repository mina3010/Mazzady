package com.minamagid.mazaady.presentation.home
import androidx.lifecycle.Observer
import com.minamagid.mazaady.common.Resource
import com.minamagid.mazaady.data.repository.FakeRepository
import com.minamagid.mazaady.domain.model.category.Data
import com.minamagid.mazaady.domain.model.category.Statistics
import com.minamagid.mazaady.domain.model.general.GeneralResponse
import com.minamagid.mazaady.domain.use_case.get_all_cats.GetAllCatsUseCase
import com.minamagid.mazaady.domain.use_case.get_options.GetOptionsByIdUseCase
import com.minamagid.mazaady.domain.use_case.get_sub_cats.GetSubCategoryByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.TestWatcher
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.internal.verification.Description

@ExperimentalCoroutinesApi
class HomeViewModelTest{
    @Mock
    private lateinit var getAllCatsUseCase: GetAllCatsUseCase
    @Mock
    private lateinit var getSubCategoryByIdUseCase: GetSubCategoryByIdUseCase
    @Mock
    private lateinit var getOptionsByIdUseCase: GetOptionsByIdUseCase
    private lateinit var fakeRepository: FakeRepository
    private lateinit var viewModel: HomeViewModel

//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var testScope: TestCoroutineScope


    @Mock
    private lateinit var stateObserver: Observer<MainState>
    @Before
    fun setup() {
        initMocks(this)
        MockitoAnnotations.openMocks(this)
        testDispatcher = TestCoroutineDispatcher()
        testScope = TestCoroutineScope(testDispatcher)

        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        fakeRepository = FakeRepository()
        getSubCategoryByIdUseCase = GetSubCategoryByIdUseCase(fakeRepository)
        getOptionsByIdUseCase = GetOptionsByIdUseCase(fakeRepository)
        getAllCatsUseCase = GetAllCatsUseCase(fakeRepository)
        viewModel = HomeViewModel(getAllCatsUseCase,getSubCategoryByIdUseCase,getOptionsByIdUseCase)

    }

    @After
    fun cleanup() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test finishes
        testScope.cleanupTestCoroutines()
        testDispatcher.cleanupTestCoroutines()
    }
    @Test
    fun `getProcessType should emit the correct states`() = runBlockingTest {
        val catId = 123
        val loadingState = MainState(isLoading = true)
        val data = Data(emptyList(), emptyList(),"1","1","1","1", Statistics(1,1,1),"",1,true,"mina",
            emptyList(),"",1,"mina magid","","",false)
        val successData = GeneralResponse(0, data,"")
        val successState = MainState(generalResponse = successData)
        val errorState = MainState(error = "unexpected error")
        val responseFlow = MutableSharedFlow<Resource<GeneralResponse>>()
        `when`(getAllCatsUseCase.invoke()).thenReturn(responseFlow)

        // Act
        val states =  ArrayList<Data?>()
        viewModel.processTypeRemote.observeForever { state ->
            states.add(state.get(0))
        }
        viewModel.getProcessType(catId)

        responseFlow.emit(Resource.Loading())
        responseFlow.emit(Resource.Success(successData))
        responseFlow.emit(Resource.Error("unexpected error"))

        // Assert
        assertEquals(listOf(loadingState, successState, errorState), states)
    }
}