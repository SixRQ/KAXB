package com.sixrq.kaxb.generators

import org.junit.Ignore
import spock.lang.Specification

@Ignore
class DisplayGeneratorTest extends Specification {
    def "I can parse an XML document"() {
        given: "a Display Generator"
        def parser = new DisplayGenerator("GpxExtensionsv3.xsd", "com.sixrq.generated")

        when: "I parse the document"
        parser.readAndDisplayDocument()

        then: "I can read the file"
        true
    }
}
