package com.dorcohen.scavjunkbox.data.model

data class NavigationDestination(
    val previous:Int,
    val current: Int
)

fun NavigationDestination.navigatedTo(destination:Int):NavigationDestination =
    NavigationDestination(previous = current, current = destination)