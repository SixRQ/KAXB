package com.sixrq.kaxb.parsers

class AnyElement(xmlns: String,  primitiveTypeMapping: MutableMap<String, String>) : Element(xmlns, primitiveTypeMapping) {
    init {
        name = "Any"
        type = "Any"
        imports = mutableListOf("javax.xml.bind.annotation.XmlAnyElement")
    }

    override fun toString(): String {
        return "    @XmlAnyElement(${processContents} = true)\n" +
                "    ${getLateinit()}var ${getPropertyName()} : ${getTypeDefinition()}"
    }
}