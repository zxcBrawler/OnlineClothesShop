package com.example.onlineshoppoizon.response

import com.example.onlineshoppoizon.model.ClothesColors
import com.example.onlineshoppoizon.model.ClothesSizeClothes
import com.example.onlineshoppoizon.model.User

data class CartResponse(
    var user : User = User(),
    var sizeClothes : ClothesSizeClothes = ClothesSizeClothes(),
    var colorClothes : ClothesColors = ClothesColors(),
    var quantity : Int = 0,
)
