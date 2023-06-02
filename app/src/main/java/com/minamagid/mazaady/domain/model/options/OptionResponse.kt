package com.minamagid.mazaady.domain.model.options

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OptionResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: List<Data>?,
    @SerializedName("msg")
    var msg: String?
)