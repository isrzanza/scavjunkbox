package com.dorcohen.scavjunkbox

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.resIdToUri(resId:Int): Uri =  Uri.parse("android.resource://${this.packageName}/$resId")

fun Context.closeKeyboard(view: View){
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
