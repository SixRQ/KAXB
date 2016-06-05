package com.sixrq.kaxb

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

class XmlParser(val filename: String) {
    fun getDocumentFromFile(): Document {
        val xmlFile = File(filename)
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        return dBuilder.parse(xmlFile)
    }

    fun readAndDisplayDocument(document: Document) {
        val root = document.documentElement
        val xmlns = root.getAttribute("xmlns")
        println(xmlns)
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
                        "xsd:complexType" -> generatedClass.append(processComplexType(item))
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

    private fun processComplexType(node: Node): Pair<String, String> {
        val complexType = StringBuilder()
        val className = extractClassName(node.attributes.item(0).nodeValue)
        val content = processDefinition(node)
        if (content.containsKey("comment")) {
            complexType.append(content.get("comment"))
        }
        complexType.append("data class $className {\n")
        complexType.append("}\n\n\n")
        return Pair(className, complexType.toString())
    }

    private fun processDefinition(node: Node): Map<String, String> {
        var comments = StringBuilder()
        val definition = StringBuilder()
        for (index in 0..(node.childNodes.length - 1)) {
            val item = node.childNodes.item(index)
            when (item) {
                is Element -> {
                    when (item.nodeName) {
                        "xsd:annotation" -> comments.append(processAnnotation(item))
//                        "xsd:sequence" -> definition.append(processSequence(item))
                    }
                }
            }
        }
        val result = TreeMap<String, String>()
        if (comments.toString().isNotBlank()) {
            result.put("comment", comments.toString())
        }
        if (definition.toString().isNotBlank()) {
            result.put("definition", definition.toString())
        }
        return result
    }

    private fun processAnnotation(item: Element): String {
        val result = StringBuilder()
        val comment = StringBuilder()
        for (index in 0..(item.childNodes.length - 1)) {
            if (item.childNodes.item(index).hasChildNodes()) {
                comment.append(item.childNodes.item(index).firstChild.nodeValue)
            }
        }
        result.append("/**\n")
        result.append(" * ${comment.toString().replace("\n", "\n * ")}")
        result.append("\n**/\n\n")
        return result.toString()
    }

    private fun extractClassName(name: String): String {
        val className = StringBuilder()
        name.split('_').forEach {
            className.append(it.capitalize())
        }
        return className.toString()
    }
}