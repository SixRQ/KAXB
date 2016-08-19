package com.sixrq.kaxb

import org.w3c.dom.Node

class Extension : Tag() {
    override fun processAttributes(item: Node?) {
        type = item!!.attributes.getNamedItem("base").nodeValue
    }
}