### Copyright (c) 2022 Christian Schliz (et al.)

# Javadoc to LaTeX exporter

## Hinweise für Anwender

1. Bitte dringend an "normales" Format halten. Zuerst kommt eine Beschreibung, danach eventuell `@`tags.
```java
/**
 * Beschreibung bla bla
 *
 * @param asd asd
 * @return pasdjoafhioarhgn
 */
```

2. Falls es geht auf die Verwendung von `@link` verzichten, stattdessen den Text verständlicher gestalten.
   Das Ganze hat folgenden Hintergrund: Ich bekomme es auf die Schnelle nicht hin hyperlinks zwischen den Klassen
   einzurichten (woher soll ich überhaupt wissen welche Klassen in diesem Dokument aufgelistet sind
   bzw. wie verlinke ich das wenn nur der Name da steht und die Klasse oben importiert ist...)

3. Bilder werden mit einem custom HTML Tag `<teximage ... />` eingebunden. Hierbei ist es zwingend nötig die
   Reihenfolge der Parameter einzuhalten. Weitere Leerzeichen oder Zeilenumbrüche zwischen den Zeilen funktionieren.
   (meistens jedenfalls) Beispiel:
```html
/**
 * <teximage src="./img/test.png" width="0.8\linewidth" caption="Das ist ein tolles Bild :)" />
 */
```

4. Generell könnt ihr jederzeit mit `<texonly ... />` LaTeX macros einbinden, ohne dass sie in der HTML Ansicht
   vom Javadoc angezeigt werden. Sieht zwar in der reinen Quellansicht komisch aus aber so müssen wir nachher nicht
   das generierte LaTeX bearbeiten. Beispiel:
```html
/**
 * <texonly tex="\label{my-label}" />
 */
```

## Entwicklungshinweise (en)

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
- **no** `record` javadoc

All fields, methods, etc. have to be anything else than private
or have a doc comment to be exported.
