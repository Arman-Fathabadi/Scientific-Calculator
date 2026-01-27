# Java Scientific Calculator

A scientific calculator with a lot of power and created in Java swing. Supports high level math operations including desmos-style graphs including implicit equations, complex numbers, programmers tools including Base converters.

## 🌐 Live Demo

**[Try it out in your browser →](https://scientific-calculator-one-ebon.vercel.app/)**

No need to install anything. Runs on CheerpJ.
> **New:** Dark/Light Mode Germany Contributor: Added Theme Sync and Settings Menu with Dark/Light Mode with a Theme Sync Toggle to match the theme to your liking!

## Features

### Full Math Engine
*   **Operations**: `+`, `-`, `x`, `/`, `xʸ`, `√`, `π`, `e`.
*   **Trigonometry**: `sin`, `Cos`, `tan` with Degree/Radian Switch.
*   **Hyperbolic Functions**: `sinh`, `cosh`, `tanh`, `asinh`, `acosh`, `atanh`.
*   **Logarithms**: Base-10 Logarithm (`log`), Natural logarithm (`ln`), Base-2 (`log2`) and Custom Base (`logb`).
*   **Combinatorics Basic**: Factorial (`n!`), Permutations (`nPr`), Combinations (`nCr`).
*   **Utilities**: `ceil`, `floor`, Modulo (`%`) Absolute Value (`abs`).

### The SimWall Street (Desmos-Style) Graphing Calculator
*   **Explicit Functions**: Graph `y = sin(x)`, `y = x^2`, etc.
*   **Implicit Equations**: Plot circles (`x^2 + y^2 = 25`), hyperbolas and more complex equations (`y = cos(x + y)`).
*   **Dynamic Grid**: The grid lines are automatically adjusted in terms of greatness to zoom index (0.1, 1, 10...).
*   **Navigation**:
    *   Pan: Drag the canvas.
    *   Zoom: Mouse wheel or `+`/`-` buttons.
    *   High quality axis sliders.
*   **Smart Parser**: implicit multiplication (`2x`, `xy`), and all the functions of mathematics.

### Developer Tools
*   **Base Converter**: Decimal to Hexadecimal to Binary.
*   **Numbers of the complex type**: Add, subtract, multiply and divide complex numbers.
*   **History of calculation**: View all past calculations.

### Modern UI/UX
*   **Dark/Light Modes**: Gorgeous and graphics free designs.
*   **Web Integration**: Professional Web environments, featuring theme updated automatically.
*   **Keyboard Support**:
    *   `Escape`: Clear All (AC)
    *   `Backspace`/`delete`: This marks the previous character as deleted.
    *   `Cmd+A` / `Ctrl+A`: Select all
    *   `Shift + 8`: Multiply (`x`)
    *   `Shift + 6`: Power (`^`)
    *   `Shift + 1`: Factorial (`!`)
    *   `Shift + 5`: Modulo (`%`)

## Installation & Run

Requires **Java 8+**.

### Clone & Run

Git clone is the tool used to create a clone of this case.
```bash
git clone https://github.com/Arman-Fathabadi/Scientific-Calculator.git
cd Scientific-Calculator
javac ScientificCalculator.java
java ScientificCalculator
```
