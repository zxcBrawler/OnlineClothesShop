package com.example.onlineshoppoizon.model

data class UserLogs (
    var idLog: Long,
    var user: User,
    var timestamp: String,
    var description: String,
)