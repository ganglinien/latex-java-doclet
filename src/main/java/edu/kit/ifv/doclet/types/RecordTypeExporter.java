package edu.kit.ifv.doclet.types;

import java.io.BufferedWriter;

import javax.lang.model.element.TypeElement;

import edu.kit.ifv.doclet.AbstractExporter;
import edu.kit.ifv.doclet.BufferedLatexWriter;

public class RecordTypeExporter extends AbstractExporter<TypeElement> {

    public RecordTypeExporter(BufferedLatexWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    public void export(TypeElement element) {
        System.err.println("# Records are not implemented yet");
    }

}
