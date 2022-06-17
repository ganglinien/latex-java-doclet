package edu.kit.ifv.doclet.types;

import edu.kit.ifv.doclet.BufferedLatexWriter;

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
    public void export(TypeElement element) throws IOException {
        super.export(element);
    }
}
