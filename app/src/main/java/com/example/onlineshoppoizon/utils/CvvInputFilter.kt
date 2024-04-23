package com.example.onlineshoppoizon.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class CvvInputFilter(private val editText: EditText) : TextWatcher {
    private var isFormatting = false
    private val mask = "XXX"
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!isFormatting) {
            isFormatting = true

            val cleanedString = s.toString().replace("[^\\d]".toRegex(), "")
            val formattedString = formatCvv(cleanedString, mask)

            editText.setText(formattedString)
            editText.setSelection(formattedString.length)

            isFormatting = false
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun formatCvv(number: String, mask: String): String {
        val maskChars = mask.toCharArray()
        val numberChars = number.toCharArray()
        val result = StringBuilder()

        var maskIndex = 0
        var numberIndex = 0
        while (maskIndex < maskChars.size && numberIndex < numberChars.size) {
            val maskChar = maskChars[maskIndex]
            if (maskChar != 'X') {
                result.append(maskChar)
                maskIndex++
            } else {
                result.append(numberChars[numberIndex])
                maskIndex++
                numberIndex++
            }
        }
        return result.toString()
    }
}