import MVC.Controller;

import java.sql.SQLException;
/**
 *
 * The main class of program
 */
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Controller controller = new Controller();
        controller.execute();
    }
}