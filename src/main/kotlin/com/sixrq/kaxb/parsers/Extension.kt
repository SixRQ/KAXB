package com.sixrq.kaxb.parsers

class Extension(xmlns: String, primitiveTypeMapping: MutableMap<String, String>) : Element(xmlns, primitiveTypeMapping) {
    init {
        imports= mutableListOf("javax.xml.bind.annotation.XmlValue",
            "javax.xml.bind.annotation.XmlJavaTypeAdapter")
        name = "value"
    }

    override fun toString(): String {
        val result = StringBuilder()
        result.append("    @XmlValue\n")
        result.append("    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)\n")
        result.append("${getSchemaType()}")
        result.append("    ${getLateinit()}var ${getPropertyName()} : ${getTypeDefinition()}\n")
        children.forEach { result.append(it.toString()) }
        return result.toString()
    }
}