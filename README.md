# venus-sample-pdf-fop-accessibility

This is a sample project configured using [fj-doc-maven-plugin init plugin](https://venusdocs.fugerit.org/guide/#maven-plugin-goal-init).

## Requirement

* JDK 8+ (*)
* Maven 3.8+

(*) Currently FOP not working on [JDK 25, See bug JDK-8368356](https://bugs.openjdk.org/browse/JDK-8368356).

## Project initialization

This project was created with [Venus Maven plugin](https://venusdocs.fugerit.org/guide/#maven-plugin-goal-init)

```shell
mvn org.fugerit.java:fj-doc-maven-plugin:8.16.7:init \
-DgroupId=org.fugerit.java.demo \
-DartifactId=venus-sample-pdf-fop-accessibility \
-Dextensions=base,freemarker,mod-fop \
-Dflavour=vanilla
```