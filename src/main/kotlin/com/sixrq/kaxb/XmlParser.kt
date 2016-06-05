package com.sixrq.kaxb

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class XmlParser(val filename: String) {
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
        println(processElements(elements))
    }

    private fun processElements(elements: NodeList): String {
        val generatedClass = StringBuilder()
        for (index in 0..(elements.length - 1)) {
            val item = elements.item(index)
            when (item) {
                is Element -> {
                    when (item.nodeName) {
                        "xsd:complexType" -> {
                            val complexType = ComplexType(xmlns, xsdns, item)
                            generatedClass.append("${complexType.className}, ${complexType.comment}\n${complexType.definition}")
                        }
                        "xsd:simpleType" -> generatedClass.append(processSimpleType(item))
                        else -> generatedClass.append("${item.nodeName}\n")
                    }
                }
            }
        }
        return generatedClass.toString()
    }

    private fun processSimpleType(node: Node?): String {
        return "Simple Type\n"
    }
}