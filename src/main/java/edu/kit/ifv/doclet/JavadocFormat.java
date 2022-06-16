package edu.kit.ifv.doclet;

import javax.lang.model.element.Element;

import edu.kit.ifv.LatexDoclet;

import java.util.Objects;

/**
 * Provides formatting utilities for javadoc comments.
 * All javadoc comments are provided as a string and
 * need some formatting before they can be exported to LaTeX.
 *
 * This formatter defines a style how keywords or patterns
 * should look after compiling the LaTeX source.
 *
 * @author Christian Schliz
 */
public class JavadocFormat {

    /**
     * This string should be copied inside the LaTeX preamble.
     * Packages or definitions that are required by the syntax
     * highlighting are defined in this passage.
     */
    public static final String PREAMBLE = """
            % used for coloring certain keywords
            \\includepackage{xcolor}
            \\definecolor{doclet-javadoc-annotation}{RGB}{213, 95, 222}
            
            \\providecommand{\\keyword}{}
            \\renewcommand{\\keyword}[1]{\\texttt{\\protect\\textcolor{doclet-javadoc-annotation}{#1}}}
            """;

    /**
     * Retrieve the javadoc comment of a Java source element
     * and apply the syntax highlighting.
     *
     * @param element Any Java source element to which javadoc
     *                may be applied.
     * @return Javadoc comment with LaTeX macros for syntax highlighting
     */
    public static String comment(Element element) {
        return applySyntaxHighlighting(
                LatexDoclet.environment.getElementUtils().getDocComment(element)
        );
    }

    private static String applySyntaxHighlighting(String input) {
        if (Objects.isNull(input)) return null;
        return input
                // style all "@keyword" groups bold and pink
                // additionally, insert newline so that a single javadoc newline after some @modifier
                // translates to double newlines (line break) in LaTeX.
                .replaceAll(
                        "(@[a-zA-Z]*)(.*)",
                        "\\\\textbf{\\\\textcolor{doclet-javadoc-annotation}{$1}}\s$2\n"
                );
    }

    public static String stylePackageHeading(CharSequence qualifiedName) {
        return String.format("""
                \\subsection{\\texttt{\\protect\\textcolor{doclet-javadoc-annotation}{package}} %s}
                """, qualifiedName);
    }

    public static String styleClassHeading(CharSequence type, CharSequence qualifiedName) {
        return String.format("""
                \\subsubsection{\\texttt{\\protect\\textcolor{doclet-javadoc-annotation}{%s}} %s}
                """,
                type,
                qualifiedName
        );
    }
}
