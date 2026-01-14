# Java Scientific Calculator

A powerful Scientific Calculator built using Java Swing. Comes with advanced math calculations, **Desmos-style graphs** with support for implicit equations, complex numbers, and programmer tools such as base conversion.

---

## 🌐 Live Demo

**[Try it out in your browser →](https://scientific-calculator-one-ebon.vercel.app/)**

No need to install anything. Runs on CheerpJ.

---

## Features

### Full Math Engine
*   **Operations**: `+`, `-`, `×`, `/`, `xʸ`, `√`, `π`, `e`.
*   **Trigonometry**: `sin`, `cos`, `tan` with Degree/Radian switch.
*   **Hyperbolic Functions**: `sinh`, `cosh`, `tanh`, `asinh`, `acosh`, `atanh`.
*   **Logarithms**: Natural Logarithm (`ln`), Base-10 Logarithm (`log`), Base-2 (`log₂`), Custom Base (`logb`).
*   **Combinatorics**: Factorial (`n!`), Permutations (`nPr`), Combinations (`nCr`).
*   **Utilities**: Modulo (`%`), Absolute Value (`abs`), `ceil`, `floor`.

### Graphing Calculator (Desmos-Style)
*   **Explicit Functions**: Graph `y = sin(x)`, `y = x^2`, and so on.
*   **Implicit Equations**: Plot circles (`x^2 + y^2 = 25`), hyperbolas, and complex equations such as `y = cos(x + y)`.
*   **Dynamic Grid**: Automatic scaling of grid lines that change with zoom level (0.1, 1, 10...).
*   **Navigation**:
    *   Pan: Drag the canvas.
    *   Zoom: Mouse wheel or `+`/`-` buttons.
    *   Axis sliders for fine control.
*   **Smart Parser**: Implicit multiplication (`2x`, `xy`), all mathematical functions.

### Developer Tools
*   **Base Converter**: Decimal ↔ Hexadecimal ↔ Binary ↔ Octal.
*   **Complex Numbers**: Add, subtract, multiply, divide complex numbers.
*   **Calculation History**: View all previous calculations.

### Modern UI/UX
*   **Dark & Light Modes**: Elegant designs with no visual artifacts.
*   **Keyboard Support**:
    *   `Escape`: Clear All (AC)
    *   `Backspace`/`Delete`: Delete last character
    *   `Cmd+A` / `Ctrl+A`: Select all
    *   `Shift + 8`: Multiply (`×`)
    *   `Shift + 6`: Power (`^`)
    *   `Shift + 1`: Factorial (`!`)
    *   `Shift + 5`: Modulo (`%`)

---

## Installation & Run

Requires **Java 8+**.

### Clone & Run
```bash
git clone https://github.com/Arman-Fathabadi/Scientific-Calculator.git
cd Scientific-Calculator
javac ScientificCalculator.java
java ScientificCalculator
```

### Run as JAR
```bash
java -jar ScientificCalculator.jar
```

---

## Usage Examples

| Action | Steps | Result |
|--------|-------|--------|
| Permutations | `5` → `nPr` → `2` → `=` | `20` |
| Circle Graph | Graph → `x^2 + y^2 = 25` → Plot | Circle |
| Hex Conversion | Convert → Decimal → Hex → `255` | `FF` |
| Custom Log | `8` → `logb` → `2` → `=` | `3` |

---

## Contributing

Pull requests are welcome! Issues are open for reporting bugs and/or suggesting new features.

---

## License

MIT License
