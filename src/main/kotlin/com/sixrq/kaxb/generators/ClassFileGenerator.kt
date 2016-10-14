package com.sixrq.kaxb.generators

import java.io.File

class ClassFileGenerator(filename: String, val packageName: String, val targetDirectory: String) : Generator(filename, packageName) {

    fun generateClasses() {
        val outputDirectory = File("${targetDirectory.replace('\\','/')}/${packageName.replace('.', '/')}".replace("//", "/"))
        outputDirectory.mkdirs()
        parser.generate().forEach {
            val targetFile = "$outputDirectory/${it.component1()}.kt"
            File(targetFile).printWriter().use { outputFile ->
                outputFile.print(it.component2())
            }
        }
    }
}