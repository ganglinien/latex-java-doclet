package edu.kit.ifv.doclet.types;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import edu.kit.ifv.doclet.AbstractExporter;
import edu.kit.ifv.doclet.BufferedLatexWriter;
import edu.kit.ifv.doclet.JavadocFormat;

public class EnumTypeExporter extends AbstractExporter<TypeElement> {

    public EnumTypeExporter(BufferedLatexWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    public void export(TypeElement element) throws IOException {
        getBufferedWriter()
                .append(JavadocFormat.styleClassHeading("enum", element.getQualifiedName()))
                .append(System.lineSeparator())
                .append(JavadocFormat.comment(element))
                .append(System.lineSeparator());

        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement instanceof VariableElement variableElement
                    && variableElement.getKind() == ElementKind.ENUM_CONSTANT) {
                getBufferedWriter().append(String.format("""
                        \\paragraph*{%s}
                        %s
                        """,
                        variableElement.getSimpleName(),
                        JavadocFormat.comment(variableElement))
                );
            }
        }
    }

}
