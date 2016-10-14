package com.sixrq.kaxb.generators

class DisplayGenerator(filename: String, packageName: String) : Generator(filename, packageName){

    fun readAndDisplayDocument() {
        parser.generate().forEach(::print)
    }
}