package com.example.onlineshoppoizon.model

data class ClothesColors(
    val id: Long = 0,
    val clothes: Clothes = Clothes(),
    val colors: Colors = Colors(),
)
