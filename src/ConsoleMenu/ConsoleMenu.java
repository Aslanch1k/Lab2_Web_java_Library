package ConsoleMenu;

import Book.*;
import Db.DAOs.*;
import MVC.ConsoleView;
import MVC.DbModel;
import MVC.Model;
import MVC.View;
import Order.*;
import ReadingHoleRecord.*;
import SeasonTicketRecord.*;
import User.*;
import User.Buliders.*;
import org.w3c.dom.Entity;
import java.sql.Date;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class defines graphical interface of our application
 * and contains methods of its realization
 * Also contains a bit validation
 */
public class ConsoleMenu {

    static Model model = new DbModel();
    static View view = new ConsoleView();
    static User user = null;
    static UserDao userDao = new UserDao();
    static OrderDao orderDao = new OrderDao();
    static BookDao bookDao = new BookDao();
    static ReadingHoleDao readingHoleDao = new ReadingHoleDao();

    static Scanner scanner = new Scanner(System.in);

    static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static int enterInt(String str){
        str = scanner.nextLine();
        while (!isNumeric(str)){
            System.out.println("Enter integer!!!");
            str = scanner.nextLine();
        }
        int rez = Integer.parseInt(str);
        return rez;
    }


    public static void startWindow() throws SQLException, ClassNotFoundException {
        user = null;
        String infLogInWindow = """
                You are on start window of app
                \n---Library app---\n
                1 - Sing in app
                2 - Log in app
                Any key - Go to Catalog
                ":q" - Exit app
                """;
        System.out.println(infLogInWindow);
        String authMode = scanner.nextLine();
        if (Objects.equals(authMode, "1")) {
            createUserMode();
        } else if (Objects.equals(authMode, "2")) {
            logInUserMode();
        } else if (Objects.equals(authMode, ":q")) {
            System.exit(0);
        } else {
            catalog();
        }
    }

    static void userMenu() throws SQLException, ClassNotFoundException {
        System.out.println("---User Menu---\n");
        System.out.println("1 - Season ticket");
        System.out.println("2 - To do order");
        System.out.println("9 - Go to catalog");
        System.out.println("Any key - Go to start menu (unauthorization)");
        String mode = scanner.nextLine();
        if (Objects.equals(mode, "1")){
            seasonTicket();
        } else if (Objects.equals(mode, "2")) {
            order();
        } else if (Objects.equals(mode, "9")) {
            catalog();
        } else {
            startWindow();
        }
    }


    static void adminMenu() throws SQLException, ClassNotFoundException {
        System.out.println("---Admin Menu---\n");
        System.out.println("1 - Manage books");
        System.out.println("2 - Add librarian");
        System.out.println("3 - Change users block status");
        System.out.println("9 - Go to catalog");
        System.out.println("Any key - Go to start menu (unauthorization)");
        String mode = scanner.nextLine();
        if (Objects.equals(mode, "1")){
            manageBooks();
        } else if (Objects.equals(mode, "2")) {
            manageLibrarians();
        } else if (Objects.equals(mode, "3")){
            changeUserBlockStatus();
        } else if (Objects.equals(mode, "9")) {
            catalog();
        }else {
            startWindow();
        }
    }

    static void librarianMenu() throws SQLException, ClassNotFoundException {
        System.out.println("---Librarian Menu---\n");
        System.out.println("1 - Users list");
        System.out.println("2 - Users orders");
        System.out.println("3 - Users Seasons Tickets");
        System.out.println("4 - Manage reading hole records");
        System.out.println("9 - Go to catalog");
        System.out.println("Any key - Go to start menu (unauthorization)");
        OrderDao orderDao = new OrderDao();
        UserDao userDao = new UserDao();
        String mode = scanner.nextLine();

        if (Objects.equals(mode, "1")){
            userList();
        }
        else if (Objects.equals(mode, "2")){
            usersOrders();
        }
        else if (Objects.equals(mode, "3")) {
            usersSeasonTickets();
        }
        else if (Objects.equals(mode, "4")) {
            readingHoleMagazine();
        }
        else if (Objects.equals(mode, "9")) {
            catalog();
        }
        else {
            startWindow();
        }
    }


    static void userList() throws SQLException, ClassNotFoundException {
        System.out.println("---User list---\n");
        view.showEntities(model.findAllEntity(userDao));
        librarianMenu();
    }

    static void usersOrders() throws SQLException, ClassNotFoundException {
        System.out.println("---Orders---\n");
        view.showEntities(model.findAllEntity(orderDao));
        librarianMenu();
    }

    static void usersSeasonTickets() throws SQLException, ClassNotFoundException {
        System.out.println("---User tickets---\n");
        System.out.println("Enter users Login:");
        String login = scanner.nextLine();
        usersTicket(login);
    }

    static void changeUserBlockStatus() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        System.out.println("---Change Users Status---\n");
        System.out.println("Users id to change status:");

        String userIdStr = "";
        int userId = enterInt(userIdStr);

        System.out.println("Users new block status:");
        String blockStatus = scanner.nextLine();

        User user = model.getUser(userDao, userId);
        if (Objects.equals(user.getStatus(), "User")){
            model.updateEntity(userDao, userId,"blockStatus" ,blockStatus);
        }

        adminMenu();
    }
    static void manageLibrarians() throws SQLException, ClassNotFoundException {
        System.out.println("---Manage Librarians---\n");
        System.out.println("1 - Show librarians");
        System.out.println("2 - Add Librarian");
        System.out.println("3 - Remove Librarian");
        System.out.println("Any key - Go back");
        String mode = scanner.nextLine();
        if (Objects.equals(mode, "1")){
            showLibrarians();
        } else if (Objects.equals(mode, "2")) {
            addLibrarian();
        }else if (Objects.equals(mode, "3")) {
            removeLibrarian();
        } else {
            adminMenu();
        }
    }

    static void showLibrarians() throws SQLException, ClassNotFoundException {
        System.out.println("---Librarians---\n");
        view.showEntities(model.findByParamEntity(userDao, "status", "Librarian"));
        manageLibrarians();
    }

    static void addLibrarian() throws SQLException, ClassNotFoundException {
        System.out.println("---Adding librarian---\n");
        System.out.println("Librarian login:");
        String login = scanner.nextLine();

        System.out.println("Librarian password:");
        String password = scanner.nextLine();

        User librarian = User.buildUser(new LibrarianBuilder(), login, password);
        model.createEntity(userDao, librarian);
        manageLibrarians();
    }

    static void removeLibrarian() throws SQLException, ClassNotFoundException {
        System.out.println("---Removing librarian---\n");
        System.out.println("Librarian id to remove:");
        String librarianIdStr = "";

        int librarianId = enterInt(librarianIdStr);
        model.deleteEntity(userDao, librarianId);
        manageLibrarians();
    }

    static void manageBooks() throws SQLException, ClassNotFoundException {
        System.out.println("\n---Managing Books---\n");
        System.out.println("1 - Add book");
        System.out.println("2 - Remove book");
        System.out.println("3 - Update book");
        System.out.println("Any key - Go back");
        String mode = scanner.nextLine();
        if (Objects.equals(mode, "1")){
            addBook();
        } else if (Objects.equals(mode, "2")) {
            removeBook();
        } else if (Objects.equals(mode, "3")) {
            updateBook();
        } else {
            adminMenu();
        }
    }

    static void addBook() throws SQLException, ClassNotFoundException {
        System.out.println("---Book adding---\n");
        System.out.println("Name of book:");
        String name = scanner.nextLine();

        System.out.println("Author:");
        String author = scanner.nextLine();

        System.out.println("Publisher:");
        String publisher = scanner.nextLine();

        System.out.println("Date of publishing in format \"YYYY-MM-DD\":");
        Date date = Date.valueOf(scanner.nextLine());

        System.out.println("Quantity of books:");
        String quantityStr = "";
        int quantity = enterInt(quantityStr);

        Book book = Book.buildBook(
                new BookBuilder(),
                name,
                author,
                publisher,
                date,
                quantity
        );
        model.createEntity(bookDao, book);
        manageBooks();
    }

    static void removeBook() throws SQLException, ClassNotFoundException {
        System.out.println("---Removing book---\n");
        System.out.println("Book id to remove:");
        String bookIdStr = "";
        int bookId = enterInt(bookIdStr);

        model.deleteEntity(bookDao, bookId);
        manageBooks();
    }

    static void updateBook() throws SQLException, ClassNotFoundException {
        System.out.println("---Book Updating---\n");
        System.out.println("Book id you wanted to change:");
        String bookIdStr = "";
        int bookId = enterInt(bookIdStr);

        System.out.println("Parameter you wanted to change:");
        String param = scanner.nextLine();

        System.out.println("Value of param:");
        String value = scanner.nextLine();

        model.updateEntity(bookDao, bookId, param, value);
        manageBooks();
    }

    static void readingHoleMagazine() throws SQLException, ClassNotFoundException {
        System.out.println("---Reading Hole Magazine---\n");
        System.out.println("1 - Show reading hole records");
        System.out.println("2 - Add reading hole record");
        System.out.println("3 - Remove reading hole record");
        System.out.println("Any key - go back");
        String mode = scanner.nextLine();
        if (Objects.equals(mode, "1")){
            showReadingHoleRecords();
        } else if (Objects.equals(mode, "2")) {
            addReadingHoleRecord();
        } else if (Objects.equals(mode, "3")) {
            removeReadingHoleRecord();
        } else {
            librarianMenu();
        }
    }

    static void showReadingHoleRecords() throws SQLException, ClassNotFoundException {
        System.out.println("---Reading Hole Records---\n");
        view.showEntities(model.findAllEntity(readingHoleDao));
        readingHoleMagazine();
    }

    static void addReadingHoleRecord() throws SQLException, ClassNotFoundException {
        System.out.println("---Adding reading hole record---\n");
        System.out.println("Enter user Id, that get book to hole");
        String userIdStr = "";
        int userId = enterInt(userIdStr);

        System.out.println("Enter book Id, that user get to hole");
        String bookIdStr = "";
        int bookId = enterInt(bookIdStr);

        ReadingHoleRecord record = ReadingHoleRecord.buildReadingHoleRecord(
                new ReadingHoleRecordBuilder(),
                userId,
                bookId
        );
        model.createEntity(readingHoleDao, record);
        readingHoleMagazine();
    }

    static void removeReadingHoleRecord() throws SQLException, ClassNotFoundException {
        System.out.println("Enter record Id to remove");
        String recordIdStr = "";
        int recordId = enterInt(recordIdStr);

        model.deleteEntity(readingHoleDao, recordId);
        readingHoleMagazine();
    }

    static void usersTicket(String login) throws SQLException, ClassNotFoundException {
        System.out.println("---Users season Ticket---\n");
        System.out.println("1 - To show users records");
        System.out.println("2 - To add record to users ticket");
        System.out.println("3 - Delete record from users ticket");
        System.out.println("Any key - Go back");
        String mode = scanner.nextLine();
        if (Objects.equals(mode, "1")){
            showUsersRecords(login);
        }
        else if (Objects.equals(mode, "2")) {
            addRecordToUsersSeasonTicket(login);
        }
        else if(Objects.equals(mode, "3")){
            deleteRecordFromUsersSeasonTicket(login);
        }
        else {
            librarianMenu();
        }
    }

    static void showUsersRecords(String login) throws SQLException, ClassNotFoundException {
        SeasonTicketRecordDao dao = new SeasonTicketRecordDao();
        dao.setUserLogin(login);

        System.out.println("---");
        view.showEntities(model.findAllEntity(dao));
        usersTicket(login);
    }

    static void addRecordToUsersSeasonTicket(String login) throws SQLException, ClassNotFoundException {
        SeasonTicketRecordDao dao = new SeasonTicketRecordDao();
        dao.setUserLogin(login);

        System.out.println("---Adding record to UST---\n");
        System.out.println("Enter book id, that user get");
        String bookIdStr = "";
        int bookId = enterInt(bookIdStr);

        SeasonTicketRecord record = SeasonTicketRecord.buildSeasonTicketRecord(
                new SeasonTicketRecordBuilder(),
                bookId
        );

        model.createEntity(dao, record);

        int quantity = model.getBookById(bookDao, bookId).getQuantity();
        model.updateEntity(bookDao, bookId, "quantity", Integer.toString(quantity - 1));
        usersTicket(login);
    }

    static void deleteRecordFromUsersSeasonTicket(String login) throws SQLException, ClassNotFoundException {
        SeasonTicketRecordDao dao = new SeasonTicketRecordDao();
        dao.setUserLogin(login);

        System.out.println("---Removing from UST---\n");
        System.out.println("Enter record id, that you want to delete");
        String recordIdStr = "";
        int recordId = enterInt(recordIdStr);

        SeasonTicketRecord record = model.getRecordById(dao, recordId);

        int bookId = record.getBookId();
        int quantity = model.getBookById(bookDao, bookId).getQuantity();

        model.deleteEntity(dao, recordId);
        model.updateEntity(bookDao, bookId, "quantity", Integer.toString(quantity + 1));
        usersTicket(login);
    }

    static void seasonTicket() throws SQLException, ClassNotFoundException {
        System.out.println(MessageFormat.format("\n---Season Ticket of User: \"{0}\"---\n", user.getLogin()));
        System.out.println("1 - View season ticket records");
        System.out.println("Any key - Go back");

        String mode = scanner.nextLine();
        if (Objects.equals(mode, "1")){
            showSeasonTicket();
        } else {
            userMenu();
        }
    }

    static void showSeasonTicket() throws SQLException, ClassNotFoundException {
        SeasonTicketRecordDao dao = new SeasonTicketRecordDao();
        dao.setUserLogin(user.getLogin());

        System.out.println("\n---Season Ticket Records---\n");
        dao.setUserLogin(user.getLogin());

        view.showEntities(model.findAllEntity(dao));
        seasonTicket();
    }

    static void catalog() throws SQLException, ClassNotFoundException {
        BookDao bookDao = new BookDao();
        System.out.println("\n---CATALOG---\n");
        System.out.println("---Catalog options:---\n");
        System.out.println("0 - Show all books");
        System.out.println("1 - Search by name of book");
        System.out.println("2 - Search by author name");
        System.out.println("3 - Sort by param");
        System.out.println("Any key - Go Back");
        String mode = scanner.nextLine();
        if (Objects.equals(mode, "0")){
            showAllBooks();
        } else if (Objects.equals(mode, "1")){
            searchByNameOfBook();
        } else if (Objects.equals(mode, "2")) {
            searchByAuthorOfBook();
        } else if (Objects.equals(mode, "3")){
            sortByParam();
        }  else {
            if (user == null){
                startWindow();
            } else {
                if (Objects.equals(user.getStatus(), "User")){
                    userMenu();
                } else if (Objects.equals(user.getStatus(), "Admin")) {
                    adminMenu();
                } else {
                    librarianMenu();
                }
            }
        }
        catalog();
    }

    static void showAllBooks() throws SQLException, ClassNotFoundException {
        System.out.println("\n---All books in catalog---\n");
        view.showEntities(model.findAllEntity(bookDao));
        catalog();
    }

    static void searchByNameOfBook() throws SQLException, ClassNotFoundException {
        System.out.println("---Searching book by name---\n");
        System.out.println("Enter a book name:");
        String name = scanner.nextLine();

        List<Entity> books = model.findByParamEntity(bookDao, "name", name);
        if (books.size() == 0){
            System.out.println("This book are not available");
        } else {
            System.out.println(MessageFormat.format("\n---Books by search \"{0}\"---\n", name));
            view.showEntities(books);
        }
        catalog();
    }

    static void searchByAuthorOfBook() throws SQLException, ClassNotFoundException {
        System.out.println("---Searching book by author---\n");
        System.out.println("Enter a book author:");
        String author = scanner.nextLine();

        List<Entity> books = model.findByParamEntity(bookDao, "author", author);
        if (books.size() == 0){
            System.out.println("Books of this author are not available");
        } else {
            System.out.println(MessageFormat.format("\n---Books by search \"{0}\"---\n", author));
            view.showEntities(books);
        }
        catalog();
    }

    static void sortByParam() throws SQLException, ClassNotFoundException {
        System.out.println("---Sorting Book By Param---\n");
        System.out.println("1 - sort by name");
        System.out.println("2 - sort by author");
        System.out.println("3 - sort by publisher");
        System.out.println("4 - sort by date of publishing");
        System.out.println("Any key - Go back");
        String param = scanner.nextLine();
        if (Objects.equals(param, "1")){
            view.showEntities(model.sortBooksByValue(bookDao, "name"));
            sortByParam();
        } else if (Objects.equals(param, "2")) {
            view.showEntities(model.sortBooksByValue(bookDao, "author"));
            sortByParam();
        } else if (Objects.equals(param, "3")) {
            view.showEntities(model.sortBooksByValue(bookDao, "publisher"));
            sortByParam();
        } else if (Objects.equals(param, "4")){
            view.showEntities(model.sortBooksByValue(bookDao, "date"));
            sortByParam();
        } else {
            catalog();
        }
    }

    static void order() throws SQLException, ClassNotFoundException {
        System.out.println("---Ordering---\n");
        System.out.println("1 - To do order");
        System.out.println("Any key - Go back");

        String login = scanner.nextLine();
        if (Objects.equals(login, "1")){
            System.out.println("Entry an order data and Librarian will contact you: ");
            String orderData = scanner.nextLine();

            Order order = Order.buildOrder(new OrderBuilder(), user.getId(), orderData);

            int orderStatus = model.createEntity(orderDao, order);
            if (orderStatus != 0){
                System.out.println("Your order successfully done!!!");
                userMenu();
            } else {
                System.out.println("Please try one more(((");
                order();
            }
        } else {
            userMenu();
        }
    }

    static void createUserMode() throws SQLException, ClassNotFoundException {
        System.out.println("---Sing in page---\n");
        System.out.println("1 - Sing in");
        System.out.println("Any key - Go back");

        String login = scanner.nextLine();
        if (Objects.equals(login, "1")){
            System.out.println("Enter Login:");
            login = scanner.nextLine();

            if(model.findByParamEntity(userDao, "login", login).size() != 0){
                System.out.println("User with such login exists.\n1 - Try another one\nAny key - Go to stars Menu:");
                login = scanner.nextLine();
                if (Objects.equals(login, "1")){
                    createUserMode();
                }
                else {
                    startWindow();
                }
            }
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            User user = User.buildUser(new UserBuilder(), login, password);
            model.createEntity(userDao, user);
            System.out.println("User created!");
            logInUserMode();
        }
        else {
            startWindow();
        }

    }

    static void logInUserMode() throws SQLException, ClassNotFoundException {
        System.out.println("---Log in page---\n");
        System.out.println("1 - Log in");
        System.out.println("Any key - Go back");

        String login = scanner.nextLine();
        if (Objects.equals(login, "1")){
            System.out.println("Enter Login:");
            login = scanner.nextLine();

            System.out.println("Enter password:");
            String password = scanner.nextLine();

            user = model.authUser(userDao, login, password);
            if (user != null){
                if (user.isBlockStatus()){
                    System.out.println("You are blocked");
                    startWindow();
                }
                else {
                    System.out.println("Log in successful");
                    if (Objects.equals(user.getStatus(), "User")){
                        userMenu();
                    } else if (Objects.equals(user.getStatus(), "Admin")) {
                        adminMenu();
                    } else {
                        librarianMenu();
                    }
                }
            }
            else {
                System.out.println("User do not exist or bab password");
                System.out.println("1 - Try another one");
                System.out.println("Any key - Go to start Menu");
                String mode = scanner.nextLine();
                if (Objects.equals(mode, "1")) {
                    logInUserMode();
                }
                else{
                    startWindow();
                }
            }
        }
        else {
            startWindow();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
