/*
 *    Copyright 2017 Simon Wiehe
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

import org.junit.Ignore
import spock.lang.Specification

import static groovy.io.FileType.FILES

class ClassFileGeneratorTest extends Specification {

    def "I can parse an xml document and generate class files"() {
        given: "a ClassFileGenerator"
        def uuid = UUID.randomUUID()
        def targetDirectory = "${System.getProperty("java.io.tmpdir")}/{$uuid}/kaxb/generated"
        def generator = new ClassFileGenerator("StandAloneComplexType.xsd", "com.example", targetDirectory)

        when: "the files are generated"
        generator.generateClasses()

        then: "the correct files exist"
        def directory = new File("${targetDirectory}/com/example")
        def files = []
        directory.traverse(type: FILES, maxDepth: 0) { files.add(it.getProperties()["name"]) }
        files.size() == 2
        files.contains("ObjectFactory.kt")
        files.contains("StandAloneComplexType.kt")
        expectedStandAloneComplexType == new File("${targetDirectory}/com/example/StandAloneComplexType.kt").text
        expectedSingleClassObjectFactory == new File("${targetDirectory}/com/example/ObjectFactory.kt").text
    }

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
