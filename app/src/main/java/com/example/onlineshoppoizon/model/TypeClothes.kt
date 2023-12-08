package com.example.onlineshoppoizon.model

data class TypeClothes(
    val idType: Long = 0,
    val nameType: String = "",
    val categoryClothes: CategoryClothes = CategoryClothes(),
    )
