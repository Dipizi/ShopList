package com.example.shoplist.utils

import androidx.databinding.BindingAdapter
import com.example.shoplist.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("messageErrorName")
fun bindMessageErrorName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = textInputLayout.context.getString(R.string.message_about_error_name)
    textInputLayout.error = if (isError) message else null
}

@BindingAdapter("messageErrorCount")
fun bindMessageErrorCount(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = textInputLayout.context.getString(R.string.message_about_error_count)
    textInputLayout.error = if (isError) message else null
}

@BindingAdapter("setCount")
fun bindSetCount(editText: TextInputEditText, count: Int?) {
    count?.let {
        editText.setText(it.toString())
    }
}

@BindingAdapter("setName")
fun bindSetCount(editText: TextInputEditText, name: String?) {
    name?.let {
        editText.setText(it)
    }
}