package edu.kit.ifv.doclet;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public class PackageExporter extends AbstractExporter<PackageElement> {

    public PackageExporter(BufferedWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    public void export(PackageElement element) throws IOException {
        getBufferedWriter().append(String.format(
                """
                \\subsection{%s}
                %s

                """,
                element.getQualifiedName(),
                DocletUtils.comment(element) != null
                    ? DocletUtils.comment(element) 
                    : "\\textit{No description}"
            ));

        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement instanceof TypeElement typeElement) {
                new TypeExporter(getBufferedWriter()).export(typeElement);
            } else {
                new IllegalStateException("Package '" + element.getQualifiedName()
                        + "' has unhandled element of kind: " + enclosedElement.getKind()).printStackTrace();
            }

        }
    }
    
}
