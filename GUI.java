import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GUI {

    /**
     * Creates a GUI.
     */
    public GUI() {

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(750, 500));
        frame.setPreferredSize(new Dimension(750, 750));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title text
        JLabel title = new JLabel("Tariff Calculator");
        title.setFont(new Font("Nunito Regular", Font.PLAIN, 50));
        title.setBorder(new EmptyBorder(50, 50, 50, 50));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Country selection dropdown
        JComboBox<String> dropdown = new JComboBox<>();
        dropdown.setMaximumSize(new Dimension(400, 100));
        dropdown.setFont(new Font("Monospaced", Font.PLAIN, 25));
        dropdown.addItem("[Select Origin Country]");
        fillCountryDropdown(dropdown);

        // Country selection dropdown
        JComboBox<String> dropdown2 = new JComboBox<>();
        dropdown2.setMaximumSize(new Dimension(400, 100));
        dropdown2.setFont(new Font("Monospaced", Font.PLAIN, 25));
        dropdown2.addItem("[Select Currency]");
        dropdown2.addItem("USD");
        fillCurrencyDropdown(dropdown2);

        // Item price input
        JTextField priceInput = new JTextField("[Enter Item Price]");
        priceInput.setFont(new Font("Nunito Regular", Font.PLAIN, 25));
        priceInput.setForeground(Color.GRAY);
        priceInput.setMaximumSize(new Dimension(400, 100));
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

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Nunito Regular", Font.PLAIN, 25));
        submitButton.setMaximumSize(new Dimension(400, 100));
        submitButton.setBackground(new Color(19, 102, 11));
        submitButton.setForeground(Color.WHITE);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Calculation result display
        JLabel resultLabel = new JLabel("Waiting...");
        resultLabel.setBorder(new EmptyBorder(50, 50, 50, 50));
        resultLabel.setFont(new Font("Nunito Regular", Font.PLAIN, 25));
        resultLabel.setMaximumSize(new Dimension(400, 100));
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.WHITE);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (priceInput.getText().equals("[Enter Item Price]")
                        || dropdown.getSelectedItem().equals("[Select Origin Country]")
                        || dropdown2.getSelectedItem().equals("[Select Currency]")) {
                    resultLabel.setText("Please fill in all values!");
                } else {
                    System.out.println("Getting the tariff on a " + priceInput.getText() + " "
                            + dropdown2.getSelectedItem() + " item from "
                            + dropdown.getSelectedItem().toString().substring(0, 2));
                    double price;
                    try {
                        price = Double.parseDouble(priceInput.getText());
                    } catch (Exception ex) {
                        resultLabel.setText("Price must be a number!");
                        return;
                    }

                    double convertedPrice = CurrencyConversion.convert(price, dropdown2.getSelectedItem().toString());

                    String countryCode = dropdown.getSelectedItem().toString().substring(0, 2);

                    double tariffPrice = TariffOperations.getTariff(convertedPrice, countryCode, false);

                    resultLabel.setText("$" + tariffPrice);
                }
            }
        });

        mainPanel.add(title);
        mainPanel.add(dropdown);
        mainPanel.add(priceInput);
        mainPanel.add(dropdown2);
        mainPanel.add(submitButton);
        mainPanel.add(resultLabel);

        frame.add(mainPanel);

        frame.pack();
        frame.setVisible(true);
    }

    private void fillCountryDropdown(JComboBox<String> dropdown) {
        String[][] tariffList = TariffData.getFullData();
        for (String[] i : tariffList) {
            dropdown.addItem(i[0] + " | " + i[1]);
        }
    }

    private void fillCurrencyDropdown(JComboBox<String> dropdown) {
        String[][] currencyList = CurrencyConversion.getFullData();
        for (String[] i : currencyList) {
            dropdown.addItem(i[0]);
        }
    }

    /**
     * This main method is for testing only and should be removed in production.
     */
    /*
     * public static void main(String[] args) {
     * TariffData.loadData();
     * new GUI();
     * }
     */
}
