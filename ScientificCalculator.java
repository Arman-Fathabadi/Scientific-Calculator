import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import javax.swing.text.*;
import java.awt.geom.Path2D;

public class ScientificCalculator extends JFrame implements ActionListener, KeyListener {
    JFrame frame;
    JTextPane textArea;
    JScrollPane scrollPane;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[16];
    JButton addButton, subtractButton, multiplyButton, divideButton;
    JButton decimalButton, equalButton, deleteButton, clearButton;
    JButton sinButton, cosButton, tanButton, logButton, sqrtButton, powerButton, piButton, eButton;
    JButton themeToggleButton;
    JPanel panel, scientificPanel, headerPanel;
    JPanel advancedPanel;
    JButton sinhButton, coshButton, tanhButton, asinhButton, acoshButton, atanhButton;
    JButton lnButton, log2Button, logbButton;
    JButton factorialButton, permutationButton, combinationButton;
    JButton matrixButton;
    JButton complexButton;
    JButton conversionButton;
    JButton modButton, absButton, ceilButton, floorButton;
    JButton radDegToggleButton;
    JButton graphButton;
    JButton historyButton;
    JFrame historyFrame;
    JTextPane historyTextArea;
    JScrollPane historyScrollPane;
    JButton clearHistoryButton;
    Font fo = new Font("Arial", Font.BOLD, 13);
    Font smallFont = new Font("Arial", Font.BOLD, 11);

    Color lightBackground = new Color(245, 245, 247);
    Color lightForeground = new Color(20, 20, 20);
    Color lightButtonBackground = new Color(235, 235, 235);
    Color lightNumberButtonBackground = new Color(250, 250, 250);
    Color lightOperationButtonBackground = new Color(255, 165, 50);
    Color lightScientificButtonBackground = new Color(180, 220, 255);

    Color darkBackground = new Color(30, 30, 35);
    Color darkForeground = new Color(240, 240, 240);
    Color darkButtonBackground = new Color(55, 55, 60);
    Color darkNumberButtonBackground = new Color(65, 65, 75);
    Color darkOperationButtonBackground = new Color(255, 140, 50);
    Color darkScientificButtonBackground = new Color(50, 85, 130);

    boolean isDarkMode = false;
    boolean isDegreeMode = true;
    BigDecimal num1 = BigDecimal.ZERO;
    BigDecimal num2 = BigDecimal.ZERO;
    BigDecimal lastNum2 = BigDecimal.ZERO;
    BigDecimal result = BigDecimal.ZERO;
    char operation;
    boolean operationClicked = false;
    boolean equalsClicked = false;
    MathContext mc = new MathContext(15, RoundingMode.HALF_UP);

    SimpleAttributeSet userInputStyle = new SimpleAttributeSet();
    SimpleAttributeSet resultStyle = new SimpleAttributeSet();
    SimpleAttributeSet errorStyle = new SimpleAttributeSet();

    // We'll keep track of the "current expression" plus the "last expression" for
    // chained output
    String currentExpression = "";
    String lastExpression = "";
    String lastFullExpression = ""; // To store the full expression for history

    public ScientificCalculator() {
        frame = new JFrame("Scientific Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 900);
        frame.setLayout(null);
        frame.setResizable(true);

        headerPanel = new JPanel();
        headerPanel.setBounds(5, 5, 460, 40);
        headerPanel.setLayout(new GridLayout(1, 4, 5, 5));
        deleteButton = new JButton("DEL");
        clearButton = new JButton("AC");
        themeToggleButton = new JButton("Dark");
        historyButton = new JButton("History");
        headerPanel.add(deleteButton);
        headerPanel.add(clearButton);
        headerPanel.add(themeToggleButton);
        headerPanel.add(historyButton);
        frame.add(headerPanel);

        textArea = new JTextPane();
        textArea.setFocusable(false);
        textArea.setFont(fo);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(5, 50, 460, 120);
        scrollPane.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        frame.add(scrollPane);

        // Modified userInputStyle to green
        StyleConstants.setForeground(userInputStyle, new Color(0, 150, 70)); // Green color
        StyleConstants.setBold(userInputStyle, true);

        // Modified resultStyle to orange
        StyleConstants.setForeground(resultStyle, new Color(255, 140, 0)); // Orange color
        StyleConstants.setBold(resultStyle, true);

        StyleConstants.setForeground(errorStyle, Color.RED);

        addButton = new JButton("+");
        subtractButton = new JButton("−");
        multiplyButton = new JButton("×");
        divideButton = new JButton("÷");
        decimalButton = new JButton(".");
        equalButton = new JButton("=");

        sinButton = new JButton("sin");
        cosButton = new JButton("cos");
        tanButton = new JButton("tan");
        logButton = new JButton("log");
        sqrtButton = new JButton("√");
        powerButton = new JButton("xʸ");
        piButton = new JButton("π");
        eButton = new JButton("e");

        functionButtons[0] = addButton;
        functionButtons[1] = subtractButton;
        functionButtons[2] = multiplyButton;
        functionButtons[3] = divideButton;
        functionButtons[4] = decimalButton;
        functionButtons[5] = equalButton;
        functionButtons[6] = deleteButton;
        functionButtons[7] = clearButton;
        functionButtons[8] = sinButton;
        functionButtons[9] = cosButton;
        functionButtons[10] = tanButton;
        functionButtons[11] = logButton;
        functionButtons[12] = sqrtButton;
        functionButtons[13] = powerButton;
        functionButtons[14] = piButton;
        functionButtons[15] = eButton;

        for (int i = 0; i < 16; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(fo);
            functionButtons[i].setFocusable(false);
            functionButtons[i].setBorderPainted(true);
            functionButtons[i].setFocusPainted(false);
            addButtonHoverEffect(functionButtons[i]);
        }

        themeToggleButton.addActionListener(this);
        themeToggleButton.setFont(fo);
        themeToggleButton.setFocusable(false);
        themeToggleButton.setBorderPainted(true);
        themeToggleButton.setFocusPainted(false);
        addButtonHoverEffect(themeToggleButton);

        historyButton.addActionListener(this);
        historyButton.setFont(fo);
        historyButton.setFocusable(false);
        historyButton.setBorderPainted(true);
        historyButton.setFocusPainted(false);
        addButtonHoverEffect(historyButton);

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(fo);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBorderPainted(true);
            numberButtons[i].setFocusPainted(false);
            addButtonHoverEffect(numberButtons[i]);
        }

        panel = new JPanel();
        panel.setBounds(5, 180, 460, 220);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subtractButton);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(multiplyButton);
        panel.add(numberButtons[0]);
        panel.add(decimalButton);
        panel.add(equalButton);
        panel.add(divideButton);

        scientificPanel = new JPanel();
        scientificPanel.setBounds(5, 410, 460, 120);
        scientificPanel.setLayout(new GridLayout(2, 4, 10, 10));
        scientificPanel.add(sinButton);
        scientificPanel.add(cosButton);
        scientificPanel.add(tanButton);
        scientificPanel.add(logButton);
        scientificPanel.add(sqrtButton);
        scientificPanel.add(powerButton);
        scientificPanel.add(piButton);
        scientificPanel.add(eButton);

        advancedPanel = new JPanel();
        advancedPanel.setBounds(5, 540, 460, 300);
        advancedPanel.setLayout(new GridLayout(4, 5, 5, 5));
        sinhButton = new JButton("sinh");
        coshButton = new JButton("cosh");
        tanhButton = new JButton("tanh");
        asinhButton = new JButton("asinh");
        acoshButton = new JButton("acosh");
        atanhButton = new JButton("atanh");
        lnButton = new JButton("ln");
        log2Button = new JButton("log₂");
        logbButton = new JButton("logb");
        factorialButton = new JButton("n!");
        permutationButton = new JButton("nPr");
        combinationButton = new JButton("nCr");
        matrixButton = new JButton("Matrix");
        complexButton = new JButton("Complex");
        conversionButton = new JButton("Convert");
        modButton = new JButton("mod");
        absButton = new JButton("abs");
        ceilButton = new JButton("ceil");
        floorButton = new JButton("floor");
        radDegToggleButton = new JButton("Deg");
        graphButton = new JButton("Graph");

        JButton[] advButtons = {
                sinhButton, coshButton, tanhButton, asinhButton, acoshButton,
                atanhButton, lnButton, log2Button, logbButton, factorialButton,
                permutationButton, combinationButton, matrixButton, complexButton, conversionButton,
                modButton, absButton, ceilButton, floorButton, radDegToggleButton,
                graphButton
        };

        for (JButton btn : advButtons) {
            btn.addActionListener(this);
            btn.setFont(smallFont);
            btn.setFocusable(false);
            btn.setBorderPainted(true);
            btn.setFocusPainted(false);
            addButtonHoverEffect(btn);
            advancedPanel.add(btn);
        }

        historyFrame = new JFrame("History");
        historyFrame.setSize(400, 600);
        historyFrame.setLayout(new BorderLayout());
        historyTextArea = new JTextPane();
        historyTextArea.setFocusable(false);
        historyTextArea.setFont(fo);
        historyTextArea.setEditable(false);
        historyScrollPane = new JScrollPane(historyTextArea);
        clearHistoryButton = new JButton("Clear History");
        clearHistoryButton.addActionListener(this);
        historyFrame.add(historyScrollPane, BorderLayout.CENTER);
        historyFrame.add(clearHistoryButton, BorderLayout.SOUTH);

        // Add WindowFocusListener to historyFrame
        historyFrame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                frame.requestFocusInWindow(); // Request focus back to the main frame
            }
        });

        Border shadowBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 5, 5, new Color(160, 160, 160, 100)),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createRaisedBevelBorder(),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        headerPanel.setBorder(shadowBorder);
        panel.setBorder(shadowBorder);
        scientificPanel.setBorder(shadowBorder);
        advancedPanel.setBorder(shadowBorder);

        frame.add(panel);
        frame.add(scientificPanel);
        frame.add(advancedPanel);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        applyTheme(false);

        frame.requestFocus();
        frame.setVisible(true);
    }

    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            Color originalBackground;

            @Override
            public void mouseEntered(MouseEvent e) {
                originalBackground = button.getBackground();
                Color brighterColor = new Color(
                        Math.min(originalBackground.getRed() + 15, 255),
                        Math.min(originalBackground.getGreen() + 15, 255),
                        Math.min(originalBackground.getBlue() + 15, 255));
                button.setBackground(brighterColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackground);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(
                        Math.max(originalBackground.getRed() - 10, 0),
                        Math.max(originalBackground.getGreen() - 10, 0),
                        Math.max(originalBackground.getBlue() - 10, 0)));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.contains(e.getPoint())) {
                    button.setBackground(new Color(
                            Math.min(originalBackground.getRed() + 15, 255),
                            Math.min(originalBackground.getGreen() + 15, 255),
                            Math.min(originalBackground.getBlue() + 15, 255)));
                } else {
                    button.setBackground(originalBackground);
                }
            }
        });
    }

    private void applyTheme(boolean darkMode) {
        isDarkMode = darkMode;
        Color background = darkMode ? darkBackground : lightBackground;
        Color foreground = darkMode ? darkForeground : lightForeground;
        Color buttonBg = darkMode ? darkButtonBackground : lightButtonBackground;
        Color numberButtonBg = darkMode ? darkNumberButtonBackground : lightNumberButtonBackground;
        Color opButtonBg = darkMode ? darkOperationButtonBackground : lightOperationButtonBackground;
        Color sciButtonBg = darkMode ? darkScientificButtonBackground : lightScientificButtonBackground;

        frame.getContentPane().setBackground(background);
        headerPanel.setBackground(background);
        panel.setBackground(background);
        scientificPanel.setBackground(background);
        advancedPanel.setBackground(background);

        // In dark mode, use a dark gray instead of black
        textArea.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        textArea.setForeground(foreground);
        textArea.setCaretColor(foreground);

        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(darkMode ? new Color(50, 50, 50) : new Color(170, 170, 180), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        scrollPane.setBackground(background);
        scrollPane.getViewport().setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);

        themeToggleButton.setText(darkMode ? "Light" : "Dark");
        themeToggleButton.setBackground(buttonBg);
        themeToggleButton.setForeground(foreground);

        deleteButton.setBackground(buttonBg);
        deleteButton.setForeground(foreground);
        clearButton.setBackground(buttonBg);
        clearButton.setForeground(foreground);

        for (int i = 0; i < 10; i++) {
            numberButtons[i].setBackground(numberButtonBg);
            numberButtons[i].setForeground(foreground);
            numberButtons[i].setBorder(createButtonBorder(numberButtonBg, darkMode));
        }

        addButton.setBackground(opButtonBg);
        subtractButton.setBackground(opButtonBg);
        multiplyButton.setBackground(opButtonBg);
        divideButton.setBackground(opButtonBg);
        equalButton.setBackground(opButtonBg);

        addButton.setForeground(foreground);
        subtractButton.setForeground(foreground);
        multiplyButton.setForeground(foreground);
        divideButton.setForeground(foreground);
        equalButton.setForeground(foreground);

        addButton.setBorder(createButtonBorder(opButtonBg, darkMode));
        subtractButton.setBorder(createButtonBorder(opButtonBg, darkMode));
        multiplyButton.setBorder(createButtonBorder(opButtonBg, darkMode));
        divideButton.setBorder(createButtonBorder(opButtonBg, darkMode));
        equalButton.setBorder(createButtonBorder(opButtonBg, darkMode));

        sinButton.setBackground(sciButtonBg);
        sinButton.setForeground(foreground);
        cosButton.setBackground(sciButtonBg);
        cosButton.setForeground(foreground);
        tanButton.setBackground(sciButtonBg);
        tanButton.setForeground(foreground);
        logButton.setBackground(sciButtonBg);
        logButton.setForeground(foreground);
        sqrtButton.setBackground(sciButtonBg);
        sqrtButton.setForeground(foreground);
        powerButton.setBackground(sciButtonBg);
        powerButton.setForeground(foreground);
        piButton.setBackground(sciButtonBg);
        piButton.setForeground(foreground);
        eButton.setBackground(sciButtonBg);
        eButton.setForeground(foreground);

        sinButton.setBorder(createButtonBorder(sciButtonBg, darkMode));
        cosButton.setBorder(createButtonBorder(sciButtonBg, darkMode));
        tanButton.setBorder(createButtonBorder(sciButtonBg, darkMode));
        logButton.setBorder(createButtonBorder(sciButtonBg, darkMode));
        sqrtButton.setBorder(createButtonBorder(sciButtonBg, darkMode));
        powerButton.setBorder(createButtonBorder(sciButtonBg, darkMode));
        piButton.setBorder(createButtonBorder(sciButtonBg, darkMode));
        eButton.setBorder(createButtonBorder(sciButtonBg, darkMode));

        decimalButton.setBackground(numberButtonBg);
        decimalButton.setForeground(foreground);
        decimalButton.setBorder(createButtonBorder(numberButtonBg, darkMode));

        deleteButton.setBorder(createButtonBorder(buttonBg, darkMode));
        clearButton.setBorder(createButtonBorder(buttonBg, darkMode));
        if (darkMode) {
            themeToggleButton.setBorder(createButtonBorder(buttonBg, darkMode));
        } else {
            themeToggleButton.setBorder(createButtonBorder(buttonBg, darkMode));
        }

        historyButton.setBackground(buttonBg);
        historyButton.setForeground(foreground);
        historyButton.setBorder(createButtonBorder(buttonBg, darkMode));

        JButton[] advButtons = {
                sinhButton, coshButton, tanhButton, asinhButton, acoshButton,
                atanhButton, lnButton, log2Button, logbButton, factorialButton,
                permutationButton, combinationButton, matrixButton, complexButton,
                conversionButton, modButton, absButton, ceilButton, floorButton,
                radDegToggleButton, graphButton
        };

        for (JButton btn : advButtons) {
            btn.setBackground(sciButtonBg);
            btn.setForeground(foreground);
            btn.setBorder(createButtonBorder(sciButtonBg, darkMode));
        }

        historyFrame.getContentPane().setBackground(background);
        historyTextArea.setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);
        historyTextArea.setForeground(foreground);
        historyScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(darkMode ? new Color(50, 50, 50) : new Color(170, 170, 180), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        historyScrollPane.setBackground(background);
        historyScrollPane.getViewport().setBackground(darkMode ? new Color(50, 50, 50) : Color.WHITE);

        clearHistoryButton.setBackground(buttonBg);
        clearHistoryButton.setBackground(buttonBg);
        clearHistoryButton.setForeground(foreground);
        clearHistoryButton.setBorder(createButtonBorder(buttonBg, darkMode));

        SwingUtilities.updateComponentTreeUI(frame);
        SwingUtilities.updateComponentTreeUI(historyFrame);
    }

    private Border createButtonBorder(Color baseColor, boolean darkMode) {
        if (darkMode) {
            return BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        // Flat border for Light Mode
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new ScientificCalculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == themeToggleButton) {
            applyTheme(!isDarkMode);
            frame.requestFocus();
            return;
        }
        if (e.getSource() == historyButton) {
            historyFrame.setVisible(true);
            frame.requestFocusInWindow(); // Request focus when history is opened
            return;
        }

        // Number buttons
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (equalsClicked) {
                    clearTextArea();
                    equalsClicked = false;
                    lastExpression = "";
                    lastFullExpression = "";
                }
                appendStyled(String.valueOf(i), userInputStyle);
                currentExpression += i;
                operationClicked = false;
                return;
            }
        }

        // Decimal
        if (e.getSource() == decimalButton) {
            if (!currentExpression.endsWith(".")) {
                appendStyled(".", userInputStyle);
                currentExpression += ".";
            }
            return;
        }

        // pi
        if (e.getSource() == piButton) {
            if (equalsClicked) {
                clearTextArea();
                equalsClicked = false;
                lastExpression = "";
                lastFullExpression = "";
            }
            appendStyled("π", userInputStyle);
            currentExpression += String.valueOf(Math.PI);
            operationClicked = false;
            return;
        }

        // e
        if (e.getSource() == eButton) {
            if (equalsClicked) {
                clearTextArea();
                equalsClicked = false;
                lastExpression = "";
                lastFullExpression = "";
            }
            appendStyled("e", userInputStyle);
            currentExpression += String.valueOf(Math.E);
            operationClicked = false;
            return;
        }

        // Basic scientific: sin, cos, tan, log, sqrt
        if (e.getSource() == sinButton || e.getSource() == cosButton ||
                e.getSource() == tanButton || e.getSource() == logButton ||
                e.getSource() == sqrtButton) {

            if (currentExpression.isEmpty())
                return;
            try {
                double value = Double.parseDouble(currentExpression);
                double tempResult = 0;
                String func = "";
                if (e.getSource() == sinButton) {
                    func = "sin";
                    tempResult = Math.sin(isDegreeMode ? Math.toRadians(value) : value);
                } else if (e.getSource() == cosButton) {
                    func = "cos";
                    tempResult = Math.cos(isDegreeMode ? Math.toRadians(value) : value);
                } else if (e.getSource() == tanButton) {
                    func = "tan";
                    tempResult = Math.tan(isDegreeMode ? Math.toRadians(value) : value);
                } else if (e.getSource() == logButton) {
                    if (value <= 0) {
                        appendStyled("\nError: Log of non-positive number\n", errorStyle);
                        return;
                    }
                    func = "log";
                    tempResult = Math.log10(value);
                } else if (e.getSource() == sqrtButton) {
                    if (value < 0) {
                        appendStyled("\nError: Square root of negative number\n", errorStyle);
                        return;
                    }
                    func = "√";
                    tempResult = Math.sqrt(value);
                }
                clearTextArea();

                String expr = func + "(" + value + ")=" + formatResult(tempResult);
                appendStyled(expr + "\n", resultStyle);

                lastFullExpression = expr;
                lastExpression = formatResult(tempResult);
                currentExpression = String.valueOf(tempResult);
                num1 = BigDecimal.valueOf(tempResult);
                equalsClicked = true;
                operationClicked = false;
            } catch (Exception ex) {
                appendStyled("\nError: Invalid input\n", errorStyle);
            }
            return;
        }

        // Operators
        if (e.getSource() == addButton || e.getSource() == subtractButton ||
                e.getSource() == multiplyButton || e.getSource() == divideButton ||
                e.getSource() == powerButton) {

            if (operationClicked) {
                appendStyled("\nError: Multiple operators ignored\n", errorStyle);
                return;
            }
            if (!currentExpression.isEmpty()) {
                try {
                    num1 = new BigDecimal(currentExpression, mc);
                } catch (Exception ex) {
                    appendStyled("\nError: Invalid input\n", errorStyle);
                    return;
                }
                if (equalsClicked) {
                    equalsClicked = false;
                }
                char op = ' ';
                String opStrForDisplay = "";
                if (e.getSource() == powerButton) {
                    op = '^';
                    opStrForDisplay = "^";
                    appendStyled("^", userInputStyle);
                } else if (e.getSource() == addButton) {
                    op = '+';
                    opStrForDisplay = "+";
                    appendStyled("+", userInputStyle);
                } else if (e.getSource() == subtractButton) {
                    op = '-';
                    opStrForDisplay = "−";
                    appendStyled("−", userInputStyle);
                } else if (e.getSource() == multiplyButton) {
                    op = '*';
                    opStrForDisplay = "×";
                    appendStyled("×", userInputStyle);
                } else if (e.getSource() == divideButton) {
                    op = '/';
                    opStrForDisplay = "÷";
                    appendStyled("÷", userInputStyle);
                }
                operation = op;
                operationClicked = true;

                lastExpression = formatResult(num1.doubleValue()); // Format num1 here
                if (lastFullExpression.isEmpty()) {
                    lastFullExpression = lastExpression + opStrForDisplay;
                } else {
                    lastFullExpression = lastExpression + opStrForDisplay; // Use formatted lastExpression
                }
                currentExpression = "";
            }
            return;
        }

        // Equals
        if (e.getSource() == equalButton) {
            if (!equalsClicked && !currentExpression.isEmpty()) {
                try {
                    num2 = new BigDecimal(currentExpression, mc);
                } catch (Exception ex) {
                    return;
                }
                lastNum2 = num2;
            } else {
                num2 = lastNum2;
            }
            BigDecimal temp = BigDecimal.ZERO;
            try {
                switch (operation) {
                    case '+':
                        temp = num1.add(num2, mc);
                        break;
                    case '-':
                        temp = num1.subtract(num2, mc);
                        break;
                    case '*':
                        temp = num1.multiply(num2, mc);
                        break;
                    case '/':
                        if (num2.compareTo(BigDecimal.ZERO) == 0) {
                            appendStyled("\nError: Division by zero\n", errorStyle);
                            return;
                        }
                        temp = num1.divide(num2, mc);
                        break;
                    case '^':
                        temp = BigDecimal.valueOf(Math.pow(num1.doubleValue(), num2.doubleValue()));
                        break;
                }
            } catch (Exception ex) {
                appendStyled("\nError: Invalid arithmetic\n", errorStyle);
                return;
            }

            String expressionToShow;
            if (lastFullExpression.isEmpty()) {
                expressionToShow = currentExpression;
                lastFullExpression = currentExpression;
            } else {
                expressionToShow = lastFullExpression + currentExpression;
            }

            BigDecimal temp2 = temp.stripTrailingZeros();
            String finalStr = expressionToShow + "=" + formatResult(temp2.doubleValue());
            clearTextArea();
            appendStyled(finalStr + "\n", resultStyle);

            appendStyledToHistory(finalStr + "\n----------\n");

            lastExpression = formatResult(temp2.doubleValue());
            lastFullExpression = lastExpression; // Reset for chaining, using formatted result
            num1 = temp;
            currentExpression = String.valueOf(temp.doubleValue());
            result = temp;
            equalsClicked = true;
            operationClicked = false;
            return;
        }

        // AC / Clear
        if (e.getSource() == clearButton) {
            if (!getTextContent(textArea).isEmpty()) {
                appendStyledToHistory(getTextContent(textArea).trim() + "\n----------\n");
            }
            clearTextArea();
            num1 = BigDecimal.ZERO;
            num2 = BigDecimal.ZERO;
            result = BigDecimal.ZERO;
            equalsClicked = false;
            currentExpression = "";
            lastExpression = "";
            lastFullExpression = "";
            return;
        }

        // DEL
        if (e.getSource() == deleteButton) {
            if (!currentExpression.isEmpty() && !equalsClicked) {
                currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
                String txt = getTextContent(textArea);
                if (!txt.isEmpty()) {
                    txt = txt.substring(0, txt.length() - 1);
                    setTextContent(textArea, txt);
                }
            }
            return;
        }

        // Hyperbolic, etc.
        // Convert Button (Full Cross-Base Conversion)
        if (e.getSource() == conversionButton) {
            String[] options = {
                    "Decimal -> Hex", "Decimal -> Binary", "Decimal -> Octal",
                    "Hex -> Decimal", "Hex -> Binary", "Hex -> Octal",
                    "Binary -> Decimal", "Binary -> Hex", "Binary -> Octal",
                    "Octal -> Decimal", "Octal -> Hex", "Octal -> Binary"
            };

            String choice = (String) JOptionPane.showInputDialog(frame,
                    "Select conversion type:", "Convert",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choice == null)
                return;

            String[] parts = choice.split(" -> ");
            String sourceBaseName = parts[0];
            String targetBaseName = parts[1];

            long valueToConvert = 0;
            String originalInputDisplay = "";

            try {
                // 1. Get Source Value
                if (sourceBaseName.equals("Decimal")) {
                    if (currentExpression.isEmpty())
                        return;
                    double val = Double.parseDouble(currentExpression);
                    valueToConvert = (long) val;
                    originalInputDisplay = String.valueOf(valueToConvert);
                } else {
                    String input = JOptionPane.showInputDialog(frame, "Enter " + sourceBaseName + " value:");
                    if (input == null || input.trim().isEmpty())
                        return;
                    input = input.trim();
                    originalInputDisplay = input.toUpperCase();

                    int radix = 10;
                    if (sourceBaseName.equals("Hex"))
                        radix = 16;
                    else if (sourceBaseName.equals("Binary"))
                        radix = 2;
                    else if (sourceBaseName.equals("Octal"))
                        radix = 8;

                    valueToConvert = Long.parseLong(input, radix);
                }

                // 2. Convert to Target String
                String resultStr = "";
                String prefix = "";

                if (targetBaseName.equals("Decimal")) {
                    resultStr = String.valueOf(valueToConvert);
                    prefix = "Dec";
                } else if (targetBaseName.equals("Hex")) {
                    resultStr = Long.toHexString(valueToConvert).toUpperCase();
                    prefix = "Hex";
                } else if (targetBaseName.equals("Binary")) {
                    resultStr = Long.toBinaryString(valueToConvert);
                    prefix = "Bin";
                } else if (targetBaseName.equals("Octal")) {
                    resultStr = Long.toOctalString(valueToConvert);
                    prefix = "Oct";
                }

                // 3. Display Result
                clearTextArea();
                String sourcePrefix = sourceBaseName.substring(0, 3);
                String output = sourcePrefix + "(" + originalInputDisplay + ")=" + prefix + "(" + resultStr + ")";
                appendStyled(output + "\n", resultStyle);

                lastFullExpression = output;
                lastExpression = resultStr; // Store result string

                // If target is Decimal, we can treat it as a number for further ops
                if (targetBaseName.equals("Decimal")) {
                    currentExpression = resultStr;
                    num1 = BigDecimal.valueOf(valueToConvert);
                } else {
                    currentExpression = ""; // Reset if non-decimal result to avoid parse errors on next op
                }

                equalsClicked = true;
                operationClicked = false;

            } catch (NumberFormatException ex) {
                appendStyled("\nError: Invalid " + sourceBaseName + "\n", errorStyle);
            } catch (Exception ex) {
                appendStyled("\nError: Conversion Failed\n", errorStyle);
            }
            return;
        }

        // Clear History
        if (e.getSource() == clearHistoryButton)

        {
            setTextContent(historyTextArea, "");
            return;
        }

        frame.requestFocus();
    }

    private String formatResult(double value) {
        BigDecimal bd = BigDecimal.valueOf(value).stripTrailingZeros();
        if (bd.scale() <= 0) {
            return bd.intValue() + "";
        } else {
            return bd.toPlainString();
        }
    }

    private void clearTextArea() {
        setTextContent(textArea, "");
    }

    private String getTextContent(JTextPane pane) {
        return pane.getText();
    }

    private void setTextContent(JTextPane pane, String txt) {
        pane.setText(txt);
    }

    private void appendStyled(String str, AttributeSet style) {
        try {
            StyledDocument doc = textArea.getStyledDocument();
            doc.insertString(doc.getLength(), str, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void appendStyledToHistory(String str) {
        try {
            StyledDocument doc = historyTextArea.getStyledDocument();
            doc.insertString(doc.getLength(), str, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        boolean shiftDown = e.isShiftDown();
        boolean ctrlDown = e.isControlDown();

        if (!shiftDown && (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9)) {
            int number = keyCode - KeyEvent.VK_0;
            numberButtons[number].doClick();
        } else if (keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) {
            int number = keyCode - KeyEvent.VK_NUMPAD0;
            numberButtons[number].doClick();
        }

        if (shiftDown) {
            switch (keyCode) {
                case KeyEvent.VK_1:
                    factorialButton.doClick();
                    return;
                case KeyEvent.VK_5:
                    modButton.doClick();
                    return;
                case KeyEvent.VK_8:
                    multiplyButton.doClick();
                    return;
                case KeyEvent.VK_EQUALS:
                    addButton.doClick();
                    return;
                case KeyEvent.VK_6:
                    powerButton.doClick();
                    return;
            }
        }
        if (ctrlDown) {
            switch (keyCode) {
                case KeyEvent.VK_S:
                    sinButton.doClick();
                    return;
                case KeyEvent.VK_C:
                    cosButton.doClick();
                    return;
                case KeyEvent.VK_T:
                    tanButton.doClick();
                    return;
                case KeyEvent.VK_L:
                    logButton.doClick();
                    return;
                case KeyEvent.VK_R:
                    sqrtButton.doClick();
                    return;
                case KeyEvent.VK_P:
                    piButton.doClick();
                    return;
                case KeyEvent.VK_E:
                    eButton.doClick();
                    return;
                case KeyEvent.VK_D:
                    themeToggleButton.doClick();
                    return;
            }
        }

        switch (keyCode) {
            case KeyEvent.VK_PLUS:
            case KeyEvent.VK_ADD:
                addButton.doClick();
                break;
            case KeyEvent.VK_MINUS:
            case KeyEvent.VK_SUBTRACT:
                subtractButton.doClick();
                break;
            case KeyEvent.VK_ASTERISK:
            case KeyEvent.VK_MULTIPLY:
                multiplyButton.doClick();
                break;
            case KeyEvent.VK_SLASH:
            case KeyEvent.VK_DIVIDE:
                divideButton.doClick();
                break;
            case KeyEvent.VK_PERIOD:
            case KeyEvent.VK_DECIMAL:
                decimalButton.doClick();
                break;
            case KeyEvent.VK_EQUALS:
            case KeyEvent.VK_ENTER:
                equalButton.doClick();
                break;
            case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_DELETE:
                deleteButton.doClick();
                break;
            case KeyEvent.VK_ESCAPE:
                clearButton.doClick();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static class MathEngine {
        public static long factorial(int n) {
            if (n < 0)
                throw new IllegalArgumentException("Negative number");
            long result = 1;
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
            return result;
        }

        public static long permutation(int n, int r) {
            if (r > n || n < 0 || r < 0)
                throw new IllegalArgumentException("Invalid values");
            return factorial(n) / factorial(n - r);
        }

        public static long combination(int n, int r) {
            if (r > n || n < 0 || r < 0)
                throw new IllegalArgumentException("Invalid values");
            return factorial(n) / (factorial(r) * factorial(n - r));
        }
    }

    public static class MatrixOps {
        public static double[][] add(double[][] A, double[][] B) {
            double[][] res = new double[2][2];
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    res[i][j] = A[i][j] + B[i][j];
            return res;
        }

        public static double[][] subtract(double[][] A, double[][] B) {
            double[][] res = new double[2][2];
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    res[i][j] = A[i][j] - B[i][j];
            return res;
        }

        public static double[][] multiply(double[][] A, double[][] B) {
            double[][] res = new double[2][2];
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    res[i][j] = A[i][0] * B[0][j] + A[i][1] * B[1][j];
            return res;
        }

        public static double determinant(double[][] A) {
            return A[0][0] * A[1][1] - A[0][1] * A[1][0];
        }

        public static double[][] inverse(double[][] A) {
            double det = determinant(A);
            if (det == 0)
                throw new ArithmeticException("Singular matrix");
            double[][] inv = new double[2][2];
            inv[0][0] = A[1][1] / det;
            inv[0][1] = -A[0][1] / det;
            inv[1][0] = -A[1][0] / det;
            inv[1][1] = A[0][0] / det;
            return inv;
        }

        public static String toString(double[][] M) {
            return String.format("[%.2f  %.2f]\n[%.2f  %.2f]", M[0][0], M[0][1], M[1][0], M[1][1]);
        }
    }

    public static class Complex {
        double re;
        double im;

        public Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }

        public Complex(String s) {
            s = s.replace(" ", "");
            String[] parts = s.split("\\+");
            if (parts.length == 2) {
                re = Double.parseDouble(parts[0]);
                im = Double.parseDouble(parts[1].replace("i", ""));
            } else if (s.contains("-")) {
                int index = s.indexOf('-', 1);
                if (index > 0) {
                    re = Double.parseDouble(s.substring(0, index));
                    im = Double.parseDouble(s.substring(index, s.length() - 1));
                } else {
                    re = Double.parseDouble(s.replace("i", ""));
                    im = 0;
                }
            } else {
                re = Double.parseDouble(s.replace("i", ""));
                im = s.contains("i") ? 1 : 0;
            }
        }

        public Complex add(Complex c) {
            return new Complex(this.re + c.re, this.im + c.im);
        }

        public Complex subtract(Complex c) {
            return new Complex(this.re - c.re, this.im - c.im);
        }

        public Complex multiply(Complex c) {
            return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + this.im * c.re);
        }

        public Complex divide(Complex c) {
            double denom = c.re * c.re + c.im * c.im;
            return new Complex(
                    (this.re * c.re + this.im * c.im) / denom,
                    (this.im * c.re - this.re * c.im) / denom);
        }

        @Override
        public String toString() {
            return String.format("%.2f %c %.2fi", re, (im >= 0 ? '+' : '-'), Math.abs(im));
        }
    }

    // Minimal Graph Frame to show a small sine wave
    class GraphFrame extends JFrame {
        public GraphFrame(boolean dark) {
            super("Simple Graph");
            setSize(600, 400);
            setLocationRelativeTo(frame);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            add(new GraphPanel(dark));
            setVisible(true);
        }
    }

    class GraphPanel extends JPanel {
        boolean darkModeGraph;

        public GraphPanel(boolean dark) {
            darkModeGraph = dark;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();

            // Background
            if (darkModeGraph) {
                g2.setColor(new Color(50, 50, 50));
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.fillRect(0, 0, w, h);

            // Axes
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(darkModeGraph ? Color.LIGHT_GRAY : Color.BLACK);
            int midY = h / 2;
            g2.drawLine(0, midY, w, midY); // x-axis
            g2.drawLine(w / 2, 0, w / 2, h); // y-axis

            // Simple sine wave
            g2.setColor(Color.RED);
            Path2D.Double path = new Path2D.Double();
            double scaleX = 0.04; // how “wide” the wave is
            double scaleY = 50; // amplitude
            int centerX = w / 2;
            int centerY = h / 2;

            // Start path
            path.moveTo(0, centerY);
            for (int x = 0; x < w; x++) {
                double angle = (x - centerX) * scaleX;
                double yVal = Math.sin(angle) * scaleY;
                path.lineTo(x, centerY - yVal);
            }
            g2.draw(path);
        }
    }
}