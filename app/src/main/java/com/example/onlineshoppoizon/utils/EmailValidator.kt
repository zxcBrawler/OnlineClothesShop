package com.example.onlineshoppoizon.utils

import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern

object EmailValidator : TextWatcher {
    private var isValid = false
    override fun afterTextChanged(editableText: Editable) {
        isValid = isValidEmail(editableText)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
    fun isValidEmail(email: CharSequence?): Boolean =
         email != null && EMAIL_PATTERN.matcher(email).matches()


    private val EMAIL_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}