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
        frame.setSize(600, 900);
        frame.setLayout(null);
        frame.setResizable(true);

        headerPanel = new JPanel();
        headerPanel.setBounds(5, 5, 580, 40);
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
        textArea.setFocusable(true); // Allow focus for text selection (Cmd+A)
        textArea.addKeyListener(this); // Ensure calculator keys work when text area has focus
        textArea.setFont(fo);
        textArea.setEditable(false);
        textArea.setTransferHandler(null); // Block pasting (Cmd+V) to prevent invalid text insertion
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(5, 50, 580, 120);
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
        // Custom hover effect for themeToggleButton to handle dynamic theme changes
        themeToggleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color baseColor = isDarkMode ? darkButtonBackground : lightButtonBackground;
                Color hoverColor = new Color(
                        Math.min(baseColor.getRed() + 15, 255),
                        Math.min(baseColor.getGreen() + 15, 255),
                        Math.min(baseColor.getBlue() + 15, 255));
                themeToggleButton.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Always reset to current theme's button background
                themeToggleButton.setBackground(isDarkMode ? darkButtonBackground : lightButtonBackground);
            }
        });

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
        panel.setBounds(5, 180, 580, 220);
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
        scientificPanel.setBounds(5, 410, 580, 120);
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
        advancedPanel.setBounds(5, 540, 580, 300);
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
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
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
            } else if (!equalsClicked && currentExpression.isEmpty()) {
                // Implicit second operand (e.g. "2 + =") -> Treat as "2 + 2 ="
                num2 = num1;
                lastNum2 = num1;
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
                    case 'L':
                        temp = BigDecimal.valueOf(Math.log(num1.doubleValue()) / Math.log(num2.doubleValue()));
                        break;
                    case 'P':
                        temp = BigDecimal.valueOf(perm(num1.longValue(), num2.longValue()));
                        break;
                    case 'C':
                        temp = BigDecimal.valueOf(comb(num1.longValue(), num2.longValue()));
                        break;
                    case '%':
                        temp = num1.remainder(num2, mc);
                        break;
                }
            } catch (Exception ex) {
                appendStyled("\nError: Invalid arithmetic\n", errorStyle);
                return;
            }

            String expressionToShow;
            if (equalsClicked) {
                // Repeated equals
                expressionToShow = formatResult(num1.doubleValue()) + getOperationSymbol(operation)
                        + formatResult(num2.doubleValue());
                lastFullExpression = "";
            } else {
                if (lastFullExpression.isEmpty()) {
                    expressionToShow = currentExpression;
                    lastFullExpression = currentExpression;
                } else {
                    // Use num2 explicitly to handle the implicit case (e.g. "134+" -> "134+134")
                    expressionToShow = lastFullExpression + formatResult(num2.doubleValue());
                }
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
            // If text is selected (Ctrl+A / Cmd+A), act as AC (Clear All)
            if (textArea.getSelectedText() != null) {
                clearButton.doClick();
                return;
            }

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

        // Advanced Functions
        handleAdvancedMath(e);

        // Clear History
        if (e.getSource() == clearHistoryButton) {
            setTextContent(historyTextArea, "");
            return;
        }

        // Graph Button
        if (e.getSource() == graphButton) {
            new GraphFrame(isDarkMode);
            return;
        }

        // Rad/Deg Toggle
        if (e.getSource() == radDegToggleButton) {
            isDegreeMode = !isDegreeMode;
            radDegToggleButton.setText(isDegreeMode ? "Deg" : "Rad");
            return;
        }

        frame.requestFocus();
    }

    // Helper method to keep actionPerformed clean
    private void handleAdvancedMath(ActionEvent e) {
        Object src = e.getSource();

        try {
            // Unary Operations (Act immediately on current input)
            if (src == sinhButton || src == coshButton || src == tanhButton ||
                    src == asinhButton || src == acoshButton || src == atanhButton ||
                    src == lnButton || src == log2Button ||
                    src == factorialButton ||
                    src == ceilButton || src == floorButton ||
                    src == absButton || // Added absButton
                    src == matrixButton || src == complexButton) {

                if (currentExpression.isEmpty())
                    return;
                BigDecimal val = new BigDecimal(currentExpression, mc);
                double d = val.doubleValue();
                double res = 0;
                String opName = "";

                if (src == sinhButton) {
                    res = Math.sinh(d);
                    opName = "sinh";
                } else if (src == coshButton) {
                    res = Math.cosh(d);
                    opName = "cosh";
                } else if (src == tanhButton) {
                    res = Math.tanh(d);
                    opName = "tanh";
                } else if (src == asinhButton) {
                    res = Math.log(d + Math.sqrt(d * d + 1));
                    opName = "asinh";
                } else if (src == acoshButton) {
                    res = Math.log(d + Math.sqrt(d * d - 1));
                    opName = "acosh";
                } else if (src == atanhButton) {
                    res = 0.5 * Math.log((1 + d) / (1 - d));
                    opName = "atanh";
                } else if (src == lnButton) {
                    res = Math.log(d);
                    opName = "ln";
                } else if (src == log2Button) {
                    res = Math.log(d) / Math.log(2);
                    opName = "log2";
                } else if (src == ceilButton) {
                    res = Math.ceil(d);
                    opName = "ceil";
                } else if (src == floorButton) {
                    res = Math.floor(d);
                    opName = "floor";
                } else if (src == absButton) {
                    res = Math.abs(d);
                    opName = "abs";
                } // Added abs logic
                else if (src == factorialButton) {
                    if (d < 0 || d != Math.floor(d))
                        throw new ArithmeticException("Invalid factorial input");
                    res = 1;
                    for (int i = 2; i <= (int) d; i++)
                        res *= i;
                    opName = "fact";
                } else if (src == matrixButton || src == complexButton) {
                    JOptionPane.showMessageDialog(frame, "Feature coming soon!", "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                currentExpression = formatResult(res);
                clearTextArea();
                appendStyled(opName + "(" + formatResult(d) + ") = " + currentExpression + "\n", resultStyle);
                num1 = BigDecimal.valueOf(res);
                return;
            }

            // Binary Operations (Set operation and wait for second operand)
            if (src == logbButton || src == permutationButton || src == combinationButton || src == modButton) {
                if (currentExpression.isEmpty())
                    return;
                num1 = new BigDecimal(currentExpression, mc);

                String opStr = "";
                if (src == logbButton) {
                    operation = 'L';
                    opStr = " logb ";
                } else if (src == permutationButton) {
                    operation = 'P';
                    opStr = " nPr ";
                } else if (src == combinationButton) {
                    operation = 'C';
                    opStr = " nCr ";
                } else if (src == modButton) {
                    operation = '%';
                    opStr = " % ";
                }

                appendStyled(opStr, userInputStyle);

                lastExpression = formatResult(num1.doubleValue());
                if (lastFullExpression.isEmpty()) {
                    lastFullExpression = lastExpression + opStr;
                } else {
                    lastFullExpression = lastExpression + opStr;
                }

                currentExpression = "";
                operationClicked = true;
                equalsClicked = false;
                return;
            }

        } catch (Exception ex) {
            appendStyled("\nError: " + ex.getMessage() + "\n", errorStyle);
        }
    }

    private String getOperationSymbol(char op) {
        switch (op) {
            case '+':
                return "+";
            case '-':
                return "−";
            case '*':
                return "×";
            case '/':
                return "÷";
            case '^':
                return "^";
            case '%':
                return "%";
            case 'L':
                return " logb ";
            case 'P':
                return " nPr ";
            case 'C':
                return " nCr ";
            default:
                return "";
        }
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
        boolean metaDown = e.isMetaDown();

        // Handle Select All (Cmd+A or Ctrl+A)
        if ((metaDown || ctrlDown) && keyCode == KeyEvent.VK_A) {
            textArea.selectAll();
            return;
        }

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
        // Consumer all typed events to prevent direct text entry into the JTextPane
        // This stops the "2+=3" issue where raw keys are inserted bypassing logic
        e.consume();
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

    private double perm(long n, long r) {
        if (r < 0 || r > n)
            return 0;
        double res = 1;
        for (long i = 0; i < r; i++) {
            res *= (n - i);
        }
        return res;
    }

    private double comb(long n, long r) {
        if (r < 0 || r > n)
            return 0;
        if (r > n / 2)
            r = n - r; // Symmetry
        double res = 1;
        for (long i = 1; i <= r; i++) {
            res = res * (n - i + 1) / i;
        }
        return res;
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

    // Simple Expression Parser for Graphing
    static class FunctionParser {
        public static double eval(String expression, double x) {
            try {
                return new Object() {
                    int pos = -1, ch;

                    void nextChar() {
                        ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
                    }

                    boolean eat(int charToEat) {
                        while (ch == ' ')
                            nextChar();
                        if (ch == charToEat) {
                            nextChar();
                            return true;
                        }
                        return false;
                    }

                    double parse() {
                        nextChar();
                        double xVal = parseExpression();
                        if (pos < expression.length())
                            throw new RuntimeException("Unexpected: " + (char) ch);
                        return xVal;
                    }

                    double parseExpression() {
                        double xVal = parseTerm();
                        for (;;) {
                            if (eat('+'))
                                xVal += parseTerm(); // addition
                            else if (eat('-'))
                                xVal -= parseTerm(); // subtraction
                            else
                                return xVal;
                        }
                    }

                    double parseTerm() {
                        double xVal = parseFactor();
                        for (;;) {
                            if (eat('*'))
                                xVal *= parseFactor(); // multiplication
                            else if (eat('/'))
                                xVal /= parseFactor(); // division
                            else if (ch == '(' || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '.')
                                xVal *= parseFactor(); // implicit multiplication
                            else
                                return xVal;
                        }
                    }

                    double parseFactor() {
                        if (eat('+'))
                            return parseFactor(); // unary plus
                        if (eat('-'))
                            return -parseFactor(); // unary minus

                        double xVal;
                        int startPos = this.pos;
                        if (eat('(')) { // parentheses
                            xVal = parseExpression();
                            eat(')');
                        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                            while ((ch >= '0' && ch <= '9') || ch == '.')
                                nextChar();
                            xVal = Double.parseDouble(expression.substring(startPos, this.pos));
                        } else if (ch == 'x') { // variable x
                            nextChar();
                            xVal = x;
                        } else if (ch >= 'a' && ch <= 'z') { // functions & constants
                            while (ch >= 'a' && ch <= 'z')
                                nextChar();
                            String func = expression.substring(startPos, this.pos);
                            if (eat('(')) {
                                xVal = parseExpression();
                                eat(')');
                                if (func.equals("sin"))
                                    xVal = Math.sin(xVal);
                                else if (func.equals("cos"))
                                    xVal = Math.cos(xVal);
                                else if (func.equals("tan"))
                                    xVal = Math.tan(xVal);
                                else if (func.equals("sinh"))
                                    xVal = Math.sinh(xVal);
                                else if (func.equals("cosh"))
                                    xVal = Math.cosh(xVal);
                                else if (func.equals("tanh"))
                                    xVal = Math.tanh(xVal);
                                else if (func.equals("asinh"))
                                    xVal = Math.log(xVal + Math.sqrt(xVal * xVal + 1));
                                else if (func.equals("acosh"))
                                    xVal = Math.log(xVal + Math.sqrt(xVal * xVal - 1));
                                else if (func.equals("atanh"))
                                    xVal = 0.5 * Math.log((1 + xVal) / (1 - xVal));
                                else if (func.equals("sqrt"))
                                    xVal = Math.sqrt(xVal);
                                else if (func.equals("log"))
                                    xVal = Math.log10(xVal);
                                else if (func.equals("ln"))
                                    xVal = Math.log(xVal);
                                else if (func.equals("abs"))
                                    xVal = Math.abs(xVal);
                                else if (func.equals("ceil"))
                                    xVal = Math.ceil(xVal);
                                else if (func.equals("floor"))
                                    xVal = Math.floor(xVal);
                                else
                                    throw new RuntimeException("Unknown function: " + func);
                            } else {
                                // Constants or variable acting as function without parens (rare but safe to
                                // check)
                                if (func.equals("x"))
                                    xVal = x;
                                else if (func.equals("pi"))
                                    xVal = Math.PI;
                                else if (func.equals("e"))
                                    xVal = Math.E;
                                else
                                    throw new RuntimeException("Unknown variable/constant: " + func);
                            }
                        } else {
                            throw new RuntimeException("Unexpected: " + (char) ch);
                        }

                        if (eat('^'))
                            xVal = Math.pow(xVal, parseFactor()); // exponentiation

                        return xVal;
                    }
                }.parse();
            } catch (Exception e) {
                return Double.NaN;
            }
        }
    }

    // Minimal Graph Frame to show a small sine wave
    class GraphFrame extends JFrame {
        public GraphFrame(boolean dark) {
            super("Simple Graph");
            setSize(600, 500);
            setLocationRelativeTo(frame);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            GraphPanel graphPanel = new GraphPanel(dark);
            add(graphPanel, BorderLayout.CENTER);

            // Control Panel
            JPanel controlPanel = new JPanel();
            controlPanel.setBackground(dark ? new Color(30, 30, 35) : Color.WHITE);

            JTextField inputField = new JTextField("sin(x)", 20);
            JButton plotButton = new JButton("Plot");

            controlPanel.add(new JLabel("y = "));
            controlPanel.add(inputField);
            controlPanel.add(plotButton);

            add(controlPanel, BorderLayout.SOUTH);

            plotButton.addActionListener(e -> {
                graphPanel.setFunction(inputField.getText());
                graphPanel.repaint();
            });

            // Slider for X Navigation
            JSlider xSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
            xSlider.setMajorTickSpacing(20);
            xSlider.setPaintTicks(true);
            xSlider.setBackground(dark ? new Color(30, 30, 35) : Color.WHITE);
            // The method setCenterX will be added to GraphPanel in the next step
            // We use a temporary lambda that will be valid once setCenterX exists
            xSlider.addChangeListener(ev -> {
                double val = xSlider.getValue();
                graphPanel.setCenterX(val);
            });
            add(xSlider, BorderLayout.NORTH);

            // Slider for Y Navigation
            JSlider ySlider = new JSlider(JSlider.VERTICAL, -100, 100, 0);
            ySlider.setMajorTickSpacing(20);
            ySlider.setPaintTicks(true);
            ySlider.setBackground(dark ? new Color(30, 30, 35) : Color.WHITE);
            // The method setCenterY will be added to GraphPanel in the next step
            ySlider.addChangeListener(ev -> {
                double val = ySlider.getValue();
                graphPanel.setCenterY(val);
            });
            add(ySlider, BorderLayout.EAST);

            setVisible(true);
        }
    }

    class GraphPanel extends JPanel {
        boolean darkModeGraph;
        double minX = -10;
        double maxX = 10;
        double minY = -6;
        double maxY = 6;
        String currentFunction = "sin(x)";

        public GraphPanel(boolean dark) {
            darkModeGraph = dark;

            // Pan & Zoom Interaction
            MouseAdapter inputHandler = new MouseAdapter() {
                private Point lastDragPoint;

                @Override
                public void mousePressed(MouseEvent e) {
                    lastDragPoint = e.getPoint();
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (lastDragPoint == null)
                        return;
                    Point current = e.getPoint();
                    int dx = current.x - lastDragPoint.x;
                    int dy = current.y - lastDragPoint.y;

                    double scaleX = getWidth() / (maxX - minX);
                    double scaleY = getHeight() / (maxY - minY);

                    double logicalDx = dx / scaleX;
                    double logicalDy = dy / scaleY;

                    minX -= logicalDx;
                    maxX -= logicalDx;
                    minY += logicalDy; // Y inverted
                    maxY += logicalDy;

                    lastDragPoint = current;
                    repaint();
                }

                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    double factor = (e.getWheelRotation() < 0) ? 0.9 : 1.1;

                    double width = maxX - minX;
                    double height = maxY - minY;
                    double newWidth = width * factor;
                    double newHeight = height * factor;

                    double centerX = (minX + maxX) / 2;
                    double centerY = (minY + maxY) / 2;

                    minX = centerX - newWidth / 2;
                    maxX = centerX + newWidth / 2;
                    minY = centerY - newHeight / 2;
                    maxY = centerY + newHeight / 2;

                    repaint();
                }
            };

            addMouseListener(inputHandler);
            addMouseMotionListener(inputHandler);
            addMouseWheelListener(inputHandler);
        }

        public void setFunction(String func) {
            this.currentFunction = func;
        }

        public void setCenterX(double center) {
            double width = maxX - minX;
            minX = center - width / 2.0;
            maxX = center + width / 2.0;
            repaint();
        }

        public void setCenterY(double center) {
            double height = maxY - minY;
            minY = center - height / 2.0;
            maxY = center + height / 2.0;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Background
            g2.setColor(darkModeGraph ? new Color(30, 30, 35) : Color.WHITE);
            g2.fillRect(0, 0, w, h);

            // Calculate Scale
            double scaleX = w / (maxX - minX);
            double scaleY = h / (maxY - minY);

            // Grid Styling
            Color majorGridColor = darkModeGraph ? new Color(70, 70, 70) : new Color(220, 220, 220);
            Color axisColor = darkModeGraph ? Color.WHITE : Color.BLACK;
            Color textColor = darkModeGraph ? Color.LIGHT_GRAY : Color.GRAY;

            // Draw Grid and Labels
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            FontMetrics fm = g2.getFontMetrics();

            // Vertical Grid Lines (X steps)
            for (int x = (int) Math.ceil(minX); x <= (int) Math.floor(maxX); x++) {
                int xPixel = (int) ((x - minX) * scaleX);

                // Grid Line
                if (x == 0) {
                    g2.setColor(axisColor);
                    g2.setStroke(new BasicStroke(2f));
                } else {
                    g2.setColor(majorGridColor);
                    g2.setStroke(new BasicStroke(1f));
                }
                g2.drawLine(xPixel, 0, xPixel, h);

                // Label
                if (x != 0) {
                    g2.setColor(textColor);
                    String label = String.valueOf(x);
                    int labelWidth = fm.stringWidth(label);
                    // Position label near x-axis (axis Y is at 0)
                    // yPixel = (maxY - yVal) * scaleY. For y=0, yPixel = maxY * scaleY.
                    int yPos = (int) (maxY * scaleY) + 15;
                    // Clamp to screen
                    if (yPos > h - 5)
                        yPos = h - 5;
                    if (yPos < 15)
                        yPos = 15;
                    g2.drawString(label, xPixel - labelWidth / 2, yPos);
                }
            }

            // Horizontal Grid Lines (Y steps)
            for (int y = (int) Math.ceil(minY); y <= (int) Math.floor(maxY); y++) {
                int yPixel = (int) ((maxY - y) * scaleY);

                // Grid Line
                if (y == 0) {
                    g2.setColor(axisColor);
                    g2.setStroke(new BasicStroke(2f));
                } else {
                    g2.setColor(majorGridColor);
                    g2.setStroke(new BasicStroke(1f));
                }
                g2.drawLine(0, yPixel, w, yPixel);

                // Label
                if (y != 0) {
                    g2.setColor(textColor);
                    String label = String.valueOf(y);
                    int labelWidth = fm.stringWidth(label);
                    int zeroXPixel = (int) ((0 - minX) * scaleX);
                    // Position label near y-axis (axis X is at 0)
                    int xPos = zeroXPixel - labelWidth - 5;
                    // Clamp
                    if (xPos < 5)
                        xPos = 5;
                    if (xPos > w - labelWidth - 5)
                        xPos = w - labelWidth - 5;
                    g2.drawString(label, xPos, yPixel + 4);
                }
            }

            // Plot Function: y = sin(x) for simplicity
            // In a real scientific calculator, this should parse 'currentExpression' with x
            g2.setColor(new Color(255, 60, 60)); // Desmos Red
            g2.setStroke(new BasicStroke(2.5f));

            Path2D.Double path = new Path2D.Double();
            boolean first = true;

            // Step size in pixels
            for (int i = 0; i <= w; i++) {
                // Convert pixel X to logical X
                double logicalX = minX + (i / scaleX);
                double logicalY = FunctionParser.eval(currentFunction, logicalX);

                // Convert logical Y to pixel Y
                // pixelY = (maxY - logicalY) * scaleY
                double pixelY = (maxY - logicalY) * scaleY;

                // Avoid drawing lines to infinity if value is NaN or Infinite
                if (Double.isNaN(pixelY) || Double.isInfinite(pixelY)) {
                    first = true; // reset path
                    continue;
                }

                // Clamp for drawing performance (optional, but good practice)
                if (pixelY < -500 || pixelY > h + 500) {
                    if (!first)
                        path.lineTo(i, pixelY); // Draw to offscreen point to maintain line direction
                    continue;
                }

                if (first) {
                    path.moveTo(i, pixelY);
                    first = false;
                } else {
                    path.lineTo(i, pixelY);
                }
            }
            g2.draw(path);
        }
    }
}