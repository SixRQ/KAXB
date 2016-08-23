package com.sixrq.kaxb

class Element(xmlns: String, val primitiveTypeMapping: MutableMap<String, String>) : Tag(xmlns) {
    override fun toString(): String {
        return "    @XmlElement(name = \"${name}\", namespace = \"$xmlns\")\n" +
                "${getSchemaType()}" +
                "    ${getLateinit()}var ${getPropertyName()} : ${getTypeDefinition()}"
    }

    private fun getSchemaType(): String {
        if (maxOccurs.isBlank()) {
            when (type.toLowerCase()) {
                "xsd:token" -> return "    @XmlSchemaType(\"token\")\n"
                else -> return ""
            }
        }
        return ""
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