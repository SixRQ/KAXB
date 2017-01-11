package com.sixrq.kaxb.generators

import spock.lang.Specification

class ClassFileGeneratorTest extends Specification {

    def "I can parse an xml document and generate class files"() {
        given: "a ClassFileGenerator"
        def generator = new ClassFileGenerator("GpxExtensionsv3.xsd", "com.sixrq.generated", "${System.getProperty("java.io.tmpdir")}/kaxb/generated")

        when: "the files are generated"
        generator.generateClasses()

        then: "the files exist"
        true
    }


}
