package User.Buliders;

/**
 * Class LibrarianBuilder
 * Build a user with status Librarian
 */
public class LibrarianBuilder extends BaseBuilder {

    public void buildStatus() {
        user.setStatus("Librarian");
    }

    @Override
    public void buildBlockStatus() {
        user.setBlockStatus(false);
    }

    public void buildLogin(String login) {
        user.setLogin(login);
    }

    public void buildPassword(String password) {
        user.setPassword(password);
    }
}
