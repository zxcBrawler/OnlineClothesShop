package com.example.onlineshoppoizon.model

data class DeliveryInfo (
    val id : Long = 0,

    var order: Orders = Orders(),

    var typeDelivery: TypeDelivery = TypeDelivery(),

    var shopAddresses: ShopAddresses? = ShopAddresses(),

    var addresses: Address? = Address(),
)