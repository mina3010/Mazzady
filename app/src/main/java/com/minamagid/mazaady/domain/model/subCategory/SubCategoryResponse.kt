package com.minamagid.mazaady.domain.model.subCategory

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SubCategoryResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: List<Data>?,
    @SerializedName("msg")
    var msg: String?
)