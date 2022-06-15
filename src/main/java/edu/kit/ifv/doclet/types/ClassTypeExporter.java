package edu.kit.ifv.doclet.types;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.lang.model.element.TypeElement;

import edu.kit.ifv.doclet.AbstractExporter;
import edu.kit.ifv.doclet.DocletUtils;

public class ClassTypeExporter extends AbstractExporter<TypeElement> {

    public ClassTypeExporter(BufferedWriter bufferedWriter) {
        super(bufferedWriter);
    }

    @Override
    public void export(TypeElement element) throws IOException {
            getBufferedWriter().append(String.format("""
            \\subsubsection*{Class %s}
            %s
            
            """,
            element.getQualifiedName(),
            DocletUtils.comment(element) != null
                    ? DocletUtils.comment(element) 
                    : "\\textit{No description}"
        ));
    }

}
