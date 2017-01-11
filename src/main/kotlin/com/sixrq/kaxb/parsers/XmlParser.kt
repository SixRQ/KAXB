package com.sixrq.kaxb.parsers

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class XmlParser(val filename: String, val packageName: String) {
    val root: Element by lazy {
        val xmlFile = File(ClassLoader.getSystemClassLoader().getResource(filename).toURI().schemeSpecificPart)
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        dBuilder.parse(xmlFile).documentElement
    }
    val xmlns: String by lazy { root.getAttribute("xmlns") }
    val primitiveTypeMapping: MutableMap<String, String> = hashMapOf()


    fun generate() : Map<String, Tag> {
        val elements = root.childNodes
        val schema = Schema(xmlns)
        val classes: MutableMap<String, Tag> = hashMapOf()
        processElements(schema, elements)

        schema.includes.forEach {
            val xmlParser = XmlParser(it, packageName)
            classes.putAll(xmlParser.generate())
        }
        primitiveTypeMapping.putAll(extractBasicTypes(schema))
        classes.putAll(extractEnumerations(schema))
        classes.putAll(extractClasses(schema))
        classes.putAll(extractQNames(schema))

        return classes
    }

    private fun extractEnumerations(schema: Schema) : Map<String, Tag> {
        val enumerations: MutableMap<String, Tag> = hashMapOf()
        schema.children.filter {
            it is SimpleType &&
                    it.children.filter {
                        it is Restriction &&
                                it.children.filter { it is Enumeration }.isNotEmpty()
                    }.isNotEmpty()
        }.forEach {
            enumerations.put(it.name, it)
        }
        return enumerations
    }

    private fun extractQNames(schema: Schema) : Map<String, Tag> {
        val qNames: MutableMap<String, Tag> = hashMapOf()
        schema.children.filter { it is com.sixrq.kaxb.parsers.Element }.forEach {
            qNames.put(it.name, it)
        }
        return qNames
    }

    private fun extractClasses(schema: Schema) : Map<String, Tag> {
        val classes: MutableMap<String, Tag> = hashMapOf()
        schema.children.filter { it is ComplexType }.forEach {
            classes.put(it.name, it)
        }
        return classes
    }

    private fun extractBasicTypes(schema: Schema) : Map<String, String> {
        val basicTypes: MutableMap<String, String> = hashMapOf()
        schema.children.filter { it is SimpleType &&
                it.children.filter { it is Restriction &&
                        it.children.filter { it is Enumeration }.isEmpty()}.isNotEmpty()}.forEach {
            basicTypes.put(it.name, (it.children.filter { it is Restriction }[0] as Restriction).extractType())
        }
        return basicTypes
    }

    private fun processElements(tag: Tag, elements: NodeList) {
        for (index in 0..(elements.length - 1)) {
            val item = elements.item(index)
            val childTag = {
                when (item.nodeName) {
                    "xsd:complexType" -> ComplexType(xmlns, packageName)
                    "xsd:simpleType" -> SimpleType(xmlns, packageName)
                    "xsd:element" -> Element(xmlns, primitiveTypeMapping)
                    "xsd:any" -> AnyElement(xmlns, primitiveTypeMapping)
                    "xsd:extension" -> Extension(xmlns, primitiveTypeMapping)
                    "xsd:annotation" -> Annotation(xmlns)
                    "xsd:documentation" -> Documentation(xmlns)
                    "xsd:sequence" -> Sequence(xmlns)
                    "xsd:restriction" -> Restriction(xmlns)
                    "xsd:enumeration" -> Enumeration(xmlns)
                    "xsd:simpleContent" -> SimpleContent(xmlns)
                    "xsd:attribute" -> Attribute(xmlns, primitiveTypeMapping)
                    "xsd:include" -> Include(xmlns)
                    else -> Tag(xmlns)
                }
            }.invoke()
            if (item.hasAttributes() || item.hasChildNodes()) {
                childTag.processAttributes(item)
                processElements(childTag, item.childNodes)
                if (childTag is Include) {
                    tag.includes.add(childTag.schemaLocation)
                } else {
                    tag.children.add(childTag)
                }
            }
            if (item.nodeType == Node.TEXT_NODE) {
                tag.processText(item)
            }
        }
    }
}