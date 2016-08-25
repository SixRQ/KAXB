package com.sixrq.kaxb

class Attribute(xmlns: String, primitiveTypeMapping: MutableMap<String, String>) : Element(xmlns, primitiveTypeMapping) {
    init {
        imports = mutableListOf(
                "javax.xml.bind.annotation.XmlValue",
                "javax.xml.bind.annotation.XmlJavaTypeAdapter")
    }

    override fun toString(): String {
        return "    @XmlValue\n" +
                "    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)\n" +
                "${getSchemaType()}" +
                "    ${getLateinit()}var ${getPropertyName()} : ${getTypeDefinition()}\n"
    }
}