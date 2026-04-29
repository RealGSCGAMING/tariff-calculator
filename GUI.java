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
        // frame.setResizable(false);

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
        dropdown.setMaximumSize(new Dimension(350, 100));
        dropdown.setFont(new Font("Nunito Regular", Font.PLAIN, 25));
        dropdown.addItem("[Select Country of Origin]");
        dropdown.addItem("Germany");

        // Item price input
        JTextField priceInput = new JTextField("[Enter Item Price]");
        priceInput.setFont(new Font("Nunito Regular", Font.PLAIN, 25));
        priceInput.setForeground(Color.GRAY);
        priceInput.setMaximumSize(new Dimension(350, 100));
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
        submitButton.setMaximumSize(new Dimension(350, 100));
        submitButton.setBackground(new Color(19, 102, 11));
        submitButton.setForeground(Color.WHITE);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Calculation result display
        JLabel resultLabel = new JLabel("Waiting...");
        resultLabel.setBorder(new EmptyBorder(50, 50, 50, 50));
        resultLabel.setFont(new Font("Nunito Regular", Font.PLAIN, 25));
        resultLabel.setMaximumSize(new Dimension(350, 100));
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.WHITE);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultLabel.setText("$" + String.valueOf(TariffOperations.getTariff(Double.parseDouble(priceInput.getText()), "DE", false)) + "0");
            }
        });

        mainPanel.add(title);
        mainPanel.add(dropdown);
        mainPanel.add(priceInput);
        mainPanel.add(submitButton);
        mainPanel.add(resultLabel);

        frame.add(mainPanel);

        // Other buttons
        // frame.add(new JButton("LeftButton"), BorderLayout.WEST);
        // frame.add(new JButton("RightButton"), BorderLayout.EAST);

        // Header and footer
        // frame.add(new JButton("TopButton"), BorderLayout.NORTH);
        // frame.add(new JButton("BottomButton"), BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private JButton createButton(String text, int minx, int miny, int preferredx, int preferredy) {
        JButton button = new JButton(text);
        button.setMinimumSize(new Dimension(minx, miny));
        button.setPreferredSize(new Dimension(preferredx, preferredy));
        return button;
    }

    /**
     * This main method is for testing only and should be removed in production.
     */
    public static void main(String[] args) {
        TariffData.loadData("tariff.csv");
        GUI testGui = new GUI();
    }
}
