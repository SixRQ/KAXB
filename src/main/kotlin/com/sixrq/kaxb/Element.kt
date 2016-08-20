package com.sixrq.kaxb

class Element(val primitiveTypeMapping: MutableMap<String, String>) : Tag() {
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
            else -> {
                val className = extractClassName(type)
                if (primitiveTypeMapping.containsKey(className)) {
                    return primitiveTypeMapping.get(className).toString()
                } else {
                    return className
                }}
        }
    }

    private fun getLateinit(): String {
        if (maxOccurs.isNotBlank() || primitiveTypeMapping.containsKey(extractClassName(type))) {
            return ""
        }
        return "lateinit "
    }
}