package com.sixrq.kaxb

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DisplayColor_t", namespace = "http://www.garmin.com/xmlschemas/GpxExtensions/v3")
enum class DisplayColorT(val value : String ) {
    BLACK("Black"),
    DARKRED("DarkRed"),
    DARKGREEN("DarkGreen"),
    DARKYELLOW("DarkYellow"),
    DARKBLUE("DarkBlue"),
    DARKMAGENTA("DarkMagenta"),
    DARKCYAN("DarkCyan"),
    LIGHTGRAY("LightGray"),
    DARKGRAY("DarkGray"),
    RED("Red"),
    GREEN("Green"),
    YELLOW("Yellow"),
    BLUE("Blue"),
    MAGENTA("Magenta"),
    CYAN("Cyan"),
    WHITE("White"),
    TRANSPARENT("Transparent");

    companion object {
        fun from(value : String ): DisplayColorT = DisplayColorT.values().first { it.value == value }
    }
}
