package edu.kit.ifv.doclet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class BufferedLatexWriter extends BufferedWriter {

    public BufferedLatexWriter(Writer writer) throws IOException {
        super(writer);
    }

    @Override
    public BufferedLatexWriter append(CharSequence csq) throws IOException {
        super.write(replaceWithLaTeX(String.valueOf(csq)));
        return this;
    }

    private String replaceWithLaTeX(String input) {
        return input
                // escape underscores because LaTeX assumes math mode otherwise
                .replaceAll(
                        "_",
                        "\\\\_"
                );
    }
}
