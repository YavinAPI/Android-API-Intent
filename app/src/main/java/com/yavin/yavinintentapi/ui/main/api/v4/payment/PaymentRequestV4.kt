package com.yavin.yavinintentapi.ui.main.api.v4.payment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yavin.yavinintentapi.ui.main.model.Customer
import com.yavin.yavinintentapi.ui.main.model.ReceiptTicket
import com.yavin.yavinintentapi.ui.main.model.Vendor
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentRequestV4(
    @SerializedName("amount")
    var amount: Int = 0,
    @SerializedName("cartId")
    var cartId: String? = null,
    @SerializedName("currencyCode")
    var currencyCode: String? = null,
    @SerializedName("customer")
    var customer: Customer? = null,
    @SerializedName("giftAmount")
    var giftAmount: Int? = 0,
    @SerializedName("medium")
    var medium: String? = null,
    @SerializedName("prepayScreen")
    var prepayScreen: String? = null,
    @SerializedName("receiptTicket")
    var receiptTicket: ReceiptTicket? = null,
    @SerializedName("receiptTicketJson")
    var receiptTicketJson: String? = null,
    @SerializedName("reference")
    var reference: String? = null,
    @SerializedName("transactionType")
    var transactionType: String? = null,
    @SerializedName("vendor")
    var vendor: Vendor? = null,
    @SerializedName("idempotentUuid")
    var idempotentUuid: String? = null,
) : Parcelable