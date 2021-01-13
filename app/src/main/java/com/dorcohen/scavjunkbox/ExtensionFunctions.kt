package com.dorcohen.scavjunkbox

import android.content.Context
import android.net.Uri

fun Context.resIdToUri(resId:Int): Uri =  Uri.parse("android.resource://${this.packageName}/$resId")