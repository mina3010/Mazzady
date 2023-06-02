package com.minamagid.mazaady.domain.model.category



import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AdsBanner(
    @SerializedName("duration")
    var duration: Int?,
    @SerializedName("img")
    var img: String?,
    @SerializedName("media_type")
    var mediaType: String?
)