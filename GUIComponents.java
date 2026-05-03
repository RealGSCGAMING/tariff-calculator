import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

class ModalUtils {

    public static Font titleFont = new Font("Nunito Regular", Font.PLAIN, 50);
    public static Font normalFont = new Font("Nunito Regular", Font.PLAIN, 25);
    public static Font monoFont = new Font("Monospaced", Font.PLAIN, 25);

    public static Dimension buttonSize = new Dimension(400, 100);
    public static Dimension halfButtonSize = new Dimension(200, 100);

    /**
     * Quickly applies properties to a JComponent.
     * 
     * @param component       The component to be modified.
     * @param font            The font that the component should use.
     * @param foregroundColor The foreground color that the component should use.
     * @param backgroundColor The background color that the component should use.
     */
    public static void applyProperties(JComponent component, Font font, Color foregroundColor, Color backgroundColor,
            Dimension size) {
        component.setFont(font);
        component.setForeground(foregroundColor);
        component.setBackground(backgroundColor);
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        component.setMaximumSize(size);
        component.setPreferredSize(size);
    }

    /**
     * Fills a dropdown with countries contained in the tariff dataset.
     * 
     * @param dropdown The dropdown to be filled.
     */
    public static void fillCountryDropdown(JComboBox<String> dropdown) {
        String[][] tariffList = TariffData.getFullData();
        for (String[] i : tariffList) {
            dropdown.addItem(i[0] + " | " + i[1]);
        }
    }

    /**
     * Fills a dropdown with currencies contained in the currency dataset.
     * 
     * @param dropdown The dropdown to be filled.
     */
    public static void fillCurrencyDropdown(JComboBox<String> dropdown) {
        String[][] currencyList = CurrencyConversion.getFullData();
        for (String[] i : currencyList) {
            dropdown.addItem(i[0]);
        }
    }

    /**
     * Removes special characters from a price string.
     * 
     * @param s The string to be cleaned.
     * 
     * @return The cleaned string.
     */
    public static String cleanPriceString(String str) {

        String cleanString = "";
        String[] chars = str.split("");
        boolean usedDecimal = false;

        for (String c : chars) {
            if ("1234567890.$".contains(c)) {

                if (c.equals(".")) {
                    if (usedDecimal) {
                        return "";
                    } else {
                        cleanString += c;
                        usedDecimal = true;
                    }
                } else if (!cleanString.equals("$")) {
                    cleanString += c;
                }

            } else {
                return "";
            }
        }

        return cleanString;
    }

    /**
     * Allows a JButton to be activated by highlighting it and pressing Enter.
     * NOTE: This method was written by AI.
     * 
     * @param button The button to be activated.
     */
    public static void addEnterKeySupport(JButton button) {
        button.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "press");
        button.getActionMap().put("press", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });
    }

}

class MainModal {
    /**
     * @return The main menu modal.
     */
    public static JPanel get(CardLayout layoutManager, JPanel parent, JFrame closableFrame) {

        // Main modal
        JPanel mainModal = new JPanel();
        mainModal.setBackground(Color.BLACK);
        mainModal.setLayout(new BoxLayout(mainModal, BoxLayout.Y_AXIS));

        // Title text
        JLabel title = new JLabel("Tariff Calculator");

        ImageIcon imgIcon = new ImageIcon("logo.png");
        Image img = imgIcon.getImage();
        img = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(img);
        title.setIcon(imgIcon);

        ModalUtils.applyProperties(title, ModalUtils.titleFont, Color.WHITE, Color.WHITE, null);
        title.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Calculator button
        JButton calculatorButton = new JButton("Calculate Tariff");
        ModalUtils.applyProperties(calculatorButton, ModalUtils.normalFont, Color.WHITE, new Color(19, 102, 11),
                ModalUtils.buttonSize);
        ModalUtils.addEnterKeySupport(calculatorButton);

        // Currency exchange button
        JButton currencyButton = new JButton("Currency Exchange");
        ModalUtils.applyProperties(currencyButton, ModalUtils.normalFont, Color.WHITE, new Color(69, 153, 232),
                ModalUtils.buttonSize);
        ModalUtils.addEnterKeySupport(currencyButton);

        // Rankings button
        JButton rankingsButton = new JButton("Tariff List");
        ModalUtils.applyProperties(rankingsButton, ModalUtils.normalFont, Color.WHITE, new Color(202, 180, 15),
                ModalUtils.buttonSize);
        ModalUtils.addEnterKeySupport(rankingsButton);

        // Close button
        JButton closeButton = new JButton("Close");
        ModalUtils.applyProperties(closeButton, ModalUtils.normalFont, Color.BLACK, new Color(247, 155, 148),
                ModalUtils.buttonSize);
        ModalUtils.addEnterKeySupport(closeButton);

        calculatorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layoutManager.show(parent, "calculator");
            }
        });

        currencyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layoutManager.show(parent, "currency");
            }
        });

        rankingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layoutManager.show(parent, "rankings");
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closableFrame.dispose();
            }
        });

        mainModal.add(title);
        mainModal.add(calculatorButton);
        mainModal.add(currencyButton);
        mainModal.add(rankingsButton);
        mainModal.add(closeButton);

        return mainModal;
    }
}

class CalculatorModal {

    /**
     * @return The tariff calculation modal.
     */
    public static JPanel get(CardLayout layoutManager, JPanel parent) {

        // Calculator modal
        JPanel calculatorModal = new JPanel();
        calculatorModal.setBackground(Color.BLACK);
        calculatorModal.setLayout(new BoxLayout(calculatorModal, BoxLayout.Y_AXIS));

        // Title text
        JLabel title = new JLabel("Calculate Tariff");
        ModalUtils.applyProperties(title, ModalUtils.titleFont, Color.WHITE, null, null);
        title.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Country selection dropdown
        JComboBox<String> countryDropdown = new JComboBox<>();
        ModalUtils.applyProperties(countryDropdown, ModalUtils.monoFont, null, Color.WHITE, ModalUtils.buttonSize);
        countryDropdown.addItem("[Select Origin Country]");
        ModalUtils.fillCountryDropdown(countryDropdown);

        // Item price input
        JTextField priceInput = new JTextField("[Enter Item Price]");
        ModalUtils.applyProperties(priceInput, ModalUtils.monoFont, Color.GRAY, Color.WHITE, new Dimension(300, 100));
        priceInput.setHorizontalAlignment(SwingConstants.CENTER);

        priceInput.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (priceInput.getText().equals("[Enter Item Price]")) {
                    priceInput.setText("");
                    priceInput.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (priceInput.getText().isEmpty()) {
                    priceInput.setForeground(Color.GRAY);
                    priceInput.setText("[Enter Item Price]");
                }
            }
        });

        // Currency selection dropdown
        JComboBox<String> currencyDropdown = new JComboBox<>();
        ModalUtils.applyProperties(currencyDropdown, ModalUtils.monoFont, Color.BLACK, Color.WHITE,
                new Dimension(100, 100));

        currencyDropdown.addItem("USD");
        currencyDropdown.addItem("EUR");
        currencyDropdown.addItem("GBP");
        currencyDropdown.addItem("JPY");
        currencyDropdown.addItem("---");
        ModalUtils.fillCurrencyDropdown(currencyDropdown);

        currencyDropdown.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                if (currencyDropdown.getSelectedItem().equals("---")) {
                    currencyDropdown.setSelectedIndex(0);
                }
            }
        });

        JPanel priceInputGroup = new JPanel();
        priceInputGroup.setMaximumSize(new Dimension(400, 100));
        priceInputGroup.setLayout(new BoxLayout(priceInputGroup, BoxLayout.X_AXIS));
        priceInputGroup.add(priceInput);
        priceInputGroup.add(currencyDropdown);

        // Submit button
        JButton submitButton = new JButton("Submit");
        ModalUtils.applyProperties(submitButton, ModalUtils.normalFont, Color.WHITE, new Color(19, 102, 11),
                ModalUtils.buttonSize);
        ModalUtils.addEnterKeySupport(submitButton);

        // Error and waiting display
        JLabel resultLabel = new JLabel("Waiting...");
        ModalUtils.applyProperties(resultLabel, ModalUtils.normalFont, null, Color.WHITE, ModalUtils.buttonSize);
        resultLabel.setBorder(new EmptyBorder(50, 50, 50, 50));
        resultLabel.setMinimumSize(new Dimension(0, 100));
        resultLabel.setOpaque(true);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Tariff price display
        JLabel tariffLabel = new JLabel("...");
        ModalUtils.applyProperties(tariffLabel, ModalUtils.normalFont, null, new Color(247, 155, 148),
                ModalUtils.halfButtonSize);
        tariffLabel.setBorder(new EmptyBorder(50, 50, 50, 50));
        tariffLabel.setMinimumSize(new Dimension(200, 100));
        tariffLabel.setOpaque(true);
        tariffLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tariffLabel.setVisible(false);

        // Total price display
        JLabel totalLabel = new JLabel("...");
        ModalUtils.applyProperties(totalLabel, ModalUtils.normalFont, null, new Color(148, 247, 155),
                ModalUtils.halfButtonSize);
        totalLabel.setBorder(new EmptyBorder(50, 50, 50, 50));
        totalLabel.setMinimumSize(new Dimension(200, 100));
        totalLabel.setOpaque(true);
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setVisible(false);

        JPanel resultGroup = new JPanel();
        resultGroup.setMaximumSize(new Dimension(400, 100));
        resultGroup.setLayout(new BoxLayout(resultGroup, BoxLayout.X_AXIS));
        resultGroup.add(resultLabel);
        resultGroup.add(tariffLabel);
        resultGroup.add(totalLabel);

        // Back button
        JButton backButton = new JButton("Main Menu");
        ModalUtils.applyProperties(backButton, ModalUtils.normalFont, Color.WHITE, Color.BLACK, ModalUtils.buttonSize);
        ModalUtils.addEnterKeySupport(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layoutManager.show(parent, "main");
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (priceInput.getText().equals("[Enter Item Price]")
                        || countryDropdown.getSelectedItem().equals("[Select Origin Country]")
                        || currencyDropdown.getSelectedItem().equals("---")) {
                    resultLabel.setVisible(true);
                    tariffLabel.setVisible(false);
                    totalLabel.setVisible(false);
                    resultLabel.setText("Please fill in all values!");
                } else {
                    System.out.println(
                            "Getting the tariff on a " + ModalUtils.cleanPriceString(priceInput.getText()) + " "
                                    + currencyDropdown.getSelectedItem() + " item from "
                                    + countryDropdown.getSelectedItem().toString().substring(0, 2));
                    double price;
                    try {
                        price = Double.parseDouble(ModalUtils.cleanPriceString(priceInput.getText()));
                    } catch (Exception ex) {
                        resultLabel.setText("Price must be a number!");
                        resultLabel.setVisible(true);
                        tariffLabel.setVisible(false);
                        totalLabel.setVisible(false);
                        return;
                    }

                    double convertedPrice = CurrencyConversion.convert(price,
                            currencyDropdown.getSelectedItem().toString());

                    String countryCode = countryDropdown.getSelectedItem().toString().substring(0, 2);

                    double totalPrice = TariffOperations.getTariff(convertedPrice, countryCode, false);
                    String totalPriceStr = String.valueOf(totalPrice);
                    if (totalPriceStr.substring(totalPriceStr.indexOf(".") + 1).length() < 2) {
                        totalPriceStr += "0";
                    }

                    double tariffPrice = Math.round((totalPrice - convertedPrice) * 100.0) / 100.0;
                    String tariffPriceStr = String.valueOf(tariffPrice);
                    if (tariffPriceStr.substring(tariffPriceStr.indexOf(".") + 1).length() < 2) {
                        tariffPriceStr += "0";
                    }

                    // resultLabel.setText("$" + tariffPrice);
                    totalLabel.setText("<html>Total:<br>$" + totalPriceStr + "</html>");
                    totalLabel.setVisible(true);
                    tariffLabel.setText("<html>Tariff:<br>$"
                            + tariffPriceStr + "</html>");
                    tariffLabel.setVisible(true);
                }
            }
        });

        calculatorModal.add(title);
        calculatorModal.add(countryDropdown);
        calculatorModal.add(priceInputGroup);
        calculatorModal.add(submitButton);
        calculatorModal.add(resultGroup);
        calculatorModal.add(backButton);

        return calculatorModal;
    }

}

class CurrencyModal {
    /**
     * @return The currency conversion modal.
     */
    public static JPanel get(CardLayout layoutManager, JPanel parent) {
        JPanel currencyPanel = new JPanel();
        currencyPanel.setBackground(Color.BLACK);
        currencyPanel.setLayout(new BoxLayout(currencyPanel, BoxLayout.Y_AXIS));

        // Title text
        JLabel title = new JLabel("Currency Exchange");
        ModalUtils.applyProperties(title, ModalUtils.titleFont, Color.WHITE, null, null);
        title.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Top text
        JLabel topText = new JLabel("Convert");
        ModalUtils.applyProperties(topText, ModalUtils.normalFont, Color.WHITE, null, null);
        topText.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Item price input
        JTextField priceInput = new JTextField("[Amount]");
        ModalUtils.applyProperties(priceInput, ModalUtils.monoFont, Color.GRAY, Color.WHITE, new Dimension(300, 100));
        priceInput.setHorizontalAlignment(SwingConstants.CENTER);

        priceInput.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (priceInput.getText().equals("[Amount]")) {
                    priceInput.setText("");
                    priceInput.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (priceInput.getText().isEmpty()) {
                    priceInput.setForeground(Color.GRAY);
                    priceInput.setText("[Amount]");
                }
            }
        });

        // Currency selection dropdown
        JComboBox<String> currencyDropdown = new JComboBox<>();
        ModalUtils.applyProperties(currencyDropdown, ModalUtils.monoFont, Color.BLACK, Color.WHITE,
                new Dimension(100, 100));

        currencyDropdown.addItem("USD");
        currencyDropdown.addItem("EUR");
        currencyDropdown.addItem("GBP");
        currencyDropdown.addItem("JPY");
        currencyDropdown.addItem("---");
        ModalUtils.fillCurrencyDropdown(currencyDropdown);

        currencyDropdown.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                if (currencyDropdown.getSelectedItem().equals("---")) {
                    currencyDropdown.setSelectedIndex(0);
                }
            }
        });

        // Divider text
        JLabel dividerText = new JLabel("to");
        ModalUtils.applyProperties(dividerText, ModalUtils.normalFont, Color.BLACK, null, null);
        dividerText.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Currency selection dropdown 2
        JComboBox<String> currencyDropdown2 = new JComboBox<>();
        ModalUtils.applyProperties(currencyDropdown2, ModalUtils.monoFont, Color.BLACK, Color.WHITE,
                new Dimension(100, 100));

        currencyDropdown2.addItem("USD");
        currencyDropdown2.addItem("EUR");
        currencyDropdown2.addItem("GBP");
        currencyDropdown2.addItem("JPY");
        currencyDropdown2.addItem("---");
        ModalUtils.fillCurrencyDropdown(currencyDropdown2);

        currencyDropdown2.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                if (currencyDropdown2.getSelectedItem().equals("---")) {
                    currencyDropdown2.setSelectedIndex(0);
                }
            }
        });

        // Price input group
        JPanel priceInputGroup = new JPanel();
        priceInputGroup.setMaximumSize(new Dimension(400, 100));
        priceInputGroup.setLayout(new BoxLayout(priceInputGroup, BoxLayout.X_AXIS));
        priceInputGroup.add(priceInput);
        priceInputGroup.add(currencyDropdown);
        priceInputGroup.add(dividerText);
        priceInputGroup.add(currencyDropdown2);

        // Submit button
        JButton submitButton = new JButton("Submit");
        ModalUtils.applyProperties(submitButton, ModalUtils.normalFont, Color.WHITE, new Color(19, 102, 11),
                ModalUtils.buttonSize);
        ModalUtils.addEnterKeySupport(submitButton);

        // Result display
        JLabel resultLabel = new JLabel("Waiting...");
        ModalUtils.applyProperties(resultLabel, ModalUtils.normalFont, Color.BLACK, Color.WHITE, ModalUtils.buttonSize);
        resultLabel.setBorder(new EmptyBorder(50, 50, 50, 50));
        resultLabel.setOpaque(true);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (priceInput.getText().equals("[Enter Amount]")
                        || currencyDropdown.getSelectedItem().equals("---")
                        || currencyDropdown2.getSelectedItem().equals("---")) {
                    resultLabel.setText("Please fill in all values!");
                } else {
                    System.out.println("Converting " + ModalUtils.cleanPriceString(priceInput.getText()) + " "
                            + currencyDropdown.getSelectedItem() + " to " + currencyDropdown2.getSelectedItem());
                    double inputPrice;
                    try {
                        inputPrice = Double.parseDouble(ModalUtils.cleanPriceString(priceInput.getText()));
                    } catch (Exception ex) {
                        resultLabel.setText("Price must be a number!");
                        return;
                    }

                    double usdPrice = CurrencyConversion.convert(inputPrice,
                            currencyDropdown.getSelectedItem().toString());

                    double convertedPrice = CurrencyConversion.reverseConvert(usdPrice,
                            currencyDropdown2.getSelectedItem().toString());

                    String convertedPriceStr = String.valueOf(convertedPrice);
                    if (convertedPriceStr.substring(convertedPriceStr.indexOf(".") + 1).length() < 2) {
                        convertedPriceStr += "0";
                    }

                    resultLabel.setText(convertedPriceStr + " " + currencyDropdown2.getSelectedItem().toString());
                }
            }
        });

        // Back button
        JButton backButton = new JButton("Main Menu");
        ModalUtils.applyProperties(backButton, ModalUtils.normalFont, Color.WHITE, Color.BLACK, ModalUtils.buttonSize);
        ModalUtils.addEnterKeySupport(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layoutManager.show(parent, "main");
            }
        });

        currencyPanel.add(title);
        currencyPanel.add(topText);
        currencyPanel.add(priceInputGroup);
        currencyPanel.add(submitButton);
        currencyPanel.add(resultLabel);
        currencyPanel.add(backButton);

        return currencyPanel;
    }
}

class RankingsModal {
    /**
     * @return The tariff calculation modal.
     */
    public static JPanel get(CardLayout layoutManager, JPanel parent) {

        JPanel rankingsPanel = new JPanel();
        rankingsPanel.setBackground(Color.BLACK);
        rankingsPanel.setLayout(new BoxLayout(rankingsPanel, BoxLayout.Y_AXIS));

        // Title text
        JLabel title = new JLabel("Tariff List");
        ModalUtils.applyProperties(title, ModalUtils.titleFont, Color.WHITE, null, null);
        title.setBorder(new EmptyBorder(50, 50, 10, 50));

        // Tariff list
        JPanel rankingList = new JPanel();
        rankingList.setLayout(new BoxLayout(rankingList, BoxLayout.Y_AXIS));
        JScrollPane viewport = new JScrollPane(rankingList);
        viewport.setMaximumSize(new Dimension(400, 400));
        viewport.setPreferredSize(new Dimension(400, 400));
        viewport.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[][] sortedData = Arrays.copyOf(TariffData.getFullData(), TariffData.getFullData().length);

        // Uncomment to sort tariffs by percentage
        // Arrays.sort(sortedData, Comparator.comparingDouble(row ->
        // Double.valueOf((TariffOperations.USE_IEEPA ? row[3] : row[2]))));

        for (String[] info : sortedData) {
            if (info[2].equals(".00")) {
                continue;
            }
            JPanel infoPanel = new JPanel();
            infoPanel.setMaximumSize(new Dimension(400, 100));
            infoPanel.setBackground(new Color((int) (Math.random() * 100) + 155, (int) (Math.random() * 100) + 155,
                    (int) (Math.random() * 100) + 155));

            JLabel countryName = new JLabel(info[1]);
            JLabel tariff = new JLabel((TariffOperations.USE_IEEPA ? info[3] : info[2]).substring(1) + "%");

            infoPanel.add(countryName);
            infoPanel.add(tariff);

            rankingList.add(infoPanel);
        }

        // Back button
        JButton backButton = new JButton("Main Menu");
        ModalUtils.applyProperties(backButton, ModalUtils.normalFont, Color.WHITE, Color.BLACK, ModalUtils.buttonSize);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layoutManager.show(parent, "main");
            }
        });

        rankingsPanel.add(title);
        rankingsPanel.add(viewport);
        rankingsPanel.add(backButton);

        return rankingsPanel;
    }
}