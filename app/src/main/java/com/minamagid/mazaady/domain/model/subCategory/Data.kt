package com.minamagid.mazaady.domain.model.subCategory

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("description")
    var description: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("list")
    var list: Boolean?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("options")
    var options: List<Option>?,
    @SerializedName("other_value")
    var otherValue: Any?,
    @SerializedName("parent")
    var parent: Any?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("type")
    var type: Any?,
    @SerializedName("value")
    var value: String?
)