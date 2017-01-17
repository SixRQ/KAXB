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

group = "com.sixrq"
version = "1.0-SNAPSHOT"

buildscript {
    repositories {
        maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap-1.1") }
        maven { setUrl("http://sixrq.geekgalaxy.com:7073/artifactory/plugins-release") }
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1-M04")
    }
}

apply {
    plugin("groovy")
    plugin("java")
    plugin("kotlin")
}

repositories {
    maven { setUrl("http://dl.bintray.com/kotlin/kotlin-eap-1.1") }
    maven { setUrl("http://sixrq.geekgalaxy.com:7073/artifactory/plugins-release") }
    mavenCentral()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1-M04")
    compile("org.codehaus.groovy:groovy-all:2.3.11")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.7.4")
    testCompile("org.spockframework:spock-core:1.0-groovy-2.4")
    testCompile("junit:junit:4.11")
}

