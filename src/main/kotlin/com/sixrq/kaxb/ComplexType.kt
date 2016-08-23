package com.sixrq.kaxb

class ComplexType(xmlns: String, val packageName: String): Tag(xmlns) {
    override fun toString(): String{
        val classDef = StringBuilder()
        val documentation = StringBuilder()
        val properties : MutableList<Tag> = mutableListOf()

        children.filter { it is Annotation }.forEach {
                it.children.filter{ document -> document is Documentation}.
                        forEach { comment -> documentation.append("${comment.toString()}\n")}
            }

        children.filter{ propertyGroup -> propertyGroup is Sequence }.forEach { properties.addAll(it.children.filter { it is Element }) }

        classDef.append("$packageName\n\n")
        classDef.append("import javax.xml.bind.annotation.XmlAccessType\n")
        classDef.append("import javax.xml.bind.annotation.XmlAccessorType\n")
        classDef.append("import javax.xml.bind.annotation.XmlElement\n")
        classDef.append("import javax.xml.bind.annotation.XmlSchemaType\n")
        classDef.append("import javax.xml.bind.annotation.XmlType\n")

        if(documentation.isNotBlank()) {
            classDef.append("\n$documentation\n")
        }
        classDef.append("\n@XmlAccessorType(XmlAccessType.FIELD)\n")
        classDef.append("@XmlType(name = \"$elementName\", namespace = \"$xmlns\", propOrder = arrayOf(\n")
        properties.forEach { property ->
            classDef.append("    \"${property.getPropertyName()}\",\n")
        }
        classDef.setLength(classDef.length-2)
        classDef.append("\n))")
        classDef.append("\nclass $name ${appendType()}{\n")
        properties.forEach { property ->
            classDef.append("$property\n")
        }
        classDef.append("}\n")
        return classDef.toString()
    }

    private fun appendType() : String {
        if (type.isNotBlank()) {
            return ": $type "
        }
        return ""
    }
}