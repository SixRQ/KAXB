package com.sixrq.kaxb.generators

import com.sixrq.kaxb.parsers.Element

class DisplayGenerator(filename: String, packageName: String) : Generator(filename, packageName){

    fun readAndDisplayDocument() {
        parser.generate().filter { it.value !is Element }.forEach(::print)
    }
}