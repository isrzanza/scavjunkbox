package com.dorcohen.scavjunkbox.data.model

sealed class Result
data class Success(val data:Any) : Result()
data class Failure(val message:String) : Result()