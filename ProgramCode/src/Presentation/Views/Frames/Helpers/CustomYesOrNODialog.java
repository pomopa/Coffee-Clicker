package Presentation.Views.Frames.Helpers;

import Presentation.Views.Frames.Enums.ViewStyles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A custom dialog box with yes/no options for user interaction.
 */
public class CustomYesOrNODialog extends JDialog {

    /**
     * String necessary to store the message that needs showing.
     */
    private String message;

    /**
     * Variable necessary to know which button was clicked.
     */
    private int buttonSelected;

    /**
     * Default constructor for the class Custom Yes or No Dialog.
     *
     * @param parent  the parent frame of the dialog
     * @param message the message to display in the dialog
     * @param button1 the text for the first button
     * @param button2 the text for the second button
     */
    public CustomYesOrNODialog(Frame parent, String message, String button1, String button2) {
        super(parent, true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(ViewStyles.POPUP_WINDOW.getWidth(),ViewStyles.POPUP_WINDOW.getHeight());
        setLocationRelativeTo(parent);
        this.message = message;
        buttonSelected = 0;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(setImage(), BorderLayout.NORTH);
        mainPanel.add(setErrorMessage(), BorderLayout.CENTER);
        mainPanel.add(setButtonsToChooseFrom(button1, button2), BorderLayout.SOUTH);

        add(mainPanel);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            //No volem que fagi cap acció, seria com si prem el botó de no.
            buttonSelected = 2;
            }
        });
    }

    /**
     * Sets the image panel at the top of the dialog.
     *
     * @return the JPanel containing the image
     */
    private JPanel setImage() {
        JPanel p = new JPanel();
        ImageIcon imageIcon = new ImageIcon("resources/ConfirmationPhoto2.png");
        Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(imageLabel);

        p.setBackground(Color.WHITE);

        p.setBorder(BorderFactory.createEmptyBorder(50, 0, 0 ,0));
        return p;
    }

    /**
     * Sets the error message panel in the center of the dialog.
     *
     * @return the JPanel containing the error message
     */
    private JPanel setErrorMessage() {
        JPanel p = new JPanel();
        JLabel label = new JLabel("<html><body><b style='word-wrap: break-word;'>" + message.replace("\n", "<br>") + "</b></body></html>");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(label);
        p.setBackground(Color.WHITE);
        return p;
    }

    /**
     * Sets the buttons panel at the bottom of the dialog.
     *
     * @param button1 the text for the first button
     * @param button2 the text for the second button
     * @return the JPanel containing the buttons
     */
    private JPanel setButtonsToChooseFrom(String button1, String button2) {
        JPanel p = new JPanel(new BorderLayout());
        JButton option1 = new JButton(button1);
        JButton option2 = new JButton(button2);

        option1.setForeground(Color.WHITE);
        option2.setForeground(Color.WHITE);

        option1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                buttonSelected = 1;
            }
        });

        option2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                buttonSelected = 2;
            }
        });

        option1.setBackground(Color.decode("#008000"));
        option2.setBackground(Color.decode("#F00000"));

        option1.setFocusPainted(false);
        option2.setFocusPainted(false);

        p.add(option1, BorderLayout.EAST);
        p.add(option2, BorderLayout.WEST);

        p.setBackground(Color.WHITE);

        p.setBorder(BorderFactory.createEmptyBorder(0, 100, 40 ,100));

        return p;
    }

    /**
     * Gets the selected button.
     *
     * @return the index of the selected button (1 for the first button, 2 for the second button)
     */
    public int getButtonSelected() {
        return buttonSelected;
    }
}
