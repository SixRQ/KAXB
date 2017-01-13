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

package com.sixrq.kaxb.main

import com.sixrq.kaxb.generators.ClassFileGenerator
import java.lang.System.exit

class SchemaGenerator {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val schemaGenerator = SchemaGenerator()
            exit(schemaGenerator.generate(args))
        }
    }

    fun generate(args: Array<String>): Int {
        val validArgs = listOf("--P", "--S", "--T")
        if (args.size != 6 ||
                !validArgs.containsAll(args.filter { it.startsWith("--") } ) ||
                !args.filter { it.startsWith("--") }.containsAll(validArgs)) {
            println("Usage: SchemaGenerator --P <destination package> --S <schema file> --T <target directory>")
            return 999
        }

        var packageName = ""
        var schemaLocation = ""
        var targetDirectory = ""

        args.forEachIndexed { index, value ->
            when {
                value.toUpperCase() == "--P" -> packageName = args[index + 1]
                value.toUpperCase() == "--S" -> schemaLocation = args[index + 1]
                value.toUpperCase() == "--T" -> targetDirectory = args[index + 1]
            }
        }

        val classFileGenerator = ClassFileGenerator(schemaLocation, packageName, targetDirectory)
        classFileGenerator.generateClasses()

        return 0
    }
}