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

package com.sixrq.kaxb.generators

import com.sixrq.kaxb.parsers.XmlParser
import spock.lang.Specification

class ObjectFactoryGeneratorTest extends Specification {
    def "An ObjectFactory is correctly generated for a single class"() {
        given: "a parsed schema with a single class and an ObjectFactoryGenerator"
        def parser = new XmlParser("StandAloneComplexType.xsd", "com.example")
        def classes = parser.generate()
        def objectFactoryGenerator = new ObjectFactoryGenerator("com.example")
        objectFactoryGenerator.addObject(classes.keySet()[0])

        when: "the ObjectFactory is generated"
        def objectFactory = objectFactoryGenerator.gerenateObjectFactory()

        then: "the ObjectFactory is correctly formed"
        objectFactory == expectedSingleClassObjectFactory
    }

    def "An ObjectFactory is correctly generated for a single class and QName"() {
        given: "a parsed schema with a single class and an ObjectFactoryGenerator"
        def parser = new XmlParser("QName.xsd", "com.example")
        def classes = parser.generate()
        def objectFactoryGenerator = new ObjectFactoryGenerator("com.example")
        objectFactoryGenerator.addObject(classes.keySet()[0])
        objectFactoryGenerator.addQName(classes.values()[1])

        when: "the ObjectFactory is generated"
        def objectFactory = objectFactoryGenerator.gerenateObjectFactory()

        then: "the ObjectFactory is correctly formed"
        objectFactory == expectedQNameObjectFactory
    }

    def expectedQNameObjectFactory = "com.example\n" +
            "\n" +
            "import javax.xml.bind.JAXBElement\n" +
            "import javax.xml.bind.annotation.XmlElementDecl\n" +
            "import javax.xml.bind.annotation.XmlRegistry\n" +
            "import javax.xml.namespace.QName\n" +
            "\n" +
            "/**\n" +
            " * This object contains factory methods for each\n" +
            " * Kotlin content interface and Kotlin element interface\n" +
            " * generated in the com.example package.\n" +
            " * <p>An ObjectFactory allows you to programatically\n" +
            " * construct new instances of the Kotlin representation\n" +
            " * for XML content. The Kotlin representation of XML\n" +
            " * content can consist of schema derived interfaces\n" +
            " * and classes representing the binding of schema\n" +
            " * type definitions, element declarations and model\n" +
            " * groups.  Factory methods for each of these are\n" +
            " * provided in this class.\n" +
            " *\n" +
            " */\n" +
            "\n" +
            "@XmlRegistry\n" +
            "class ObjectFactory {\n" +
            "\n" +
            "    private val _QNameEntry_QNAME = QName(\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", \"QNameEntry\")\n" +
            "\n" +
            "    /**\n" +
            "     * Create an instance of {@link StandAloneComplexType }\n" +
            "     *\n" +
            "     */\n" +
            "    fun createStandAloneComplexType: StandAloneComplexType { return StandAloneComplexType() }\n" +
            "\n" +
            "    /**\n" +
            "     * Create an instance of {@link JAXBElement }{@code <}{@link QNameEntry }{@code >}}\n" +
            "     *\n" +
            "     */\n" +
            "    @XmlElementDecl(namespace = \"http://www.garmin.com/xmlschemas/GpxExtensions/v3\", name = \"QNameEntry\")\n" +
            "    public JAXBElement<StandAloneComplexType> createQNameEntry(StandAloneComplexType value) {\n" +
            "        return new JAXBElement<StandAloneComplexType>(_QNameEntry_QNAME, StandAloneComplexType.class, null, value);\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "}\n"

    def expectedSingleClassObjectFactory = "com.example\n" +
            "\n" +
            "import javax.xml.bind.JAXBElement\n" +
            "import javax.xml.bind.annotation.XmlElementDecl\n" +
            "import javax.xml.bind.annotation.XmlRegistry\n" +
            "import javax.xml.namespace.QName\n" +
            "\n" +
            "/**\n" +
            " * This object contains factory methods for each\n" +
            " * Kotlin content interface and Kotlin element interface\n" +
            " * generated in the com.example package.\n" +
            " * <p>An ObjectFactory allows you to programatically\n" +
            " * construct new instances of the Kotlin representation\n" +
            " * for XML content. The Kotlin representation of XML\n" +
            " * content can consist of schema derived interfaces\n" +
            " * and classes representing the binding of schema\n" +
            " * type definitions, element declarations and model\n" +
            " * groups.  Factory methods for each of these are\n" +
            " * provided in this class.\n" +
            " *\n" +
            " */\n" +
            "\n" +
            "@XmlRegistry\n" +
            "class ObjectFactory {\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * Create an instance of {@link StandAloneComplexType }\n" +
            "     *\n" +
            "     */\n" +
            "    fun createStandAloneComplexType: StandAloneComplexType { return StandAloneComplexType() }\n" +
            "\n" +
            "\n" +
            "}\n"
}
