package edu.kit.ifv.doclet.types;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.*;

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
                    .append("\\textit{This class is \\guillemotleft{}abstract\\guillemotright}")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }
    }

    protected void exportFields(TypeElement element) throws IOException {
        List<VariableElement> fieldElements = element.getEnclosedElements().stream()
                .filter(enclosedElement -> enclosedElement.getKind() == ElementKind.FIELD)
                .map(enclosedElement -> (VariableElement) enclosedElement)
                // filter private elements
                // .filter(variableElement -> !variableElement.getModifiers().contains(Modifier.PRIVATE))
                .toList();

        getBufferedWriter().append("""
                \\paragraph*{Fields}
                \\begin{itemize}
                """);

        if (fieldElements.isEmpty()) {
            getBufferedWriter().append("""
                        \\item[] \\textit{Type has no fields}
                    """);
        }

        for (VariableElement fieldElement : fieldElements) {
            getBufferedWriter().append(String.format(
                    """
                        \\item \\texttt{\\keyword{%s} %s \\textbf{%s}}
                        
                        %s
                    """,
                    parseVisibility(fieldElement),
                    fieldElement.asType().toString(),
                    fieldElement.getSimpleName(),
                    JavadocFormat.comment(fieldElement) != null ? JavadocFormat.comment(element) : ""
            ));
        }

        getBufferedWriter().append("""
                \\end{itemize}
                """);
    }

    protected String parseVisibility(VariableElement element) {
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

    @Override
    public void export(TypeElement element) throws IOException {
        getBufferedWriter()
                .append(JavadocFormat.styleClassHeading(typeName(), element.getQualifiedName()))
                .append(System.lineSeparator());
        exportAbstractionNote(element);
        getBufferedWriter()
                .append(JavadocFormat.comment(element) != null
                        ? JavadocFormat.comment(element) : "\\textit{No description}")
                .append(System.lineSeparator());
        exportFields(element);
        getBufferedWriter()
                .append(System.lineSeparator())
                .append(System.lineSeparator());
    }

}
