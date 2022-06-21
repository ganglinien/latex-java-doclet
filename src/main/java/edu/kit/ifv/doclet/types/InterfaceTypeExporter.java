package edu.kit.ifv.doclet.types;

import edu.kit.ifv.doclet.BufferedLatexWriter;
import edu.kit.ifv.doclet.JavadocFormat;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

public class InterfaceTypeExporter extends ClassTypeExporter {

    public InterfaceTypeExporter(BufferedLatexWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    protected void exportAbstractionNote(TypeElement element) {
        // null object
    }

    @Override
    protected void exportMethods(TypeElement element) {
        // null object
    }

    @Override
    protected String typeName() {
        return "interface";
    }

    @Override
    protected boolean shouldBeExported(Element element) {
        String comment = JavadocFormat.comment(element);
        return comment != null && !comment.isBlank();
    }

    @Override
    public void export(TypeElement element) throws IOException {
        super.export(element);
    }
}
