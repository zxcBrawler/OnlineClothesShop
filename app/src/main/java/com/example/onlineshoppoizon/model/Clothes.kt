package com.example.onlineshoppoizon.model

import com.example.onlineshoppoizon.response.TypeClothesResponse

data class Clothes(
    val idClothes: Int = 0,
    val nameClothesEn: String = "",
    val priceClothes: String = "",
    val barcode: String = "",
    val clothesPhoto: String = "",
    val typeClothes: TypeClothes= TypeClothes(),
)
