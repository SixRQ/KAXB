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

class Extension(xmlns: String, primitiveTypeMapping: MutableMap<String, String>) : Element(xmlns, primitiveTypeMapping) {
    init {
        imports= mutableListOf("javax.xml.bind.annotation.XmlValue",
            "javax.xml.bind.annotation.XmlJavaTypeAdapter")
        name = "value"
    }

    override fun toString(): String {
        val result = StringBuilder()
        result.append("    @XmlValue\n")
        result.append("    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)\n")
        result.append("${getSchemaType()}")
        result.append("    ${getLateinit()}var ${getPropertyName()} : ${getTypeDefinition()}\n")
        children.forEach { result.append(it.toString()) }
        return result.toString()
    }
}