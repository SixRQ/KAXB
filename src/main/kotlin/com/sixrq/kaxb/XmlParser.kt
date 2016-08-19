package com.sixrq.kaxb

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class XmlParser(val filename: String, val packageName: String) {
    val root: Element by lazy {
        val xmlFile = File(filename)
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        dBuilder.parse(xmlFile).documentElement
    }
    val xmlns: String by lazy { root.getAttribute("xmlns") }
    val xsdns: String by lazy { root.getAttribute("xmlns:xsd") }


    fun readAndDisplayDocument() {
        val elements = root.childNodes
        val schema = Schema()
        val basicType: MutableMap<String, String> = hashMapOf()
        processElements(schema, elements)

        for (child in schema.children.filter { it is SimpleType &&
                it.children.filter { it is Restriction &&
                        it.children.filter { it is Enumeration }.isEmpty()}.isNotEmpty()}) {
            basicType.put(child.name, (child.children.filter { it is Restriction }[0] as Restriction).extractType())
        }

        for (child in schema.children.filter { it is SimpleType &&
                it.children.filter { it is Restriction &&
                        it.children.filter { it is Enumeration }.isNotEmpty()}.isNotEmpty()}) {
            println(child)
        }

        for (child in schema.children.filter { it is ComplexType } ) {
            println(child)
        }
    }

    private fun processElements(tag: Tag, elements: NodeList) {
        for (index in 0..(elements.length - 1)) {
            val item = elements.item(index)
            val childTag = {
                when (item.nodeName) {
                    "xsd:complexType" -> ComplexType(xmlns, packageName)
                    "xsd:simpleType" -> SimpleType(xmlns, packageName)
                    "xsd:element" -> Element()
                    "xsd:extension" -> Extension()
                    "xsd:annotation" -> Annotation()
                    "xsd:documentation" -> Documentation()
                    "xsd:sequence" -> Sequence()
                    "xsd:restriction" -> Restriction()
                    "xsd:enumeration" -> Enumeration()
                    else -> Tag()
                }
            }.invoke()
            if (item.hasAttributes() || item.hasChildNodes()) {
                childTag.processAttributes(item)
                processElements(childTag, item.childNodes)
                tag.children.add(childTag)
            }
            if (item.nodeType == Node.TEXT_NODE) {
                tag.processText(item)
            }
        }
    }
}