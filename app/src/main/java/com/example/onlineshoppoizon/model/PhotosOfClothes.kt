package com.example.onlineshoppoizon.model

import com.example.onlineshoppoizon.response.ClothesResponse

data class PhotosOfClothes(
    val id: Long,
    val clothes: Clothes,
    val clothesPhoto: ClothesPhoto,
)
