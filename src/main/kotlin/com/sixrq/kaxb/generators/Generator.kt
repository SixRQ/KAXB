package com.sixrq.kaxb.generators

import com.sixrq.kaxb.parsers.XmlParser

open class Generator(filename: String, packageName: String){

    protected val parser = XmlParser(filename, packageName)

}