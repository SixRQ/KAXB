package com.sixrq.kaxb

import org.w3c.dom.Element

class SimpleType(val item: Element) {
    val key by lazy { item.getAttributes().getNamedItem("name").nodeValue }
    val value by lazy{
        when(item.getElementsByTagName("xsd:restriction").item(0)) {
            is Element ->
                item.getElementsByTagName("xsd:restriction").item(0).attributes.getNamedItem("base").nodeValue
                        .replace("xsd:", "")
                        .replace("token", "String")
                        .replace("hexBinary", "ByteArray")
                        .capitalize()
            else -> item.getAttributes().getNamedItem("name").nodeValue //TODO need to handle enumerations
        }
    }
}
