package com.minamagid.mazaady.domain.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("msg")
    var msg: String?
)