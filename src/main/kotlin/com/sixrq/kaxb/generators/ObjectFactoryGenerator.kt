package com.sixrq.kaxb.generators

import com.sixrq.kaxb.parsers.Tag

class ObjectFactoryGenerator(val packageName: String) {
    val classNames: MutableList<String> = mutableListOf()
    val qNames: MutableList<Tag> = mutableListOf()

    fun addObject(className: String) {
        classNames.add(className)
    }

    fun addQName(qname: Tag) {
        qNames.add(qname)
    }

    fun gerenateObjectFactory(): String {
        val builder = StringBuilder()
        builder.append("$packageName\n\n")
        builder.append("import javax.xml.bind.JAXBElement\n")
        builder.append("import javax.xml.bind.annotation.XmlElementDecl\n")
        builder.append("import javax.xml.bind.annotation.XmlRegistry\n")
        builder.append("import javax.xml.namespace.QName\n\n")

        builder.append("/**\n")
        builder.append(" * This object contains factory methods for each\n")
        builder.append(" * Kotlin content interface and Kotlin element interface\n")
        builder.append(" * generated in the $packageName package.\n")
        builder.append(" * <p>An ObjectFactory allows you to programatically\n")
        builder.append(" * construct new instances of the Kotlin representation\n")
        builder.append(" * for XML content. The Kotlin representation of XML\n")
        builder.append(" * content can consist of schema derived interfaces\n")
        builder.append(" * and classes representing the binding of schema\n")
        builder.append(" * type definitions, element declarations and model\n")
        builder.append(" * groups.  Factory methods for each of these are\n")
        builder.append(" * provided in this class.\n")
        builder.append(" *\n")
        builder.append(" */\n\n")

        builder.append("@XmlRegistry\n")
        builder.append("class ObjectFactory {\n\n")
        qNames.forEach { qname ->
            builder.append("    private val _${qname.elementName}_QNAME = QName(\"${qname.xmlns}\", \"${qname.elementName}\")\n")
        }
        builder.append("\n")
        classNames.forEach { className ->
            builder.append("    /**\n")
            builder.append("     * Create an instance of {@link $className }\n")
            builder.append("     *\n")
            builder.append("     */\n")
            builder.append("    fun create$className: $className { return $className() }\n\n")
        }
        qNames.forEach { qname ->
            builder.append("    /**\n")
            builder.append("     * Create an instance of {@link JAXBElement }{@code <}{@link ${qname.elementName} }{@code >}}\n")
            builder.append("     *\n")
            builder.append("     */\n")
            builder.append("    @XmlElementDecl(namespace = \"${qname.xmlns}\", name = \"${qname.elementName}\")\n")
            builder.append("    public JAXBElement<${qname.type}> create${qname.elementName}(${qname.type} value) {\n")
            builder.append("        return new JAXBElement<${qname.type}>(_${qname.elementName}_QNAME, ${qname.type}.class, null, value);\n")
            builder.append("    }\n\n")
        }
        builder.append("\n}\n")
        return builder.toString()
    }
}