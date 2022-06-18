package edu.kit.ifv.doclet;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

public class PackageExporter extends AbstractExporter<PackageElement> {

    public PackageExporter(BufferedLatexWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    public void export(PackageElement element) throws IOException {
        getBufferedWriter()
                .append(JavadocFormat.stylePackageHeading(element.getQualifiedName()))
                .append(System.lineSeparator())
                .append(JavadocFormat.comment(element) != null
                        ? JavadocFormat.comment(element) : "\\textit{Keine Beschreibung}")
                .append(System.lineSeparator());

        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement instanceof TypeElement typeElement) {
                new TypeExporter(getBufferedWriter()).export(typeElement);
            } else {
                new IllegalStateException("Package '" + element.getQualifiedName()
                        + "' has unhandled element of kind: " + enclosedElement.getKind()).printStackTrace();
            }
        }

        // insert a page break after each package
        getBufferedWriter().append("""
                \\clearpage
                """);
    }
    
}
