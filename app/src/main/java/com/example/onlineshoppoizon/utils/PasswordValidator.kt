package com.example.onlineshoppoizon.utils

import android.text.Editable
import android.text.TextWatcher
import com.example.onlineshoppoizon.utils.Const.ERR_DIGIT
import com.example.onlineshoppoizon.utils.Const.ERR_LEN
import com.example.onlineshoppoizon.utils.Const.ERR_SPECIAL
import com.example.onlineshoppoizon.utils.Const.ERR_UPPER
import com.example.onlineshoppoizon.utils.Const.ERR_WHITESPACE


object PasswordValidator : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) = Unit

    fun validatePassword(pwd: String) = runCatching {
        require(pwd.length >= 8) { ERR_LEN }
        require(pwd.none { it.isWhitespace() }) { ERR_WHITESPACE }
        require(pwd.any { it.isDigit() }) { ERR_DIGIT }
        require(pwd.any { it.isUpperCase() }) { ERR_UPPER }
        require(pwd.any { !it.isLetterOrDigit() }) { ERR_SPECIAL }
        require(pwd.isEmpty()) { ERR_LEN }
    }
}