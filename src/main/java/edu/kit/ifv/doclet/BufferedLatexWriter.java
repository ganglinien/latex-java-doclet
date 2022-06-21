package edu.kit.ifv.doclet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

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
        // remove html escape for latex macros
        input = Pattern.compile("<texonly[\\s\n]*?tex=\"(.*?)\"[\\s\n]*?/?>", Pattern.DOTALL)
                .matcher(input)
                .replaceAll("$1");

        // escape underscores because LaTeX assumes math mode otherwise
        input = input.replaceAll("_", "\\\\_");

        // escape hashtag because LaTeX doesn't allow it in vertical mode
        input = input.replaceAll("#", "\\\\#");

        return input;
    }
}
