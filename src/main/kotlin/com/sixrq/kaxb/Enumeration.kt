package com.sixrq.kaxb

import java.util.regex.Pattern

class Enumeration(xmlns: String) : Tag(xmlns) {
    override fun toString(): String{
        val enumeration = StringBuilder()
        enumeration.append("    @XmlEnumValue(\"${value}\")\n")
        enumeration.append("    ${value.split(Pattern.compile("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A=Z][a-z])")).joinToString("_").toUpperCase()}")
        if (value.isNotEmpty()) {
            enumeration.append("(\"$value\")")
        }
        enumeration.append(",")
        return enumeration.toString()
    }
}