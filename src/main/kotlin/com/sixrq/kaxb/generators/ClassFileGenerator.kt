package com.sixrq.kaxb.generators

import com.sixrq.kaxb.parsers.Element
import java.io.File

class ClassFileGenerator(filename: String, val packageName: String, val targetDirectory: String) : Generator(filename, packageName) {

    fun generateClasses() {
        val outputDirectory = File("${targetDirectory.replace('\\','/')}/${packageName.replace('.', '/')}".replace("//", "/"))
        val objectFactoryGenerator = ObjectFactoryGenerator(packageName)
        outputDirectory.mkdirs()
        parser.generate().forEach {
            val targetFile = "$outputDirectory/${it.component1()}.kt"
            if (it.component2() is Element) {
                objectFactoryGenerator.addQName(it.component2())
            } else {
                File(targetFile).printWriter().use { outputFile ->
                    outputFile.print(it.component2())
                }
                objectFactoryGenerator.addObject(it.component1())
            }
        }
        val objectFactoryFile = "$outputDirectory/ObjectFactory.kt"
        File(objectFactoryFile).printWriter().use { outputFile ->
            outputFile.print(objectFactoryGenerator.gerenateObjectFactory())
        }
    }
}