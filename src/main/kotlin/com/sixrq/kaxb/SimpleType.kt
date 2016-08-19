package com.sixrq.kaxb

class SimpleType(val xmlns: String, val packageName: String) : Tag() {
    override fun toString(): String{
        for (child in children) {
            if (child.children.isNotEmpty() && child.children[0] is Enumeration) {
                return processEnumerationClass()
            }
        }
        return ""
    }

    private fun processEnumerationClass(): String {
        val classDef  = StringBuilder()
        var documentation: String = ""
        var members : MutableList<Enumeration> = mutableListOf()

        for (child in children) {
            if (child is Annotation) {
                documentation = child.children[0].toString()
            }
            if (child is Restriction) {
                members.addAll(processEnumeration(child))
            }
        }

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
        classDef.append("@XmlType(name = \"$elementName\", namespace = \"$xmlns\")")
        classDef.append("\nenum class $name(${appendType()}) {\n")
        for (member in members) {
            classDef.append("    $member\n")
        }
        classDef.setLength(classDef.length-2)
        classDef.append(";\n")

        if (appendType().isNotEmpty()) {
            classDef.append("\n    companion object {\n")
            classDef.append("        fun from(${appendType().replace("val ", "")}): $name = $name.values().first { it.value == value }\n")
            classDef.append("    }\n")
        }
        classDef.append("}\n")
        return classDef.toString()

    }

    private fun processEnumeration(child: Tag): MutableList<Enumeration> {
        val members : MutableList<Enumeration> = mutableListOf()
        for (element in child.children) {
            if (element is Enumeration) {
                members.add(element)
            }
        }
        return members
    }

    private fun appendType() : String {
        val restriction = children.filter { it is Restriction }
        if (restriction[0].type.isNotBlank()) {
            return "val value : ${extractType(restriction[0].type)} "
        }
        return ""
    }

    private fun extractType(type: String) : String {
        when (type.toLowerCase()) {
            "xsd:string" -> return "String"
            "xsd:token" -> return "String"
            else -> return extractClassName(type)
        }
    }
}
