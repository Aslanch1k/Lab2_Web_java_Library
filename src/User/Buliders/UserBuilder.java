package User.Buliders;

/**
 * Class UserBuilder
 * Build a user with status User
 */
public class UserBuilder extends BaseBuilder {

    public void buildStatus() {
        user.setStatus("User");
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
