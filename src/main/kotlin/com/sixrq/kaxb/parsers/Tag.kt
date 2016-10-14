package com.sixrq.kaxb.parsers

import org.w3c.dom.Node

open class Tag(val xmlns: String) {
    var name: String = ""
    var elementName = ""
    var type: String = ""
    var minOccurs: String = ""
    var maxOccurs: String = ""
    var value: String = ""
    var processContents: String = ""
    var base: String = ""
    var imports: MutableList<String> = mutableListOf()

    val children: MutableList<Tag> = mutableListOf()

    open fun processAttributes(item: Node?) {
        if (item!!.attributes.getNamedItem("name") != null) {
            name = extractClassName(item.attributes.getNamedItem("name").nodeValue)
            elementName = item.attributes.getNamedItem("name").nodeValue
        }
        if (item.attributes.getNamedItem("type") != null) {
            type = extractClassName(item.attributes.getNamedItem("type").nodeValue)
        } else if (item.attributes.getNamedItem("base") != null) {
            type = extractClassName(item.attributes.getNamedItem("base").nodeValue)
        }
        if (item.attributes.getNamedItem("minOccurs") != null) {
            minOccurs = item.attributes.getNamedItem("minOccurs").nodeValue
        }
        if (item.attributes.getNamedItem("maxOccurs") != null) {
            maxOccurs = item.attributes.getNamedItem("maxOccurs").nodeValue
        }
        if (item.attributes.getNamedItem("value") != null) {
            value = item.attributes.getNamedItem("value").nodeValue
        }
        if (item.attributes.getNamedItem("processContents") != null) {
            processContents = item.attributes.getNamedItem("processContents").nodeValue
        }
        if (item.attributes.getNamedItem("base") != null) {
            base = item.attributes.getNamedItem("base").nodeValue
        }
    }

    fun getPropertyName() = name.replaceFirst(name[0], name[0].toLowerCase())

    fun extractClassName(name: String): String {
        val className = StringBuilder()
        name.split('_').forEach {
            className.append(it.capitalize())
        }
        return className.toString()
    }

    open fun processText(item: Node) {}

    open fun extractType() : String {
        when (type.toLowerCase()) {
            "xsd:string" -> return "String"
            "xsd:token" -> return "String"
            "xsd:decimal" -> return "BigDecimal"
            "xsd:double" -> return "Double"
            "xsd:hexbinary" -> return "ByteArray"
            "xsd:boolean" -> return "Boolean"
            "xsd:any" -> return "Any"
            else -> return extractClassName(type)
        }
    }

    override fun toString(): String{
        return "$children"
    }
}