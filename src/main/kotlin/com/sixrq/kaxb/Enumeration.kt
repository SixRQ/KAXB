package com.sixrq.kaxb

import java.util.regex.Pattern

class Enumeration : Tag() {
    override fun toString(): String{
        val enumeration = StringBuilder()
        enumeration.append("${value.split(Pattern.compile("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A=Z][a-z])")).joinToString("_").toUpperCase()}")
        if (value.isNotEmpty()) {
            enumeration.append("(\"$value\")")
        }
        enumeration.append(",")
        return enumeration.toString()
    }
}