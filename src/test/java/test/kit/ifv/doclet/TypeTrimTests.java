package test.kit.ifv.doclet;

import edu.kit.ifv.doclet.JavadocFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TypeTrimTests {

    @Test
    public void test() {
        String s = JavadocFormat.trimTypeQualifier("bla.bla.bla.Class<bla.Generic1, bla.Generic2>");
        assertEquals("Class<Generic1, Generic2>", s);
    }

}
