package edu.kit.ifv.doclet;

import edu.kit.ifv.doclet.types.ClassTypeExporter;
import edu.kit.ifv.doclet.types.EnumTypeExporter;
import edu.kit.ifv.doclet.types.InterfaceTypeExporter;
import edu.kit.ifv.doclet.types.RecordTypeExporter;

import javax.lang.model.element.TypeElement;
import java.io.IOException;

public class TypeExporter extends AbstractExporter<TypeElement> {

    public TypeExporter(BufferedLatexWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    public void export(TypeElement element) throws IOException {
        switch (element.getKind()) {
            case CLASS -> new ClassTypeExporter(getBufferedWriter()).export(element);
            case INTERFACE -> new InterfaceTypeExporter(getBufferedWriter()).export(element);
            case ENUM -> new EnumTypeExporter(getBufferedWriter()).export(element);
            case RECORD -> new RecordTypeExporter(getBufferedWriter()).export(element);
            default -> System.err.println("# unhandled kind " + element.getKind());
        }
    }
    
}
