package com.example.socialnetworkapp.domain.util

import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Context.showKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
}