# AGENTS.md

Instructions for AI coding agents working in `venus-sample-pdf-fop-accessibility`.

## Project Overview

This repository is a Java / Maven sample project for
[Venus Fugerit Doc](https://github.com/fugerit-org/fj-doc) demonstrating PDF/A and
PDF/UA-1 accessibility across three PDF rendering modules:

- `fj-doc-mod-fop` (Apache FOP)
- `fj-doc-mod-openpdf-ext` (OpenPDF)
- `fj-doc-mod-pdfbox` (Apache PDFBox)

## Build and Verification

Run the full build, FreeMarker template verification, and unit tests with:

```bash
mvn clean verify
```

### Environment Constraints

- **Java compatibility**: `pom.xml` compiles with `maven.compiler.release` set to `8`.
  Do not use Java 9+ APIs (such as `InputStream.readAllBytes()` or `List.of()`).
- **JDK 25 caveat**: Apache FOP may fail on JDK 25 (`JDK-8368356`); prefer JDK 8, 17, or 21.
- **Template verification**: `fj-doc-maven-plugin:verify` validates `.ftl` templates during
  the `compile` phase and fails the build on syntax errors.

## Key Files

- [pom.xml](pom.xml): Dependency management via `fj-doc` BOM (`fj-doc-version`).
- [DocHelper.java](src/main/java/org/fugerit/java/demo/venussamplepdffopaccessibility/DocHelper.java):
  Loads `fm-doc-process-config.xml` and applies programmatic properties for handlers that
  do not parse XML `<docHandlerCustomConfig>` attributes (such as `pdf-pdfbox-pdf-ua` in
  `fj-doc-mod-pdfbox` 8.18.8).
- [fm-doc-process-config.xml](src/main/resources/venus-sample-pdf-fop-accessibility/fm-doc-process-config.xml):
  Registers `docHandler` entries (`pdf-fop-*`, `pdf-openpdf-*`, `pdf-pdfbox-*`).
- [document.ftl](src/main/resources/venus-sample-pdf-fop-accessibility/template/document.ftl):
  Main Venus Doc FreeMarker XML template.
- [DocHelperTest.java](src/test/java/test/org/fugerit/java/demo/venussamplepdffopaccessibility/DocHelperTest.java):
  Generates sample PDFs into `target/` and runs PDFBox PDF/UA-1 inspections.

## Coding and Accessibility Conventions

- **Font embedding for PDF/UA-1**: Ensure all fonts used in PDF/UA handlers are embedded
  (for example, mapping fallback `helvetica` to an embedded TTF in `pdf-openpdf-pdf-ua`).
- **Generated outputs**: Never commit files in `target/`.
- **Markdown quality**: Keep Markdown clean for Sonar/Codacy linters (consistent ATX
  headings, language-tagged fenced code blocks, no trailing whitespace, no bare URLs,
  single trailing newline).

## Git, Changelog, and AI Transparency

Follow [CONTRIBUTING.md](CONTRIBUTING.md) for all changes:

- **Conventional Commits**: Use `<type>(<optional-scope>): <description>` (for example,
  `feat:`, `fix:`, `docs:`, `chore:`).
- **Changelog**: Record notable changes under `## [Unreleased]` in
  [CHANGELOG.md](CHANGELOG.md) following Keep a Changelog format.
- **AI Transparency Rule**: Include a plain informational `Note:` line in the commit
  message body or PR description:

  ```text
  Note: Generated with AI following Fugerit contribution guidelines.
  ```

- **No `Co-authored-by` tags**: Never add `Co-authored-by:` trailers for AI tools or models.
