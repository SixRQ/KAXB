package com.sixrq.kaxb

class Element : Tag() {
    override fun toString(): String {
        return "${getLateinit()}var ${name.replaceFirst(name[0], name[0].toLowerCase())} : ${getTypeDefinition()}"
    }

    private fun getTypeDefinition(): String {
        if (maxOccurs.isNotBlank()) {
            return "MutableList<${extractType()}> = mutableListOf()"
        }
        return extractType()
    }

    private fun extractType() : String {
        when (type.toLowerCase()) {
            "xsd:string" -> return "String"
            "xsd:token" -> return "String"
            else -> return extractClassName(type)
        }
    }

    private fun getLateinit(): String {
        if (maxOccurs.isNotBlank()) {
            return ""
        }
        return "lateinit "
    }
}