#!/usr/bin/env bash

# execute from script directory
SCRIPT_DIR="$( cd -- "$( dirname -- "${BASH_SOURCE[0]:-$0}"; )" &> /dev/null && pwd 2> /dev/null; )";
cd $SCRIPT_DIR

mvn clean package

# working with glx directory (only works in very specific place)

cp target/doclet.jar ../glx/docs/doclet.jar
bash ../glx/docs/doclet.sh
cp ../glx/docs/target/doc.tex ./target/external.tex
