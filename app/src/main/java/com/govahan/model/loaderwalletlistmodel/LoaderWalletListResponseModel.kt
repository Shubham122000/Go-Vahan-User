package com.govahan.model.loaderwalletlistmodel

import com.google.gson.annotations.SerializedName

data class LoaderWalletListResponseModel(
//    var TotalAmount: String,
//    var `data`: List<LoaderWalletListData>,
//    var message: String,
//    var status: Int,var url: String,
    @SerializedName("error"       ) var error      : Boolean? = null,
    @SerializedName("status_code" ) var statusCode : Int?     = null,
    @SerializedName("message"     ) var message    : String?  = null,
    @SerializedName("result"      ) var result     : LoaderWalletListResult?  = LoaderWalletListResult()
)
data class LoaderWalletListResult (

    @SerializedName("total_amount" ) var totalAmount : Double?         = null,
    @SerializedName("url" ) var url : String?         = null,
    @SerializedName("data"         ) var data        : ArrayList<LoaderWalletListData> = arrayListOf()

)
data class LoaderWalletListData(
    @SerializedName("id"               ) var id              : Int?    = null,
    @SerializedName("type"             ) var type            : Int?    = null,
    @SerializedName("user_id"          ) var userId          : Int?    = null,
    @SerializedName("amount"           ) var amount          : String? = null,
    @SerializedName("transaction_type" ) var transactionType : String? = null,
    @SerializedName("booking_id"       ) var bookingId       : String? = null,
    @SerializedName("referal_type"     ) var referalType     : Int?    = null,
    @SerializedName("transaction_date" ) var transactionDate : String? = null,
    @SerializedName("transaction_id"   ) var transactionId   : String? = null,
    @SerializedName("invoice"          ) var invoice         : String? = null,
    @SerializedName("status"           ) var status          : Int?    = null,
    @SerializedName("created_at"       ) var createdAt       : String? = null,
    @SerializedName("updated_at"       ) var updatedAt       : String? = null,
    @SerializedName("to_id"            ) var toId            : String? = null,
    @SerializedName("from_id"          ) var fromId          : String? = null,
    @SerializedName("from_user"        ) var fromUser        : String? = null,
    @SerializedName("to_user"          ) var toUser          : String? = null
//    var amount: String,
//    var credit: String,
//    var debit: String,
//    var vendor_name: String,var transaction_type:String,var referal_type:Int,
//    var transaction_date: String,var create_at:String,var transaction_id:String,
//    var user_id: Int
)