import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {

    private JPanel mainModal, calculatorModal, currencyModal, rankingsModal;

    /**
     * Creates a GUI.
     */
    public GUI() {

        JFrame frame = new JFrame();
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(750, 500));
        frame.setPreferredSize(new Dimension(750, 750));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainModal = MainModal.get(cardLayout, cardPanel, frame);
        calculatorModal = CalculatorModal.get(cardLayout, cardPanel);
        currencyModal = CurrencyModal.get(cardLayout, cardPanel);
        rankingsModal = RankingsModal.get(cardLayout, cardPanel);

        cardPanel.add(mainModal, "main");
        cardPanel.add(calculatorModal, "calculator");
        cardPanel.add(currencyModal, "currency");
        cardPanel.add(rankingsModal, "rankings");

        frame.add(cardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
