package com.yavin.yavinintentapi.ui.main.api.v4.transactions

import com.google.gson.annotations.SerializedName

data class TransactionsRequestV4(
    @SerializedName("start_date")
    var startDate: String? = null,

    @SerializedName("end_date")
    var endDate: String? = null,

    @SerializedName("limit")
    var limit: Int? = 20,

    @SerializedName("offset")
    var offset: Int? = 0,
)