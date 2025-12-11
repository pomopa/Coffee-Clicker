package Presentation.Controllers;

import Presentation.Views.Frames.HomeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller responsible for managing the actions on the HomeView.
 */
public class HomeController implements ActionListener {

    private MainControllerEvent mc;
    private HomeView homeView;

    /**
     * Default constructor for the class Home Controller.
     *
     * @param homeView The HomeView representing the home interface.
     * @param mc       The MainControllerEvent responsible for managing main application views.
     */
    public HomeController(HomeView homeView, MainControllerEvent mc) {
        this.homeView = homeView;
        this.mc = mc;
    }

    /**
     * Handles the actionPerformed event, determining the action based on the source.
     *
     * @param e The ActionEvent triggered by user interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case HomeView.SIGN_UP:
                mc.switchView(HomeView.SIGN_UP);
                break;

            case HomeView.LOG_IN:
                mc.switchView(HomeView.LOG_IN);
                break;
        }
    }
}
