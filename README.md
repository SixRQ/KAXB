## Synopsis

This project is used to generate native Kotlin classes from an xsd schema, similar to the JAXB tool for Java. The project will include a plugin for gradle and Intellij IDEA.

## Build Status

[![TeamCity](https://teamcity.jetbrains.com/app/rest/builds/buildType:(id:OpenSourceProjects_Kaxb_Build)/statusIcon)](https://teamcity.jetbrains.com/project.html?projectId=OpenSourceProjects_Kaxb)

## Motivation

I needed a tool that would generate native Kotlin classes rather than Java classes and then convert to Kotlin.

## Installation

Download the latest [zip](https://teamcity.jetbrains.com/app/rest/builds/buildType:(id:OpenSourceProjects_Kaxb_Build)/artifacts/content/*.zip)/[tar](https://teamcity.jetbrains.com/app/rest/builds/buildType:(id:OpenSourceProjects_Kaxb_Build)/artifacts/content/*.jar) from TeamCity and extract the contents

## Running

Once the archive has been extracted run the **bin/kaxb --P \<destination package\> --S \<schema file\> --T \<target directory\>**

## Tests

## Contributors

Simon Wiehe

## License

Apache License 2.0

