package edu.kit.ifv.doclet;

import javax.lang.model.element.Element;

import edu.kit.ifv.LatexDoclet;

public class DocletUtils {
    public static String comment(Element element) {
        return LatexDoclet.environment.getElementUtils().getDocComment(element);
    }
}
