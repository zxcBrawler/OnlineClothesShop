package com.example.onlineshoppoizon.utils

import com.yandex.mapkit.geometry.Point

object Const {
    const val ADD_ITEM = 1
    const val DECREASE_ITEM = 2
    const val MAX_ITEM_COUNT = 9
    const val TYPE_PICK_UP = 1
    const val TYPE_HOME = 2
    const val MAPKIT_API_KEY = "ecb00ce6-7bd1-419f-997e-0dec530e04c7"
    const val ERR_LEN = "Password must have at least eight characters!"
    const val ERR_WHITESPACE = "Password must not contain whitespace!"
    const val ERR_DIGIT = "Password must contain at least one digit!"
    const val ERR_UPPER = "Password must have at least one uppercase letter!"
    const val ERR_SPECIAL = "Password must have at least one special character, such as: _%-=+#@"

    val START_PLACE = Point(
        0.0,
        0.0)
}