package edu.kit.ifv.doclet;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.lang.model.element.TypeElement;

import edu.kit.ifv.doclet.types.*;

public class TypeExporter extends AbstractExporter<TypeElement> {

    public TypeExporter(BufferedWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    public void export(TypeElement element) throws IOException {
        switch (element.getKind()) {
            case CLASS:
                new ClassTypeExporter(getBufferedWriter()).export(element);
                break;
            case INTERFACE:
                new InterfaceTypeExporter(getBufferedWriter()).export(element);
                break;
            case ENUM:
                new EnumTypeExporter(getBufferedWriter()).export(element);
                break;
            case RECORD:
                new RecordTypeExporter(getBufferedWriter()).export(element);
                break;
            default:
                System.err.println("# unhandled kind " + element.getKind());
        }
    }
    
}
