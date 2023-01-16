package User.Buliders;

/**
 * Class AdminBuilder
 * Build a user with status Admin
 */
public class AdminBuilder extends BaseBuilder {
    public void buildStatus() {
        user.setStatus("Admin");
    }

    public void buildLogin(String login) {
        user.setLogin(login);
    }

    public void buildPassword(String password) {
        user.setPassword(password);
    }

    public void buildBlockStatus(){user.setBlockStatus(false);}
}
