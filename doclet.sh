#!/usr/bin/env bash

# execute from script directory
SCRIPT_DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]:-$0}"; )" &> /dev/null && pwd 2> /dev/null; )";
cd $SCRIPT_DIR

cd src/main/java
javadoc \
    -docletpath ../../../target/classes \
    -doclet edu.kit.ifv.LatexDoclet \
    -out ../../../target/doc.tex \
    -subpackages edu.kit.ifv
