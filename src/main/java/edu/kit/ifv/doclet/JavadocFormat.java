package edu.kit.ifv.doclet;

import javax.lang.model.element.Element;

import edu.kit.ifv.LatexDoclet;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Provides formatting utilities for javadoc comments.
 * All javadoc comments are provided as a string and
 * need some formatting before they can be exported to LaTeX.
 *
 * This formatter defines a style how keywords or patterns
 * should look after compiling the LaTeX source.
 *
 * <teximage src="img/architecture.png" size=".5\linewidth" caption="Wiederholung: Architektur">
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
            \\definecolor{doclet-javadoc-highlight}{RGB}{104, 222, 95}
            
            \\providecommand{\\keyword}{}
            \\renewcommand{\\keyword}[1]{\\texttt{\\protect\\textcolor{doclet-javadoc-annotation}{#1}}}
            
            \\providecommand{\\highlightReference}{}
            \\renewcommand{\\highlightReference}[1]{\\texttt{\\protect\\textcolor{doclet-javadoc-highlight}{#1}}}
            
            % TODO (maybe!) display HTML correctly. First argument is the html tag, second argument is the content
            \\providecommand{\\htmlEscape}{}
            \\renewcommand{\\htmlEscape}[2]{#2}
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

        // first parse html
        input = Pattern.compile("<([^<>]*)(.*)>(.*)</\\1>", Pattern.DOTALL)
                .matcher(input)
                .replaceAll("\\\\htmlEscape{$1}{$3}");

        input = Pattern.compile("<teximage[\\s\n]*src=\"(.*)\"[\\s\n]*size=\"(.*)\"[\\s\n]*caption=\"(.*)\"[\\s\n]*/?>", Pattern.DOTALL)
                .matcher(input)
                .replaceAll("""
                        \\\\begin{figure}
                            \\\\centering
                            \\\\includegraphics[width=$2]{$1}
                            \\\\caption{$3}
                        \\\\end{figure}
                        """);

        return input
                .replaceAll(
                        "\\{@link #(.*)}",
                        "\\\\highlightReference{$1}"
                )
                .replaceAll(
                        "<([^<>]*)(.*)>(.*)</\\1>",
                        "\\\\htmlEscape{$1}{$3}"
                )
                .replaceAll(
                        "(<br>|<br/>)",
                        "\n\n"
                )
                // style all "@keyword" groups bold and pink
                // additionally, insert newline so that a single javadoc newline after some @modifier
                // translates to double newlines (line break) in LaTeX.
                .replaceAll(
                        "(@[a-zA-Z]*)(.*)",
                        "\\\\keyword{$1}\s$2\n"
                );
    }

    public static String stylePackageHeading(CharSequence qualifiedName) {
        return String.format("""
                \\subsection{\\keyword{package} %s}
                """, qualifiedName);
    }

    public static String styleClassHeading(CharSequence type, CharSequence qualifiedName) {
        return String.format("""
                \\subsubsection[\\keyword{%s} %s]{\\keyword{%s} %s}
                """,
                type,
                String.valueOf(qualifiedName)
                        .substring(String.valueOf(qualifiedName).lastIndexOf('.') + 1),
                type,
                transformClassName(qualifiedName)
        );
    }
    
    public static String transformClassName(CharSequence qualifier) {
        if (qualifier.length() < 30) {
            return String.valueOf(qualifier).replaceAll("\\.", "\\\\-.");
        }

        String[] components = String.valueOf(qualifier).split("\\.");
        for (int i = 0; i < components.length - 1; i++) {
            if (components[i].length() > 3) {
                components[i] = components[i].substring(0, 1);
            }
        }

        return String.join("\\-.", components);
    }

    public static String trimTypeQualifier(CharSequence qualifier) {
        return String.valueOf(qualifier).replaceAll(
                "(([a-zA-Z\\d]*)\\.)*([a-zA-Z\\d])",
                "$3"
        );
    }
}
