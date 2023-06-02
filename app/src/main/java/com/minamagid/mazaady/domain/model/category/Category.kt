package com.minamagid.mazaady.domain.model.category


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Category(
    @SerializedName("children")
    var children: List<Children>?,
    @SerializedName("circle_icon")
    var circleIcon: String?,
    @SerializedName("description")
    var description: Any?,
    @SerializedName("disable_shipping")
    var disableShipping: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("slug")
    var slug: String?
)