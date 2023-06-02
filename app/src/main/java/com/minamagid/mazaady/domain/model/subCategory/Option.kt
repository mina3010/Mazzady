package com.minamagid.mazaady.domain.model.subCategory

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Option(
    @SerializedName("child")
    var child: Boolean?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("parent")
    var parent: Int?,
    @SerializedName("slug")
    var slug: String?
)