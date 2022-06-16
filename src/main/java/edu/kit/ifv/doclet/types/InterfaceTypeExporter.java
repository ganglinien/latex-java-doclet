package edu.kit.ifv.doclet.types;

import java.io.BufferedWriter;

import javax.lang.model.element.TypeElement;

import edu.kit.ifv.doclet.AbstractExporter;
import edu.kit.ifv.doclet.BufferedLatexWriter;

public class InterfaceTypeExporter extends ClassTypeExporter {

    public InterfaceTypeExporter(BufferedLatexWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    protected String typeName() {
        return "interface";
    }

}
