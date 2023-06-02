package com.minamagid.mazaady.presentation.home

import com.minamagid.mazaady.domain.model.category.CategoryResponse
import com.minamagid.mazaady.domain.model.general.GeneralResponse

data class MainState(
    val isLoading: Boolean = false,
    val generalResponse: GeneralResponse? = null,
    val error: String = ""
)