package edu.kit.ifv.doclet.types;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;

import com.sun.jdi.ClassType;
import com.sun.source.tree.ClassTree;
import edu.kit.ifv.doclet.AbstractExporter;
import edu.kit.ifv.doclet.BufferedLatexWriter;
import edu.kit.ifv.doclet.JavadocFormat;

public class ClassTypeExporter extends AbstractExporter<TypeElement> {

    public ClassTypeExporter(BufferedLatexWriter bufferedWriter) {
        super(bufferedWriter);
    }

    protected String typeName() {
        return "class";
    }

    protected void exportAbstractionNote(TypeElement element) throws IOException {
        if (element.getModifiers().contains(Modifier.ABSTRACT)) {
            getBufferedWriter()
                    .append("\\textit{Diese Klasse ist \\guillemotleft{}abstract\\guillemotright}")
                    .append(System.lineSeparator());
        }
    }

    protected void exportInheritance(TypeElement element) throws IOException {
        if (hasSignificantSuperclass(element) && element.getInterfaces().size() > 0) {
            getBufferedWriter()
                    .append(String.format(
                            "Diese Klasse erweitert \\texttt{%s}",
                            JavadocFormat.trimTypeQualifier(element.getSuperclass().toString())
                    ))
                    .append(" ")
                    .append(String.format(
                            "und implementiert \\texttt{%s}.",
                            element.getInterfaces().stream()
                                    .map(type -> JavadocFormat.trimTypeQualifier(type.toString()))
                                    .collect(Collectors.joining(", "))
                    ))
                    .append(System.lineSeparator());
        } else if (hasSignificantSuperclass(element)) {
            getBufferedWriter()
                    .append(String.format(
                            "Diese Klasse erweitert \\texttt{%s}.",
                            JavadocFormat.trimTypeQualifier(element.getSuperclass().toString())
                    ))
                    .append(System.lineSeparator());
        } else if (element.getInterfaces().size() > 0) {
            getBufferedWriter()
                    .append(String.format(
                            "Diese Klasse implementiert \\texttt{%s}.",
                            element.getInterfaces().stream()
                                    .map(type -> JavadocFormat.trimTypeQualifier(type.toString()))
                                    .collect(Collectors.joining(", "))
                    ))
                    .append(System.lineSeparator());
        }
    }

    protected void exportEnumConstants(TypeElement element) throws IOException {
        // template method
    }

    protected void exportFields(TypeElement element) throws IOException {
        List<VariableElement> fieldElements = element.getEnclosedElements().stream()
                .filter(enclosedElement -> enclosedElement.getKind() == ElementKind.FIELD)
                .map(enclosedElement -> (VariableElement) enclosedElement)
                // filter private elements
                .filter(this::shouldBeExported)
                .toList();

        if (fieldElements.isEmpty()) {
            // skip when no fields
            return;
        }

        getBufferedWriter().append("""
                \\paragraph*{Felder}
                \\begin{itemize}
                """);

        for (VariableElement fieldElement : fieldElements) {
            getBufferedWriter().append(String.format(
                    """
                        \\item \\texttt{\\keyword{%s} %s \\textbf{%s}}
                        
                        %s
                    """,
                    parseVisibility(fieldElement),
                    JavadocFormat.trimTypeQualifier(fieldElement.asType().toString()),
                    fieldElement.getSimpleName(),
                    JavadocFormat.comment(fieldElement) != null ? JavadocFormat.comment(fieldElement) : ""
            ));
        }

        getBufferedWriter().append("""
                \\end{itemize}
                """);
    }

    protected void exportMethods(TypeElement element) throws IOException {
        List<ExecutableElement> executableElements = element.getEnclosedElements().stream()
                // filter either methods or constructors
                .filter(enclosedElement -> enclosedElement.getKind() == ElementKind.METHOD
                        || enclosedElement.getKind() == ElementKind.CONSTRUCTOR)
                .map(executableElement -> (ExecutableElement) executableElement)
                // filter private elements
                .filter(executableElement -> !executableElement.getModifiers().contains(Modifier.PRIVATE))
                .toList();

        getBufferedWriter().append("""
                \\paragraph*{Methoden}
                \\begin{itemize}
                """);

        if (executableElements.isEmpty()) {
            getBufferedWriter().append("""
                        \\item[] \\textit{Typ hat keine Methoden}
                    """);
        }

        for (ExecutableElement executableElement : executableElements) {
            getBufferedWriter().append(String.format(
                    """
                        \\item \\texttt{\\keyword{%s} %s \\textbf{%s}(%s)}
                                
                        %s
                    """,
                    // method visibility as keyword
                    parseVisibility(executableElement),
                    // put method return type
                    JavadocFormat.trimTypeQualifier(executableElement.getReturnType().toString()),
                    // put simple method mane
                    executableElement.getSimpleName(),
                    // Join all parameters within the appropriate format
                    executableElement.getParameters().stream()
                            .map(parameter -> JavadocFormat.trimTypeQualifier(parameter.asType().toString())
                                    + " " + parameter.getSimpleName())
                            .collect(Collectors.joining(", ")),
                    // add javadoc comment
                    JavadocFormat.comment(executableElement) != null ? JavadocFormat.comment(executableElement) : ""
            ));
        }

        getBufferedWriter().append("""
                \\end{itemize}
                """);
    }

    protected boolean shouldBeExported(Element element) {
        return !element.getModifiers().contains(Modifier.PRIVATE) || JavadocFormat.comment(element) != null;
    }

    protected String parseVisibility(Element element) {
        if (element.getModifiers().contains(Modifier.PUBLIC)) {
            return "public";
        } else if (element.getModifiers().contains(Modifier.PROTECTED)) {
            return "protected";
        } else if (element.getModifiers().contains(Modifier.PRIVATE)) {
            return "private";
        } else {
            return "package-private";
        }
    }

    protected boolean hasSignificantSuperclass(TypeElement element) {
        if (element.getSuperclass() == null || element.getSuperclass().toString() == null) {
            return false;
        }

        String qualifier = JavadocFormat.trimTypeQualifier(element.getSuperclass().toString());

        return !qualifier.equalsIgnoreCase("none")
                && !qualifier.equalsIgnoreCase("Object");
    }

    @Deprecated
    protected String parseMostSignificantName(String fullQualifier) {
        int genericIndex = fullQualifier.indexOf('<');
        int mostSignificantTypeDelimiter;

        if (genericIndex >= 0) {
            mostSignificantTypeDelimiter = fullQualifier.substring(0, genericIndex).lastIndexOf('.');
        } else {
            mostSignificantTypeDelimiter = fullQualifier.lastIndexOf('.');
        }

        return fullQualifier.substring(mostSignificantTypeDelimiter + 1);
    }

    @Override
    public void export(TypeElement element) throws IOException {
        getBufferedWriter()
                .append(JavadocFormat.styleClassHeading(typeName(), element.getQualifiedName()))
                .append(System.lineSeparator());
        exportAbstractionNote(element);
        exportInheritance(element);
        getBufferedWriter()
                .append(System.lineSeparator())
                .append(JavadocFormat.comment(element) != null
                        ? JavadocFormat.comment(element) : "\\textit{Keine Beschreibung}")
                .append(System.lineSeparator());
        exportEnumConstants(element); // enum template method
        exportFields(element);
        exportMethods(element);
        getBufferedWriter()
                .append(System.lineSeparator())
                .append(System.lineSeparator());
    }

}
