package com.sixrq.kaxb

import org.w3c.dom.Element
import org.w3c.dom.Node

class ComplexType(val packageName: String, val xmlns: String, val xsdns: String, val item: Element) {
    val className: String by lazy { extractClassName(item.attributes.item(0).nodeValue) }

    val comment: String by lazy {
        val comments = StringBuilder()
        comments.append("/**\n")
        val elementsByTagName = item.getElementsByTagName("xsd:annotation")
        comments.append(when(elementsByTagName.length) {
            1 -> { processAnnotation(elementsByTagName.item(0)) }
            else -> ""
        })
        comments.append("\n *\n$schemaDefinition\n *\n**/\n\n")
        comments.toString().replace("\n\n", "\n")
    }

    val definition: String by lazy {
        val classDefinition = StringBuilder()
        classDefinition.append("data class ${className} {\n")
        classDefinition.append(processClassContent())
        classDefinition.append("}\n")
        classDefinition.toString()
    }

    private fun processClassContent(): String {
        val content = StringBuilder()
        val elementsByTagName = item.getElementsByTagName("xsd:sequence")
        if (elementsByTagName.item(0) != null) {
            for (item in arrayListOf(elementsByTagName.item(0).childNodes)) {
                if (item is Element) {
                    when (item.nodeName) {
                        "xsd:element" -> {
                            val varName = item.attributes.getNamedItem("name").nodeValue.decapitalize()
                            val varType =
                                    if (item.attributes.getNamedItem("maxOccurs") != null) {
                                        "List<${extractClassName(item.attributes.getNamedItem("type").nodeValue.replace("xsd:", "").replace("token", "String"))}>"
                                    } else {
                                        "${extractClassName(item.attributes.getNamedItem("type").nodeValue.replace("xsd:", "").replace("token", "String"))}"
                                    }
                            content.append("   @XmlElement(name = \"${item.attributes.getNamedItem("name").nodeValue}\", namespace = \"${xmlns}\"")
                            if (item.attributes.getNamedItem("maxOccurs") != null && item.attributes.getNamedItem("maxOccurs").nodeValue == "unbounded") {
                                content.append(", required = true)\n")
                            } else {
                                content.append(")\n")
                            }
                            content.append("   val ${varName}: ${varType}\n")
                        }
                    }
                }
            }
        }
        return content.toString()
    }

    val schemaDefinition: String by lazy {
        val definition = StringBuilder()
        definition.append(" * <p>Kotlin class for ${item.attributes.item(0).nodeValue} complex type\n *\n")
        definition.append(" * <p>The following schema fragment specifies the expected content contained within this class.\n *\n")
        definition.append(" * <pre>\n")
        definition.append(" * &lt;complexType name=\"${item.attributes.item(0).nodeValue}\">\n")
        definition.append(" *   &lt;complexContent>\n")
        definition.append(" *     &lt;restriction base=\"{${xsdns}}anyType\">\n")
        for (node in arrayListOf(item.childNodes)) {
            if (node is Element) {
                when (node.nodeName) {
                    "xsd:sequence" -> {
                        definition.append(" *       &lt;${node.nodeName.replace("xsd:", "")}>\n")
                        for (childIndex in 0..(node.childNodes.length - 1)) {
                            val childNode = node.childNodes.item(childIndex)
                            when (childNode) {
                                is Element -> {
                                    definition.append(" *         &lt;${childNode.nodeName.replace("xsd:", "")}")
                                    definition.append(" name=\"${childNode.getAttribute("name")}\"")
                                    definition.append(" type=\"{${xmlns}}${childNode.getAttribute("type")}\"")
                                    if (childNode.getAttribute("maxOccurs") != null)
                                        definition.append(" maxOccurs=\"${childNode.getAttribute("maxOccurs")}\"")
                                    if (childNode.getAttribute("maxOccurs") != null)
                                        definition.append(" minOccurs=\"${childNode.getAttribute("minOccurs")}\"")
                                    definition.append("/>\n")
                                }
                            }
                        }
                        definition.append(" *       &lt;/${node.nodeName.replace("xsd:", "")}>\n")
                    }
                }
            }
        }
        definition.append(" *     &lt;/restriction>\n")
        definition.append(" *   &lt;/complexContent>\n")
        definition.append(" * &lt;/complexType>\n")
        definition.append(" * </pre>\n *\n")
        definition.toString()
    }

    private fun extractClassName(name: String): String {
        val className = StringBuilder()
        name.split('_').forEach {
            className.append(it.capitalize())
        }
        return className.toString()
    }

    private fun processAnnotation(item: Node): String {
        val comment = StringBuilder()
        for (index in 0..(item.childNodes.length - 1)) {
            if (item.childNodes.item(index).hasChildNodes()) {
                comment.append(item.childNodes.item(index).firstChild.nodeValue)
            }
        }
        return " * ${comment.toString().replace("\n", "\n * ")}"
    }

    override fun toString(): String {
        return "package $packageName\n\n$comment\n$definition"
    }
}