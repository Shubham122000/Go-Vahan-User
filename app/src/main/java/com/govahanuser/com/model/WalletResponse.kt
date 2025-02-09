package com.govahanuser.com.model

import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("data"          ) var data         : WalletResponseData?    = WalletResponseData(),
    @SerializedName("message"       ) var message      : String?  = null,
    @SerializedName("status"        ) var status       : Int? = null,
    @SerializedName("response_code" ) var responseCode : Int?     = null
)

data class WalletResponseData(
    @SerializedName("user_id"          ) var userId          : Int?    = null,
    @SerializedName("amount"           ) var amount          : String? = null,
    @SerializedName("transaction_type" ) var transactionType : Int?    = null,
    @SerializedName("transaction_date" ) var transactionDate : String? = null,
    @SerializedName("invoice_no"       ) var invoiceNo       : String? = null,
    @SerializedName("status"           ) var status          : Int?    = null,
    @SerializedName("transaction_id"   ) var transactionId   : String? = null,
    @SerializedName("updated_at"       ) var updatedAt       : String? = null,
    @SerializedName("created_at"       ) var createdAt       : String? = null,
    @SerializedName("id"               ) var id              : Int?    = null
)
