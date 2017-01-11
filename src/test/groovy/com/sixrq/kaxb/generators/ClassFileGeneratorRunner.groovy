package com.sixrq.kaxb.generators

import spock.lang.Ignore
import spock.lang.Specification

//@Ignore
class ClassFileGeneratorRunner extends Specification {

    def "I can parse an xml document and generate class files"() {
        given: "a ClassFileGenerator"
        def generator = new ClassFileGenerator("GpxExtensionsv3.xsd", "com.sixrq.gpx", "generated")

        when: "the files are generated"
        generator.generateClasses()

        then: "the files exist"
        true
    }


}
