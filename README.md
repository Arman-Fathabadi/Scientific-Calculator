# Java Scientific Calculator

A powerful Scientific Calculator developed in Java Swing technology. This software provides basic scientific calculations, advanced mathematical operations, graphing capabilities, and developer tools such as base conversion.

![Scientific Calculator](https://via.placeholder.com/800x600?text=Scientific+Calculator+preview)

## Features

### Complete Math Engine
*   **Operations**: `+`, `-`, `x`, `/`, `xʸ`, `√`, `π`, `e`.
*   **Trigonometry**: `sin`, `cos`, `tan` with **Degree/Radian** switch.
*   **Hyperbolic functions**: `sinh`, `cosh`, `tanh`, and inverse functions (`asinh`, `acosh`).
*   **Logarithms**: Natural Logarithm (`ln`), Base-10 Logarithm (`log`), Base-2 Log.

The topics covered under this branch of mathematics are:
*   **Combinatorics**
*   **Arithmetical Utilities**: Modulo (`%`), Absolute Value (`abs`), `ceil`.

### Interactive Graphing
Visualize mathematical functions with a powerful interface similar to the one used in the popular graphing tools.
*   **Custom Plotting**: Input your own function (for example, `sin(x) x`, `2x^2`).
*   **Navigation**: Pan - drag, Zoom - mouse wheel.
*   **Precision Control**: Specific **X & Y Axis** sliders.
*   **Smart Parser**: Supports implicit multiplication (e.g., `2x`, `sin(x)`).

### Developer Tools
*   **Base Converter**: Convert easily between **Decimal**, **Hexadecimal**, **Binary**, and **Octal**.
*   **Clean History**: Meaningful history log of all your calculations.

### Contemporary UI/UX
*   **Dark & Light Modes**: Switch between dark and light themes that look sleek and sharp.
*   **Responsive Design**: A window that can be resized, adapting to various layouts.
*   **Keyboard Support**:
    *   `Escape`: Clear All (AC)
    *   `Backspace`/`Delete`: Delete last character
    *   `Shift + 8`: Multiply (`x`)
    *   `Shift + 6`: Power(`^`)
    *   `Shift + 1`: Factorial (`!`)

## Installation & Run

Make sure you are using the **Java Development Kit (JDK) version 8** or later.

### Clone the Repository
```bash
git clone https://github.com/yourusername/Scientific-Calculator.git
cd Scientific-Calculator
```

### Assemble
```bash
javac ScientificCalculator.java
java ScientificCalculator
```

## Usage Examples
*   **Permutations**: Type `5`, click `nPr`, type `2`, click `=` -> Result: `20`.
*   **Graphing**: Click **Graph**, type `sin(x)`, and scroll to zoom in and out.
*   **Hexadecimal Conversion**: Click the **Convert** button and select the operation `Decimal -> Hex`. Type the number `255` -> Result: `FF`.
*   **Custom Log**: Type `8`, click `logb`, type `2`, click `=` -> Result: `3`.

## Contributing
You’re also welcome to contribute to this project. Simply make a Pull Request.
