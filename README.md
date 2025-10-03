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

## PDF/A Type Handler

To configure a PDF/A handler we need to embed fonts (in our case [TitilliumWeb](https://fonts.google.com/specimen/Titillium+Web/about))
and create the proper [fop-config-pdf-a.xml](src/main/resources/venus-sample-pdf-fop-accessibility/fop-config-pdf-a.xml) configuration. 

Then we add the handler configuration (the [pdf-a-mode](https://venusdocs.fugerit.org/guide/#doc-handler-mod-fop-pdf-config-pdf-a-mode) attribute is needed) : 

```xml
<docHandler id="pdf-fop-pdf-a" info="pdf" type="org.fugerit.java.doc.mod.fop.PdfFopTypeHandler">
    <docHandlerCustomConfig charset="UTF-8" fop-suppress-events="1" pdf-a-mode="PDF/A-1b"
                            fop-config-mode="classloader" fop-config-classloader-path="venus-sample-pdf-fop-accessibility/fop-config-pdf-a.xml" />
</docHandler>
```

## PDF/A size increase

NOTE: PDF/A profile usually increases the size of the output file.
(Because of font embedding and other features needed).

Let's see for instance the output on our [Unit Test](src/test/java/test/org/fugerit/java/demo/venussamplepdffopaccessibility/DocHelperTest.java),
As you can see, size for a simple PDF file increased by a factor of x10.

```
[main] INFO test.org.fugerit.java.demo.venussamplepdffopaccessibility.DocHelperTest - size document_pdf-fop-plain.pdf:7132
[main] INFO test.org.fugerit.java.demo.venussamplepdffopaccessibility.DocHelperTest - size document_pdf-fop-config.pdf:9809
[main] INFO test.org.fugerit.java.demo.venussamplepdffopaccessibility.DocHelperTest - size document_pdf-fop-pdf-a.pdf:73802
```
