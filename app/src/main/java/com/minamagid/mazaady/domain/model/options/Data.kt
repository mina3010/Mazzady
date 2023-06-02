package com.minamagid.mazaady.domain.model.options

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("description")
    var description: Any?,
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
    var parent: Int?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("type")
    var type: Any?,
    @SerializedName("value")
    var value: String?


)