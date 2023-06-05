package com.minamagid.mazaady.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minamagid.mazaady.common.Resource
import com.minamagid.mazaady.domain.model.category.Children
import com.minamagid.mazaady.domain.model.category.Data
import javax.inject.Inject
import com.minamagid.mazaady.domain.use_case.get_all_cats.GetAllCatsUseCase
import com.minamagid.mazaady.domain.use_case.get_options.GetOptionsByIdUseCase
import com.minamagid.mazaady.domain.use_case.get_sub_cats.GetSubCategoryByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class HomeViewModel @Inject constructor(val getAllCatsUseCase: GetAllCatsUseCase,val getSubCategoryByIdUseCase: GetSubCategoryByIdUseCase
                                        ,val getOptionsByIdUseCase: GetOptionsByIdUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val mainState: MutableStateFlow<MainState> = _state
    val catRemote = MutableLiveData<Data?>()
    val processTypeRemote = MutableLiveData<ArrayList<Data?>>()
    val subOptions = MutableLiveData<ArrayList<Data?>>()
    val subOptions2 = MutableLiveData<ArrayList<Data?>>()
    val subCats = MutableLiveData<List<Children?>>()
    var backSelected =MutableLiveData<String>("")
    var isSelectedItem =MutableLiveData<Boolean>(false)
    var isPosition =MutableLiveData<Int>(-1)
    var backSelectedSubCatsId =MutableLiveData<Int>(0)
    var backSelectedSubCats =MutableLiveData<String>("")

    var backSelectedOptionId =MutableLiveData<Int>(0)
    var backSelectedOptionId2 =MutableLiveData<Int>(0)
    var backSelectedOptionTxt =MutableLiveData<String>("")

    private val _listMoviesByCategoryState = MutableStateFlow(MainState())

    init {
        getAllCats()
    }

    private fun getAllCats() {
        getAllCatsUseCase().onEach { response ->
            when (response) {
                is Resource.Loading<*> -> {
                    _state.value = MainState(isLoading = true)
                }
                is Resource.Success<*> -> {
                    catRemote.value =  response.data?.data

                }
                is Resource.Error<*> -> {
                    _state.value =
                        MainState(error = response.message ?: "unexpected error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getProcessType(
        catId: Int?
    ) {
        getSubCategoryByIdUseCase(catId?:0).onEach { response ->
            when (response) {
                is Resource.Loading<*> -> {
                    _listMoviesByCategoryState.value = MainState(isLoading = true)
                }
                is Resource.Success<*> -> {
                    processTypeRemote.value = response.data?.data?.let { ArrayList(it) }
                }
                is Resource.Error<*> -> {
                    _listMoviesByCategoryState.value =
                        MainState(error = response.message ?: "unexpected error")
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getSubOptions(
        id: Int?,type:Int?
    ) {
        getOptionsByIdUseCase(id?:0).onEach { response ->
            when (response) {
                is Resource.Loading<*> -> {
                    _listMoviesByCategoryState.value = MainState(isLoading = true)
                }
                is Resource.Success<*> -> {
                    if (type==0) {
                        subOptions.value = response.data?.data?.let { ArrayList(it) }
                    }else{
                        subOptions2.value = response.data?.data?.let { ArrayList(it) }
                    }
                }
                is Resource.Error<*> -> {
                    _listMoviesByCategoryState.value =
                        MainState(error = response.message ?: "unexpected error")
                }
            }
        }.launchIn(viewModelScope)
    }

}