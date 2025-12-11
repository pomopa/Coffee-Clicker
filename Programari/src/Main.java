import Business.*;
import Persistence.MySqlDAO;
import Presentation.Controllers.*;
import Presentation.Views.Frames.*;
import Presentation.Views.MainFrame;

import javax.swing.*;

/**
 * The entry point of the application. Initializes managers, views, controllers,
 * and sets up the MVC architecture.
 */
public class Main {

    /**
     * Default constructor for the Main class.
     */
    public Main() {}

    /**
     * The main method of the application.
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Implements the run method of the Runnable interface.
             */
            public void run() {
                //Instàncies dels models
                MySqlDAO mySqlDAO = new MySqlDAO();
                GameManager gameManager = new GameManager(mySqlDAO);
                SignUpManager signUpManager = new SignUpManager(mySqlDAO);
                LoginManager loginManager = new LoginManager(mySqlDAO);
                UserManager userManager = new UserManager(mySqlDAO);
                StartGameManager startGameManager = new StartGameManager(mySqlDAO);

                //Instàncies de les Views
                HomeView homeView = new HomeView();
                SignUpView signUpView = new SignUpView();
                LoginView loginView = new LoginView();
                StartGameView startGameView = new StartGameView();
                ResumeGameView resumeGameView = new ResumeGameView();
                UserView userView = new UserView();
                StatisticsView statisticsView = new StatisticsView();
                GameView gameView = new GameView();
                RecoverPasswordView recoverPasswordView = new RecoverPasswordView();

                //Instància del Main Frame
                MainFrame mainFrame = new MainFrame(homeView, signUpView, startGameView, resumeGameView, loginView,
                                                    userView,statisticsView, gameView, recoverPasswordView);

                //Instàncies dels controllers.
                MainController mainController = new MainController(gameManager, mainFrame);
                HomeController homeController = new HomeController(homeView, mainController);
                SignUpController signUpController = new SignUpController(signUpManager, gameManager, signUpView, mainController);
                LoginController loginController = new LoginController(loginManager, gameManager, loginView, mainController);
                StartGameController startGameController = new StartGameController(startGameView, mainController, startGameManager, gameManager);
                ResumeGameController resumeGameController = new ResumeGameController(resumeGameView, mainController, gameManager);
                UserController userController = new UserController(userManager, gameManager, userView, mainController);
                StatisticsController statisticsController = new StatisticsController(gameManager , statisticsView, mainController);
                GameController gameController = new GameController(mainController, gameView, gameManager);
                RecoverPasswordController recoverPasswordController = new RecoverPasswordController (mainController, recoverPasswordView, loginManager);

                //Registres dels controllers a les views.
                signUpView.registerController(signUpController);
                homeView.registerController(homeController);
                loginView.registerController(loginController);
                startGameView.registerController(startGameController);
                resumeGameView.registerController(resumeGameController);
                userView.registerController(userController);
                statisticsView.registerController(statisticsController);
                gameView.registerController(gameController);
                recoverPasswordView.registerController(recoverPasswordController);


                mainFrame.setVisible(true);
            }
        });

    }

}