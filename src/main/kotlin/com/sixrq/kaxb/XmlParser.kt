package com.sixrq.kaxb

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.util.*
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
        println(processElements(elements))
    }

    private fun processElements(elements: NodeList): String {
        val generatedClass = StringBuilder()
        // Iterate for SimpleTypes
        val simpleTypeMap = HashMap<String, String>()
        for (index in 0..(elements.length -1 )) {
            val item = elements.item(index)
            when (item) {
                is Element -> {
                    when (item.nodeName) {
                        "xsd:simpleType" -> {
                            val simpleType = SimpleType(item)
                            simpleTypeMap.put(simpleType.key, simpleType.value)
                        }
                    }
                }
            }
        }
        // Iterate a second time for complex types
        for (index in 0..(elements.length -1 )) {
            val item = elements.item(index)
            when (item) {
                is Element -> {
                    when (item.nodeName) {
                        "xsd:complexType" -> {
                            val complexType = ComplexType(packageName, xmlns, xsdns, simpleTypeMap, item)
                            generatedClass.append("${complexType.className}, $complexType")
                        }
                    }
                }
            }
        }
        return generatedClass.toString()
    }

}