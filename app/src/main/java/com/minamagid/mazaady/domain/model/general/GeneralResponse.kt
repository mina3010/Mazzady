package com.minamagid.mazaady.domain.model.general

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.minamagid.mazaady.domain.model.category.Data

@Keep
data class GeneralResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("msg")
    var msg: String?
)