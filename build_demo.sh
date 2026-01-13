#!/bin/bash
echo "Compiling for Java 8 compatibility..."
javac --release 8 ScientificCalculator.java

echo "Creating Manifest..."
echo "Main-Class: ScientificCalculator" > manifest.txt

echo "Packaging JAR..."
jar cvfm ScientificCalculator.jar manifest.txt *.class

echo "Done! You can now open index.html (start a local server first, e.g., 'python3 -m http.server')."
rm manifest.txt
