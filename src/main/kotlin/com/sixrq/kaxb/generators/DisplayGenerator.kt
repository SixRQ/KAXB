package com.sixrq.kaxb.generators

import com.sixrq.kaxb.parsers.XmlParser

class DisplayGenerator(filename: String, packageName: String){

    private val parser = XmlParser(filename, packageName)

    fun readAndDisplayDocument() {
        parser.generate().forEach(::print)
    }
}