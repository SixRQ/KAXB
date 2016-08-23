package com.sixrq.kaxb

import org.w3c.dom.Node

open class Element(xmlns: String, val primitiveTypeMapping: MutableMap<String, String>) : Tag(xmlns) {
    init {
        if (this !is AnyElement) {
            imports.add("javax.xml.bind.annotation.XmlElement")
        }
    }

    override fun processAttributes(item: Node?) {
        super.processAttributes(item)
        if (getSchemaType().isNotEmpty()) {
            imports.add("javax.xml.bind.annotation.XmlSchemaType")
        }
    }

    override fun toString(): String {
        return "    @XmlElement(name = \"${name}\", namespace = \"$xmlns\")\n" +
                "${getSchemaType()}" +
                "    ${getLateinit()}var ${getPropertyName()} : ${getTypeDefinition()}"
    }

    override fun extractType(): String {
        val className = extractClassName(type)
        if (primitiveTypeMapping.containsKey(className)) {
            return primitiveTypeMapping[className].toString()
        } else {
            return super.extractType()
        }
    }

    open fun getTypeDefinition(): String {
        if (maxOccurs.isNotBlank()) {
            return "MutableList<${extractType()}> = mutableListOf()"
        }
        return extractType()
    }

    open fun getLateinit(): String {
        if (maxOccurs.isNotBlank() || primitiveTypeMapping.containsKey(extractClassName(type))) {
            return ""
        }
        return "lateinit "
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
}