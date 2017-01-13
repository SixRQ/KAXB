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

class ComplexType(xmlns: String, val packageName: String): Tag(xmlns) {
    override fun toString(): String{
        val classDef = StringBuilder()
        val documentation = StringBuilder()
        val properties : MutableList<Tag> = mutableListOf()

        children.filter { annotation -> annotation is Annotation }.forEach {
                it.children.filter{ document -> document is Documentation }.
                        forEach { comment -> documentation.append("${comment.toString()}\n")}
            }

        properties.addAll(children.filter{ propertyGroup -> propertyGroup is Sequence || propertyGroup is SimpleContent }.
                flatMap { it.children.filter { element -> element is Element || element is AnyElement || element is Restriction } })
        classDef.append("$packageName\n\n")
        classDef.append("import javax.xml.bind.annotation.XmlAccessType\n")
        classDef.append("import javax.xml.bind.annotation.XmlAccessorType\n")
        classDef.append("import javax.xml.bind.annotation.XmlType\n")

        children.filter{ propertyGroup -> propertyGroup is Sequence || propertyGroup is SimpleContent }.
                flatMap { it.children.filter { element -> element is Element || element is AnyElement || element is Restriction } }.
                flatMap {  it.imports }.distinct().forEach { classDef.append( "import $it\n" ) }

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
        classDef.append("\ndata class $name ${appendType()}{\n")
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