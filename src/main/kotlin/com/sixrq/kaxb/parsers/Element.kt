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

import org.w3c.dom.Node

open class Element(xmlns: String, val primitiveTypeMapping: MutableMap<String, String>) : Tag(xmlns) {
    init {
        imports = mutableListOf("javax.xml.bind.annotation.XmlElement")
    }

    override fun processAttributes(item: Node?) {
        super.processAttributes(item)
        if (getSchemaType().isNotEmpty()) {
            imports.add("javax.xml.bind.annotation.XmlSchemaType")
        }
    }

    override fun toString(): String {
        return "    @XmlElement(name = \"${name}\", namespace = \"$xmlns\")\n" +
                "${getSchemaType()}" +
                "    ${getLateinit()}var ${getPropertyName()} : ${getTypeDefinition()}"
    }

    override fun extractType(): String {
        val className = extractClassName(type)
        if (primitiveTypeMapping.containsKey(className)) {
            return primitiveTypeMapping[className].toString()
        } else {
            return super.extractType()
        }
    }

    open fun getTypeDefinition(): String {
        if (maxOccurs.isNotBlank()) {
            return "MutableList<${extractType()}> = mutableListOf()"
        }
        return extractType()
    }

    open fun getLateinit(): String {
        if (maxOccurs.isNotBlank() || primitiveTypeMapping.containsKey(extractClassName(type))) {
            return ""
        }
        return "lateinit "
    }

    open fun getSchemaType(): String {
        if (maxOccurs.isBlank()) {
            when (type.toLowerCase()) {
                "xsd:token" -> return "    @XmlSchemaType(\"token\")\n"
                else -> return ""
            }
        }
        return ""
    }
}