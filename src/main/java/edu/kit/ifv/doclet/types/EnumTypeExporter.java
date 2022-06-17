package edu.kit.ifv.doclet.types;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.*;

import edu.kit.ifv.doclet.AbstractExporter;
import edu.kit.ifv.doclet.BufferedLatexWriter;
import edu.kit.ifv.doclet.JavadocFormat;

public class EnumTypeExporter extends ClassTypeExporter {

    public EnumTypeExporter(BufferedLatexWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    protected String typeName() {
        return "enum";
    }

    @Override
    protected void exportEnumConstants(TypeElement element) throws IOException {
        List<VariableElement> enumConstantElements = element.getEnclosedElements().stream()
                // filter either methods or constructors
                .filter(enclosedElement -> enclosedElement.getKind() == ElementKind.ENUM_CONSTANT)
                .map(variableElement -> (VariableElement) variableElement)
                .toList();

        getBufferedWriter().append("""
                \\paragraph*{Enum-Konstanten}
                \\begin{itemize}
                """);

        if (enumConstantElements.isEmpty()) {
            getBufferedWriter().append("""
                        \\item[] \\textit{Enum hat keine Konstanten}
                    """);
        }

        for (VariableElement enumConstantElement : enumConstantElements) {
            getBufferedWriter().append(String.format(
                    """
                        \\item \\texttt{\\textbf{%s}}
                                
                        %s
                    """,
                    enumConstantElement.getSimpleName(),
                    JavadocFormat.comment(enumConstantElement) != null ? JavadocFormat.comment(enumConstantElement) : ""
            ));
        }

        getBufferedWriter().append("""
                \\end{itemize}
                """);
    }

    @Override
    public void export(TypeElement element) throws IOException {
        super.export(element);
    }

}
