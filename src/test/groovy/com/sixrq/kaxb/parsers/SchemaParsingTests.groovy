package com.sixrq.kaxb.parsers

import spock.lang.Specification

class SchemaParsingTests extends Specification {
    def "A Complex Type with a String field correctly generates a class"() {
        given: "A schema file with a single complex type containing a token type"
        def parser = new XmlParser("complexType.xsd", "com.example")

        when: "the classes are generated"
        def classes = parser.generate()

        then: "the class is correctly generated"
        classes.get("ComplexType").toString() == expectedComplexType
    }

    def expectedComplexType = "com.example\n" +
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
            "@XmlType(name = \"ComplexType\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", propOrder = arrayOf(\n" +
            "    \"stringToken\"\n" +
            "))\n" +
            "data class ComplexType {\n" +
            "    @XmlElement(name = \"StringToken\", namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\")\n" +
            "    @XmlSchemaType(\"token\")\n" +
            "    lateinit var stringToken : String\n" +
            "}\n"
}