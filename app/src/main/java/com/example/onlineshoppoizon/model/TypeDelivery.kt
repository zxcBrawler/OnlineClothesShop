package com.example.onlineshoppoizon.model

data class TypeDelivery (
    val id : Long = 0,
    var nameType : String = "",
    val deliveryInfo : List<DeliveryInfo> = arrayListOf()
)