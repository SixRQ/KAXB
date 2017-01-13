/*
 *    Copyright 2017 SixRQ Ltd.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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