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

import spock.lang.Specification

/**
 * Created by simon on 13/01/17.
 */
class SchemaGeneratorTest extends Specification {
    def "Generate fails with no arguments"() {
        given : "A valid SchemaGenerator"
        def schemaGenerator = new SchemaGenerator()

        when : "generate is called with no arguments"
        def result = schemaGenerator.generate([] as String[])

        then : "the return code is not zero"
        result == 999
    }

    def "Generate fails with invalid arguments"() {
        given : "A valid SchemaGenerator"
        def schemaGenerator = new SchemaGenerator()

        when : "generate is called with no arguments"
        def result = schemaGenerator.generate(["--P", "package", "--T", "/tmp", "--X", "dummy"] as String[])

        then : "the return code is not zero"
        result == 999
    }

    def "Generate fails with too few arguments"() {
        given : "A valid SchemaGenerator"
        def schemaGenerator = new SchemaGenerator()

        when : "generate is called with no arguments"
        def result = schemaGenerator.generate(["--P", "package", "--T", "/tmp", "--S"] as String[])

        then : "the return code is not zero"
        result == 999
    }

    def "Generate succeeds with valid arguments"() {
        given : "A valid SchemaGenerator"
        def schemaGenerator = new SchemaGenerator()

        when : "generate is called with no arguments"
        def targetDirectory = "${System.getProperty("java.io.tmpdir")}/${UUID.randomUUID()}"
        def result = schemaGenerator.generate(["--P", "com.example", "--T", targetDirectory, "--S", "StandAloneComplexType.xsd"] as String[])

        then : "the return code is not zero"
        result == 0
        (new File(targetDirectory)).deleteDir()
    }
}
