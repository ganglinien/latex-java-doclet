package edu.kit.ifv.doclet;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.lang.model.element.Element;

public abstract class AbstractExporter<T extends Element> {
    
    private final BufferedLatexWriter bufferedWriter;

    public AbstractExporter(BufferedLatexWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    protected BufferedLatexWriter getBufferedWriter() {
        return this.bufferedWriter;
    }

    public abstract void export(T element) throws IOException;

}
