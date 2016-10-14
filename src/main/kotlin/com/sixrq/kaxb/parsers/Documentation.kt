package com.sixrq.kaxb.parsers

import org.w3c.dom.Node

class Documentation(xmlns: String) : Tag(xmlns) {
    override fun processText(item: Node) {
        value = item.nodeValue
    }

    override fun toString(): String{
        return "/**\n* ${value.replace("\n", "\n*")}\n*/"
    }
}