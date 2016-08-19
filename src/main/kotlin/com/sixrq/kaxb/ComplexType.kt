package com.sixrq.kaxb

class ComplexType(val xmlns: String, val packageName: String): Tag() {
    override fun toString(): String{
        val classDef  = StringBuilder()
        var documentation: String = ""
        var members : MutableList<Element> = mutableListOf()

        for (child in children) {
            if (child is Annotation) {
                documentation = child.children[0].toString()
            }
            if (child is Sequence) {
                members.addAll(processSequence(child))
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
        classDef.append("@XmlType(name = \"$elementName\", namespace = \"$xmlns\", propOrder = {\n")
        for (member in members) {
            classDef.append("    \"${member.name.replaceFirst(member.name[0], member.name[0].toLowerCase())}\",\n")
        }
        classDef.append("}")
        classDef.append("\ndata class $name ${appendType()}{\n")
        for (member in members) {
            classDef.append("    @XmlElement(name = \"${member.name}\", namespace = \"$xmlns\")\n")
            classDef.append("    $member\n")
        }
        classDef.append("}\n")
        return classDef.toString()
    }

    private fun processSequence(child: Tag): MutableList<Element> {
        val members : MutableList<Element> = mutableListOf()
        for (element in child.children) {
            if (element is Element) {
                members.add(element)
            }
        }
        return members
    }

    private fun appendType() : String {
        if (type.isNotBlank()) {
            return ": $type "
        }
        return ""
    }
}