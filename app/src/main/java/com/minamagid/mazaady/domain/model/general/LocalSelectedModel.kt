package com.minamagid.mazaady.domain.model.general

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.minamagid.mazaady.domain.model.category.Data

data class LocalSelectedModel(
    var id: Int?,
    var name :String,
    var value: String?
)