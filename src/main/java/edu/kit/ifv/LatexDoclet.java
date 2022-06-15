package edu.kit.ifv;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;

import edu.kit.ifv.doclet.PackageExporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * LaTeX Doclet Exporter
 * 
 * @author Christian Schliz <code@foxat.de>
 */
public class LatexDoclet implements Doclet {

    private String outputPath;
    private BufferedWriter bufferedWriter;

    public static DocletEnvironment environment;

    @Override
    public void init(Locale locale, Reporter reporter) {
    }

    @Override
    public String getName() {
        return "LaTeX Doclet";
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return Set.of(new Option() {
            @Override
            public int getArgumentCount() {
                return 1;
            }

            @Override
            public String getDescription() {
                return "path to the output file";
            }

            @Override
            public Kind getKind() {
                return Kind.STANDARD;
            }

            @Override
            public List<String> getNames() {
                return List.of("-out");
            }

            @Override
            public String getParameters() {
                return "<outputFile>";
            }

            @Override
            public boolean process(String name, List<String> arguments) {
                if (Objects.isNull(arguments) || arguments.size() == 0 || arguments.get(0).isBlank()) {
                    System.err.println("You need to specify the output path");
                    return false;
                }

                outputPath = arguments.get(0);
                return true;
            }
        });
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        LatexDoclet.environment = environment;
        
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(new File(outputPath), false));
        } catch (IOException exception) {
            System.err.println("Error: " + exception.getLocalizedMessage());
            return false;
        }

        environment.getSpecifiedElements().stream()
                .filter(element -> element.getKind() == ElementKind.PACKAGE)
                .map(element -> (PackageElement) element)
                .sorted(Comparator.comparing(p -> String.valueOf(p.getQualifiedName())))
                .forEachOrdered(this::consumePackage);

        try {
            this.bufferedWriter.flush();
        } catch (IOException exception) {
            System.err.println("Error: " + exception.getLocalizedMessage());
        } 
        return true;
    }

    private void consumePackage(PackageElement element) {
        try {
            new PackageExporter(bufferedWriter).export(element);
        } catch (IOException exception) {
            System.err.println("Error: " + exception.getLocalizedMessage());
        }
    }

}
