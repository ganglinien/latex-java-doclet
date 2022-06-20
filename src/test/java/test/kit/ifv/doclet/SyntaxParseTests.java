package test.kit.ifv.doclet;

import edu.kit.ifv.doclet.JavadocFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SyntaxParseTests {

    @BeforeEach
    void beforeEach() {
        System.out.println("=== NEW TEST ===");
    }

    @Test
    void test_basicJavadoc() {
        String formatted = JavadocFormat.applySyntaxHighlighting("""
                This is a test.
                This is the second line
                
                @param parameter and now the description!
                @returns nothing
                """);

        System.out.println(formatted);
    }

    @Test
    void test_allLinkTypes() {
        String formatted = JavadocFormat.applySyntaxHighlighting("""
                This is a long sentence with a link to {@link edu.kit.ifv.SomeClass} and another one to
                {@link SomeClass#withSomeMethod()}.
                
                Naturally, {@link #SameClassMethods()} should work too...
                """);

        System.out.println(formatted);
    }

}
