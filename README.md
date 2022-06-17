# Javadoc to LaTeX exporter

Small tool to export Java 17 code to LaTeX using the **new** Doclet API.

## Test Usage

Run `mvn test` (*nix only, see doclet.sh) and this project will export
its own javadoc to LaTeX.

## Behaviour

### Note: This doclet exports everything in German lmao

This doclet will export
- `package-info.java`
- `class` javadoc
  - fields
  - constructors and methods
- `interface` javadoc
  - methods
  - **no** fields or constructors
- `enum` javadoc
  - enum types
  - fields
  - **no** constructors
  - methods
- *(TODO!!)* record javadoc

All fields, methods, etc. have to be anything else than private to be exported.
