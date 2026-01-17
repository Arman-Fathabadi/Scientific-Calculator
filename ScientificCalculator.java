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

    // Static reference for JavaScript interop
    public static ScientificCalculator instance;

    public ScientificCalculator(boolean autoStartDark) {
        frame = new JFrame("Scientific Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 900);
        frame.setSize(600, 900);
        frame.setLayout(null);
        frame.setResizable(true);
        instance = this; // Capture instance

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

        // Check for web theme sync via system property or constructor arg
        boolean sysPropDark = "true".equals(System.getProperty("darkMode", "false"));
        applyTheme(autoStartDark || sysPropDark);

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

        boolean startDark = false;
        if (args.length > 0 && "dark".equalsIgnoreCase(args[0])) {
            startDark = true;
        }

        new ScientificCalculator(startDark);
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

            // Check for empty operation (Identity case: 137 =)
            if (operation == 0 || operation == ' ') {
                temp = num2;
            } else {
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
            }

            String expressionToShow;
            if (equalsClicked) {
                // Repeated equals
                if (operation == 0 || operation == ' ') {
                    expressionToShow = formatResult(num1.doubleValue());
                } else {
                    expressionToShow = formatResult(num1.doubleValue()) + getOperationSymbol(operation)
                            + formatResult(num2.doubleValue());
                }
                lastFullExpression = "";
            } else {
                if (lastFullExpression.isEmpty()) {
                    expressionToShow = currentExpression;
                    lastFullExpression = currentExpression;
                } else {
                    // Use num2 explicitly to handle the implicit case (e.g. "134+" -> "134+134")
                    if (operation == 0 || operation == ' ') {
                        expressionToShow = formatResult(num2.doubleValue());
                    } else {
                        expressionToShow = lastFullExpression + formatResult(num2.doubleValue());
                    }
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
        // Evaluate y = f(x)
        public static double eval(String expression, double x) {
            return eval(expression, x, 0);
        }

        // Evaluate f(x, y)
        public static double eval(String expression, double x, double y) {
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
                        double val = parseExpression();
                        if (pos < expression.length())
                            throw new RuntimeException("Unexpected: " + (char) ch);
                        return val;
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

                        double val;
                        int startPos = this.pos;
                        if (eat('(')) { // parentheses
                            val = parseExpression();
                            eat(')');
                        } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                            while ((ch >= '0' && ch <= '9') || ch == '.')
                                nextChar();
                            val = Double.parseDouble(expression.substring(startPos, this.pos));
                        } else if (ch == 'x') { // variable x
                            nextChar();
                            val = x;
                        } else if (ch == 'y') { // variable y
                            nextChar();
                            val = y;
                        } else if (ch >= 'a' && ch <= 'z') { // functions & constants
                            while (ch >= 'a' && ch <= 'z')
                                nextChar();
                            String func = expression.substring(startPos, this.pos);
                            if (eat('(')) {
                                val = parseExpression();
                                eat(')');
                                if (func.equals("sin"))
                                    val = Math.sin(val);
                                else if (func.equals("cos"))
                                    val = Math.cos(val);
                                else if (func.equals("tan"))
                                    val = Math.tan(val);
                                else if (func.equals("sinh"))
                                    val = Math.sinh(val);
                                else if (func.equals("cosh"))
                                    val = Math.cosh(val);
                                else if (func.equals("tanh"))
                                    val = Math.tanh(val);
                                else if (func.equals("asinh"))
                                    val = Math.log(val + Math.sqrt(val * val + 1));
                                else if (func.equals("acosh"))
                                    val = Math.log(val + Math.sqrt(val * val - 1));
                                else if (func.equals("atanh"))
                                    val = 0.5 * Math.log((1 + val) / (1 - val));
                                else if (func.equals("sqrt"))
                                    val = Math.sqrt(val);
                                else if (func.equals("log"))
                                    val = Math.log10(val);
                                else if (func.equals("ln"))
                                    val = Math.log(val);
                                else if (func.equals("abs"))
                                    val = Math.abs(val);
                                else if (func.equals("ceil"))
                                    val = Math.ceil(val);
                                else if (func.equals("floor"))
                                    val = Math.floor(val);
                                else
                                    throw new RuntimeException("Unknown function: " + func);
                            } else {
                                // Constants
                                if (func.equals("pi"))
                                    val = Math.PI;
                                else if (func.equals("e"))
                                    val = Math.E;
                                else
                                    throw new RuntimeException("Unknown variable: " + func);
                            }
                        } else {
                            throw new RuntimeException("Unexpected: " + (char) ch);
                        }

                        if (eat('^'))
                            val = Math.pow(val, parseFactor()); // exponentiation

                        return val;
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
            // Fix: Add KeyListener for Select All (Cmd+A / Ctrl+A)
            inputField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    boolean isMeta = e.isMetaDown();
                    boolean isCtrl = e.isControlDown();
                    if ((isMeta || isCtrl) && e.getKeyCode() == KeyEvent.VK_A) {
                        inputField.selectAll();
                        e.consume();
                    }
                }
            });
            JButton plotButton = new JButton("Plot");
            JButton zoomInButton = new JButton("+");
            JButton zoomOutButton = new JButton("-");

            controlPanel.add(new JLabel("y = "));
            controlPanel.add(inputField);
            controlPanel.add(plotButton);
            controlPanel.add(new JLabel("  Zoom:"));
            controlPanel.add(zoomInButton);
            controlPanel.add(zoomOutButton);

            add(controlPanel, BorderLayout.SOUTH);

            plotButton.addActionListener(e -> {
                graphPanel.setFunction(inputField.getText());
                graphPanel.repaint();
            });

            zoomInButton.addActionListener(e -> graphPanel.zoomIn());
            zoomOutButton.addActionListener(e -> graphPanel.zoomOut());

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
                    zoom(factor);
                }
            };

            addMouseListener(inputHandler);
            addMouseMotionListener(inputHandler);
            addMouseWheelListener(inputHandler);
        }

        // Zoom helper: factor < 1 zooms IN, factor > 1 zooms OUT
        public void zoom(double factor) {
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

        public void zoomIn() {
            zoom(0.8); // Zoom in by 20%
        }

        public void zoomOut() {
            zoom(1.25); // Zoom out by 25%
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

        private double calculateNiceStep(double range) {
            double targetStep = range / 10.0; // Aim for ~10 grid lines
            double mag = Math.pow(10, Math.floor(Math.log10(targetStep)));
            double base = targetStep / mag;
            double step;
            if (base < 2)
                step = 1.0 * mag;
            else if (base < 5)
                step = 2.0 * mag;
            else
                step = 5.0 * mag;
            return step;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

            int w = getWidth();
            int h = getHeight();

            double xRange = maxX - minX;
            double yRange = maxY - minY;
            double scaleX = w / xRange;
            double scaleY = h / yRange;

            // Colors (Desmos Style)
            Color bgColor = darkModeGraph ? new Color(25, 25, 25) : Color.WHITE;
            Color minorGridColor = darkModeGraph ? new Color(60, 60, 60) : new Color(235, 235, 235);
            Color majorGridColor = darkModeGraph ? new Color(90, 90, 90) : new Color(200, 200, 200);
            Color axisColor = darkModeGraph ? new Color(220, 220, 220) : new Color(50, 50, 50);
            Color labelColor = darkModeGraph ? new Color(180, 180, 180) : new Color(80, 80, 80);

            g2.setColor(bgColor);
            g2.fillRect(0, 0, w, h);

            // Dynamic Grids
            double xStep = calculateNiceStep(xRange);
            double yStep = calculateNiceStep(yRange);

            // Minor Grid (subdivided by 5)
            g2.setStroke(new BasicStroke(0.5f));
            g2.setColor(minorGridColor);
            drawGridLines(g2, w, h, xStep / 5.0, yStep / 5.0, scaleX, scaleY, false, null);

            // Major Grid
            g2.setStroke(new BasicStroke(1.0f));
            g2.setColor(majorGridColor);
            drawGridLines(g2, w, h, xStep, yStep, scaleX, scaleY, true, labelColor);

            // Axes
            g2.setColor(axisColor);
            g2.setStroke(new BasicStroke(1.5f));
            int yAxisX = (int) ((0 - minX) * scaleX);
            int xAxisY = (int) ((maxY - 0) * scaleY);
            if (minX <= 0 && maxX >= 0)
                g2.drawLine(yAxisX, 0, yAxisX, h);
            if (minY <= 0 && maxY >= 0)
                g2.drawLine(0, xAxisY, w, xAxisY);

            // Plotting Logic
            g2.setColor(new Color(220, 50, 40)); // Desmos Red
            g2.setStroke(new BasicStroke(2.5f));

            String plotFunc = currentFunction.replace(" ", "");
            boolean hasEquals = plotFunc.contains("=");
            boolean isExplicit = !hasEquals;

            if (hasEquals) {
                String[] parts = plotFunc.split("=");
                if (parts.length == 2 && parts[0].equals("y") && !parts[1].contains("y")) {
                    isExplicit = true;
                    plotFunc = parts[1];
                }
            }

            if (!isExplicit) {
                // Implicit Graphing: Grid Scan
                drawImplicit(g2, w, h, scaleX, scaleY, plotFunc, hasEquals);
            } else {
                // Explicit Graphing: y = f(x)
                drawExplicit(g2, w, h, scaleX, scaleY, plotFunc);
            }
        }

        private void drawGridLines(Graphics2D g2, int w, int h, double xStep, double yStep, double scaleX,
                double scaleY, boolean drawLabels, Color labelColor) {
            Font font = new Font("SansSerif", Font.BOLD, 12);
            g2.setFont(font);
            FontMetrics fm = g2.getFontMetrics();

            // Vertical Lines
            double startX = Math.ceil(minX / xStep) * xStep;
            for (double x = startX; x <= maxX; x += xStep) {
                int px = (int) ((x - minX) * scaleX);
                g2.setColor(drawLabels ? g2.getColor() : g2.getColor()); // Re-set color just in case
                g2.drawLine(px, 0, px, h);

                if (drawLabels && Math.abs(x) > 1e-10) { // Don't label axis 0
                    Color save = g2.getColor();
                    g2.setColor(labelColor);
                    String lbl = formatLabel(x, xStep);
                    int lblW = fm.stringWidth(lbl);
                    int xAxisY = (int) ((maxY - 0) * scaleY);
                    int yPos = (xAxisY < 0) ? 15 : (xAxisY > h) ? h - 5 : xAxisY + 20;
                    if (xAxisY >= 0 && xAxisY <= h)
                        yPos = xAxisY + 15; // Standard pos
                    g2.drawString(lbl, px - lblW / 2, yPos);
                    g2.setColor(save);
                }
            }

            // Horizontal Lines
            double startY = Math.ceil(minY / yStep) * yStep;
            for (double y = startY; y <= maxY; y += yStep) {
                int py = (int) ((maxY - y) * scaleY);
                g2.drawLine(0, py, w, py);

                if (drawLabels && Math.abs(y) > 1e-10) {
                    Color save = g2.getColor();
                    g2.setColor(labelColor);
                    String lbl = formatLabel(y, yStep);
                    int lblW = fm.stringWidth(lbl);
                    int yAxisX = (int) ((0 - minX) * scaleX);
                    int xPos = (yAxisX < 0) ? 5 : (yAxisX > w) ? w - lblW - 5 : yAxisX - lblW - 5;
                    if (yAxisX >= 0 && yAxisX <= w)
                        xPos = yAxisX - lblW - 5;
                    g2.drawString(lbl, xPos, py + 5);
                    g2.setColor(save);
                }
            }
        }

        private String formatLabel(double val, double step) {
            if (Math.abs(val) < 1e-10)
                return "0";
            if (Math.abs(val - Math.round(val)) < 1e-9)
                return String.valueOf((int) Math.round(val));

            if (step >= 1) {
                return String.format("%.0f", val);
            } else {
                int digits = (int) Math.ceil(-Math.log10(step));
                if (digits < 0)
                    digits = 0;
                return String.format("%." + digits + "f", val);
            }
        }

        private void drawExplicit(Graphics2D g2, int w, int h, double scaleX, double scaleY, String func) {
            Path2D.Double path = new Path2D.Double();
            boolean first = true;
            for (int i = 0; i <= w; i++) {
                double logicalX = minX + (i / scaleX);
                double logicalY = FunctionParser.eval(func, logicalX);
                double pixelY = (maxY - logicalY) * scaleY;

                if (Double.isNaN(pixelY) || Double.isInfinite(pixelY)) {
                    first = true;
                    continue;
                }
                if (pixelY < -h || pixelY > 2 * h) { // Optimization
                    if (!first)
                        path.lineTo(i, pixelY);
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

        private void drawImplicit(Graphics2D g2, int w, int h, double scaleX, double scaleY, String plotFunc,
                boolean hasEquals) {
            String lhs, rhs;
            if (hasEquals) {
                String[] parts = plotFunc.split("=");
                lhs = parts[0];
                rhs = (parts.length > 1) ? parts[1] : "0";
            } else {
                lhs = plotFunc;
                rhs = "0";
            }

            // Adaptive Resolution: Faster when zoomed out, detailed when in?
            // Fixed is fine for now, standardizing on 4px
            int step = 4; // Desmos uses quad-trees, we use grid scan for simplicity

            for (int px = 0; px < w; px += step) {
                for (int py = 0; py < h; py += step) {
                    double x1 = minX + (px / scaleX);
                    double y1 = maxY - (py / scaleY);
                    double val1 = FunctionParser.eval(lhs, x1, y1) - FunctionParser.eval(rhs, x1, y1);

                    if (px + step < w) {
                        double x2 = minX + ((px + step) / scaleX);
                        double val2 = FunctionParser.eval(lhs, x2, y1) - FunctionParser.eval(rhs, x2, y1);
                        if (Math.signum(val1) != Math.signum(val2))
                            g2.drawLine(px, py, px + step, py);
                    }
                    if (py + step < h) {
                        double y2 = maxY - ((py + step) / scaleY);
                        double val2 = FunctionParser.eval(lhs, x1, y2) - FunctionParser.eval(rhs, x1, y2);
                        if (Math.signum(val1) != Math.signum(val2))
                            g2.drawLine(px, py, px, py + step);
                    }
                }
            }
        }
    }

    // API method for JavaScript to call via CheerpJ
    public static void setWebTheme(String mode) {
        if (instance != null) {
            final boolean dark = "dark".equalsIgnoreCase(mode);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    instance.applyTheme(dark);
                }
            });
        }
    }
}