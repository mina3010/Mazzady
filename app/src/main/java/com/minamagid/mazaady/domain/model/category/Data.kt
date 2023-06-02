package com.minamagid.mazaady.domain.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.minamagid.mazaady.domain.model.options.Option

@Keep
data class Data(
    @SerializedName("ads_banners")
    var adsBanners: List<AdsBanner>?,
    @SerializedName("categories")
    var categories: List<Category>?,
    @SerializedName("google_version")
    var googleVersion: String?,
    @SerializedName("huawei_version")
    var huaweiVersion: String?,
    @SerializedName("ios_latest_version")
    var iosLatestVersion: String?,
    @SerializedName("ios_version")
    var iosVersion: String?,
    @SerializedName("statistics")
    var statistics: Statistics?,
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
    var value: String?,
    @SerializedName("isSelected")
    var isSelected: Boolean?
)