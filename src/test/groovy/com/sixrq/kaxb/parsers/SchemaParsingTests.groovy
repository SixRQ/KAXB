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

import spock.lang.Specification

class SchemaParsingTests extends Specification {
    def "A Complex Type with a String field correctly generates a class"() {
        given: "A schema file with a single complex type containing a token type"
        def parser = new XmlParser("StandAloneComplexType.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("StandAloneComplexType").toString() == expectedStandAloneComplexType
    }

    def "A Complex Type with a String field and QName correctly generates a class"() {
        given: "A schema file with a single complex type containing a token type"
        def parser = new XmlParser("QName.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("StandAloneComplexType").toString() == expectedStandAloneComplexType
        classes.get("QNameEntry").toString() == expectedQNameEntry
    }

    def "A Complex Type with Simple Type elements correctly generates a class"() {
        given: "A schema file with a single complex type containing a simple type"
        def parser = new XmlParser("SimpleType.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("ComplexType").toString() == expectedSimpleType
    }

    def "A Complex Type with Simple Type collection elements correctly generates a class"() {
        given: "A schema file with a single complex type containing a simple type"
        def parser = new XmlParser("SimpleTypeWithMaxOccurs.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("ComplexType").toString() == expectedSimpleTypeWithMaxOccurs
    }

    def "A Complex Type with an include and element of included type correctly generates a class"() {
        given: "A schema file with a single complex type containing a simple type"
        def parser = new XmlParser("ComplexTypeWithInclude.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("StandAloneComplexType").toString() == expectedStandAloneComplexType
        classes.get("IncludeComplexType").toString() == expectedIncludeComplexType
    }

    def "A Simple Enumerated Type correctly generates an enum"() {
        given: "A schema file with an enumerated simple type"
        def parser = new XmlParser("EnumeratedType.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("Enumeration").toString() == expectedEnumType
    }

    def "A Complex Type with an Any tag correctly generates an enum"() {
        given: "A schema file with an enumerated simple type"
        def parser = new XmlParser("ComplexTypeWithAny.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("AnyType").toString() == expectedAnyType
    }

    def "A Complex Type with SimpleContent correctly generates an class"() {
        given: "A schema file with an enumerated simple type"
        def parser = new XmlParser("ComplexTypeWithSimpleContent.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("Pair").toString() == expectedSimpleContent
    }



    def expectedAnyType = "com.example\n" +
            "\n" +
            "import javax.xml.bind.annotation.XmlAccessType\n" +
            "import javax.xml.bind.annotation.XmlAccessorType\n" +
            "import javax.xml.bind.annotation.XmlType\n" +
            "import javax.xml.bind.annotation.XmlAnyElement\n" +
            "\n" +
            "/**\n" +
            "* This type provides the ability to extend any data type that includes it.\n" +
            "*/\n" +
            "\n" +
            "\n" +
            "@XmlAccessorType(XmlAccessType.FIELD)\n" +
            "@XmlType(name = \"AnyType\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", propOrder = arrayOf(\n" +
            "    \"any\"\n" +
            "))\n" +
            "data class AnyType {\n" +
            "    @XmlAnyElement(lax = true)\n" +
            "    var any : MutableList<Any> = mutableListOf()\n" +
            "}\n"

    def expectedSimpleContent = "com.example\n" +
            "\n" +
            "import javax.xml.bind.annotation.XmlAccessType\n" +
            "import javax.xml.bind.annotation.XmlAccessorType\n" +
            "import javax.xml.bind.annotation.XmlType\n" +
            "import javax.xml.bind.annotation.XmlValue\n" +
            "import javax.xml.bind.annotation.XmlJavaTypeAdapter\n" +
            "import javax.xml.bind.annotation.XmlSchemaType\n" +
            "\n" +
            "@XmlAccessorType(XmlAccessType.FIELD)\n" +
            "@XmlType(name = \"Pair\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", propOrder = arrayOf(\n" +
            "    \"value\"\n" +
            "))\n" +
            "data class Pair {\n" +
            "    @XmlValue\n" +
            "    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)\n" +
            "    @XmlSchemaType(\"token\")\n" +
            "    lateinit var value : String\n" +
            "    @XmlValue\n" +
            "    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)\n" +
            "    @XmlSchemaType(\"token\")\n" +
            "    lateinit var name : String\n" +
            "\n" +
            "}\n"

    def expectedIncludeComplexType = "com.example\n" +
            "\n" +
            "import javax.xml.bind.annotation.XmlAccessType\n" +
            "import javax.xml.bind.annotation.XmlAccessorType\n" +
            "import javax.xml.bind.annotation.XmlType\n" +
            "import javax.xml.bind.annotation.XmlElement\n" +
            "import javax.xml.bind.annotation.XmlSchemaType\n" +
            "\n" +
            "/**\n" +
            "* \n" +
            "*    A sample complex type for testing\n" +
            "*    \n" +
            "*/\n" +
            "\n" +
            "\n" +
            "@XmlAccessorType(XmlAccessType.FIELD)\n" +
            "@XmlType(name = \"IncludeComplexType\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", propOrder = arrayOf(\n" +
            "    \"stringToken\",\n" +
            "    \"standAloneComplexType\"\n" +
            "))\n" +
            "data class IncludeComplexType {\n" +
            "    @XmlElement(name = \"StringToken\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\")\n" +
            "    @XmlSchemaType(\"token\")\n" +
            "    lateinit var stringToken : String\n" +
            "    @XmlElement(name = \"StandAloneComplexType\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\")\n" +
            "    lateinit var standAloneComplexType : StandAloneComplexType\n" +
            "}\n"

    def expectedEnumType = "com.example\n" +
            "\n" +
            "import javax.xml.bind.annotation.XmlEnum\n" +
            "import javax.xml.bind.annotation.XmlEnumValue\n" +
            "import javax.xml.bind.annotation.XmlType\n" +
            "\n" +
            "/**\n" +
            "* \n" +
            "*      A sample enumerated type for testing\n" +
            "*    \n" +
            "*/\n" +
            "\n" +
            "\n" +
            "@XmlType(name = \"Enumeration\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\")\n" +
            "@XmlEnum\n" +
            "enum class Enumeration(val value : String ) {\n" +
            "    @XmlEnumValue(\"Enum1\")\n" +
            "    ENUM1(\"Enum1\"),\n" +
            "    @XmlEnumValue(\"Enum2\")\n" +
            "    ENUM2(\"Enum2\"),\n" +
            "    @XmlEnumValue(\"Enum3\")\n" +
            "    ENUM3(\"Enum3\");\n" +
            "\n" +
            "    companion object {\n" +
            "        fun fromValue(value : String ): Enumeration = Enumeration.values().first { it.value == value }\n" +
            "    }\n" +
            "}\n"

    def expectedSimpleTypeWithMaxOccurs = "com.example\n" +
            "\n" +
            "import javax.xml.bind.annotation.XmlAccessType\n" +
            "import javax.xml.bind.annotation.XmlAccessorType\n" +
            "import javax.xml.bind.annotation.XmlType\n" +
            "import javax.xml.bind.annotation.XmlElement\n" +
            "\n" +
            "@XmlAccessorType(XmlAccessType.FIELD)\n" +
            "@XmlType(name = \"ComplexType\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", propOrder = arrayOf(\n" +
            "    \"simpleDouble\"\n" +
            "))\n" +
            "data class ComplexType {\n" +
            "    @XmlElement(name = \"SimpleDouble\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\")\n" +
            "    var simpleDouble : MutableList<Double> = mutableListOf()\n" +
            "}\n"

    def expectedSimpleType = "com.example\n" +
            "\n" +
            "import javax.xml.bind.annotation.XmlAccessType\n" +
            "import javax.xml.bind.annotation.XmlAccessorType\n" +
            "import javax.xml.bind.annotation.XmlType\n" +
            "import javax.xml.bind.annotation.XmlElement\n" +
            "\n" +
            "@XmlAccessorType(XmlAccessType.FIELD)\n" +
            "@XmlType(name = \"ComplexType\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", propOrder = arrayOf(\n" +
            "    \"simpleDouble\"\n" +
            "))\n" +
            "data class ComplexType {\n" +
            "    @XmlElement(name = \"SimpleDouble\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\")\n" +
            "    var simpleDouble : Double\n" +
            "}\n"

    def expectedStandAloneComplexType = "com.example\n" +
            "\n" +
            "import javax.xml.bind.annotation.XmlAccessType\n" +
            "import javax.xml.bind.annotation.XmlAccessorType\n" +
            "import javax.xml.bind.annotation.XmlType\n" +
            "import javax.xml.bind.annotation.XmlElement\n" +
            "import javax.xml.bind.annotation.XmlSchemaType\n" +
            "\n" +
            "/**\n" +
            "* \n" +
            "*    A sample complex type for testing\n" +
            "*    \n" +
            "*/\n" +
            "\n" +
            "\n" +
            "@XmlAccessorType(XmlAccessType.FIELD)\n" +
            "@XmlType(name = \"StandAloneComplexType\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", propOrder = arrayOf(\n" +
            "    \"stringToken\"\n" +
            "))\n" +
            "data class StandAloneComplexType {\n" +
            "    @XmlElement(name = \"StringToken\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\")\n" +
            "    @XmlSchemaType(\"token\")\n" +
            "    lateinit var stringToken : String\n" +
            "}\n"

    def expectedQNameEntry = "    @XmlElement(name = \"QNameEntry\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\")\n" +
            "    lateinit var qNameEntry : StandAloneComplexType"
}