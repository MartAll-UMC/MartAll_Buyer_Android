package com.org.martall.utils

fun martNameToId(martName: String): Int {
    return when (martName) {
        "바나나올" -> 1
        "마트올수산" -> 2
        "UMC정육" -> 3
        "마트올약국" -> 4
        "마트올과자가게" -> 5
        else -> 0
    }
}