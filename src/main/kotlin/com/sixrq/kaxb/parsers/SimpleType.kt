/*
 *    Copyright 2017 SixRQ Ltd.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.sixrq.kaxb.parsers

class SimpleType(xmlns: String, val packageName: String) : Tag(xmlns) {
    override fun toString(): String{
        if (children.filter { it.children.isNotEmpty() && it.children.filter { it is Enumeration }.isNotEmpty() }.isNotEmpty()) {
            return processEnumerationClass()
        }
        return ""
    }

    private fun processEnumerationClass(): String {
        val classDef = StringBuilder()
        val documentation = StringBuilder()

        children.filter { it is Annotation }.forEach {
            it.children.filter{ document -> document is Documentation }.
                    forEach { comment -> documentation.append("${comment.toString()}\n")}
        }

        classDef.append("$packageName\n\n")
        classDef.append("import javax.xml.bind.annotation.XmlEnum\n")
        classDef.append("import javax.xml.bind.annotation.XmlEnumValue\n")
        classDef.append("import javax.xml.bind.annotation.XmlType\n")

        if(documentation.isNotBlank()) {
            classDef.append("\n$documentation\n")
        }
        classDef.append("\n@XmlType(name = \"$elementName\", namespace = \"$xmlns\")")
        classDef.append("\n@XmlEnum")
        classDef.append("\nenum class $name(${appendType()}) {\n")
        children.filter{ restriction -> restriction is Restriction }.flatMap { it.children.filter { enum -> enum is Enumeration } }.forEach { member ->
            classDef.append("$member\n")
        }
        classDef.setLength(classDef.length-2)
        classDef.append(";\n")

        if (appendType().isNotEmpty()) {
            classDef.append("\n    companion object {\n")
            classDef.append("        fun fromValue(${appendType().replace("val ", "")}): $name = $name.values().first { it.value == value }\n")
            classDef.append("    }\n")
        }
        classDef.append("}\n")
        return classDef.toString()

    }

    private fun appendType() : String {
        val restriction = (children.filter { it is Restriction })[0] as Restriction
        if (restriction.type.isNotBlank()) {
            return "val value : ${restriction.extractType()} "
        }
        return ""
    }

}
