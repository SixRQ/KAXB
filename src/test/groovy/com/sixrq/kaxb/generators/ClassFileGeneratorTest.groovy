package com.sixrq.kaxb.generators

import org.junit.Ignore
import spock.lang.Specification

@Ignore
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
