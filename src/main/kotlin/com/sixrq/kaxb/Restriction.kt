package com.sixrq.kaxb

class Restriction(xmlns: String) : Tag(xmlns) {
    fun extractType() : String {
        when (type.toLowerCase()) {
            "xsd:string" -> return "String"
            "xsd:token" -> return "String"
            "xsd:decimal" -> return "BigDecimal"
            "xsd:double" -> return "Double"
            "xsd:hexbinary" -> return "ByteArray"
            else -> return extractClassName(type)
        }
    }
}
