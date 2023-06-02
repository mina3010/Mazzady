package com.minamagid.mazaady.domain.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Statistics(
    @SerializedName("auctions")
    var auctions: Int?,
    @SerializedName("products")
    var products: Int?,
    @SerializedName("users")
    var users: Int?
)