package Presentation.Views.Frames.Helpers;

import Presentation.Views.Frames.Enums.ViewStyles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A custom dialog box to display error messages.
 */
public class CustomErrorDialog extends JDialog {

    /**
     * String necessary to store the message that needs showing.
     */
    private String message;

    /**
     * Default constructor for the class custom error dialog.
     *
     * @param parent  the parent frame of the dialog
     * @param title   the title of the dialog
     * @param message the error message to display
     */
    public CustomErrorDialog(Frame parent, String title, String message) {
        super(parent, title, true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(ViewStyles.POPUP_WINDOW.getWidth(),ViewStyles.POPUP_WINDOW.getHeight());
        setLocationRelativeTo(parent);
        this.message = message;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(setImage(), BorderLayout.NORTH);
        mainPanel.add(setErrorMessage(), BorderLayout.CENTER);
        mainPanel.add(setErrorButton(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Sets the image panel at the top of the dialog.
     *
     * @return the JPanel containing the image
     */
    private JPanel setImage() {
        JPanel p = new JPanel();
        ImageIcon imageIcon = new ImageIcon("resources/angry-coffee.png");
        Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setBackground(Color.WHITE);

        p.add(imageLabel);

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
     * Sets the button panel at the bottom of the dialog.
     *
     * @return the JPanel containing the close button
     */
    private JPanel setErrorButton(){
        JPanel p = new JPanel();
        JButton closeButton = new JButton("Tanca");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        closeButton.setPreferredSize(new Dimension(ViewStyles.MEDIUM_BUTTON.getWidth(), ViewStyles.MEDIUM_BUTTON.getHeight()));
        closeButton.setBackground(Color.decode("#AD6729"));
        Color c = closeButton.getBackground();
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);

        p.add(closeButton);
        p.setBackground(Color.WHITE);

        p.setBorder(BorderFactory.createEmptyBorder(0, 0, 40 ,0));

        return p;
    }
}
