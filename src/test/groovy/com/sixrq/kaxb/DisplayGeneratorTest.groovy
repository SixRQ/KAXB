package com.sixrq.kaxb

import com.sixrq.kaxb.generators.DisplayGenerator
import spock.lang.Specification

class DisplayGeneratorTest extends Specification {
    def "I can parse an XML document"() {
        given: "an xml parser"
        def parser = new DisplayGenerator(this.getClass().getResource("/GpxExtensionsv3.xsd").toURI().getSchemeSpecificPart(), "com.sixrq.generated")

        when: "I parse the document"
        parser.readAndDisplayDocument()

        then: "I can read the file"
        true
    }
}
