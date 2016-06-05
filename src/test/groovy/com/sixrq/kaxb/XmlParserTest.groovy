package com.sixrq.kaxb

import org.w3c.dom.Document
import org.w3c.dom.Element
import spock.lang.Specification

class XmlParserTest extends Specification {
    def "I can parse an XML document"() {
        given: "an xml parser"
        def parser = new XmlParser(this.getClass().getResource("/GpxExtensionsv3.xsd").toURI().getSchemeSpecificPart(), "com.sixrq.generated")

        when: "I parse the document"
        parser.readAndDisplayDocument()

        then: "I can read the file"
        true
    }
}
