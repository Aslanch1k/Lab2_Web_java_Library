package MVC;

import ConsoleMenu.ConsoleMenu;
import java.sql.SQLException;

/**
 * Class controller from MVC pattern
 * contains method execute that start our app
 */
public class Controller {

    public void execute() throws SQLException, ClassNotFoundException {
    ConsoleMenu.startWindow();
    }
}
